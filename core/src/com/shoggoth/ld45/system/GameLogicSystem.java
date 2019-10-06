package com.shoggoth.ld45.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Interpolation;
import com.kothead.gdxjam.base.component.PositionComponent;
import com.kothead.gdxjam.base.util.Direction;
import com.shoggoth.ld45.EntityManager;
import com.shoggoth.ld45.component.*;
import com.shoggoth.ld45.screen.GameScreen;
import com.shoggoth.ld45.util.RenderConfig;
import com.shoggoth.ld45.util.Team;

import java.util.*;

public class GameLogicSystem extends EntitySystem {

    public static Direction[] SPAWN_DIRECTIONS = {
            Direction.DOWN,
            Direction.UP,
            Direction.LEFT,
            Direction.RIGHT
    };

    public static Direction[] ACTION_DIRECTIONS = {
            Direction.DOWN,
            Direction.UP,
            Direction.LEFT,
            Direction.RIGHT
    };

    private ImmutableArray<Entity> cells;
    private ImmutableArray<Entity> spawners;
    private ImmutableArray<Entity> creatures;
    private ImmutableArray<Entity> nothings;

    private ImmutableArray<Entity> sources;
    private ImmutableArray<Entity> targets;

    private GameScreen screen;
    private EntityManager manager;
    private RenderConfig config;
    private ActionQueue actionQueue;

    public GameLogicSystem(int priority, GameScreen screen, EntityManager manager, RenderConfig config, Team[] teams) {
        super(priority);
        this.screen = screen;
        this.manager = manager;
        this.config = config;
        this.actionQueue = new ActionQueue(teams);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        cells = engine.getEntitiesFor(Family.all(CellComponent.class).get());
        spawners = engine.getEntitiesFor(Family.all(SpawnerComponent.class).get());
        creatures = engine.getEntitiesFor(Family.all(CreatureComponent.class).get());
        nothings = engine.getEntitiesFor(Family.all(NothingComponent.class).get());
        sources = engine.getEntitiesFor(Family.all(SelectionSourceComponent.class).get());
        targets = engine.getEntitiesFor(Family.all(SelectionTargetComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (sources.size() > 0) {
            Entity source = sources.first();
            Entity sourceCard = AttachComponent.mapper.get(source).entity;

            if (targets.size() > 0) {
                Entity target = targets.first();
                Entity targetCard = null;
                if (AttachComponent.mapper.has(target)) {
                    targetCard = AttachComponent.mapper.get(target).entity;
                }

                if (targetCard == null || NothingComponent.mapper.has(targetCard)) {
                    if (SpawnerComponent.mapper.has(sourceCard)) {
                        spawn(sourceCard, target);
                    } else if (CreatureComponent.mapper.has(sourceCard)) {
                        move(sourceCard, target);
                    }
                } else {
                    // TODO: process saint
                    //if (AllyComponent.mapper.has(targetCard))
                    attack(sourceCard, targetCard);
                }

                //TODO: if several targets are available
                actionQueue.nextAction();
            } else {
                if (NothingComponent.mapper.has(sourceCard)) {
                    showAvailableBuildings();
                    setSelectable(source);
                } else if (SpawnerComponent.mapper.has(sourceCard)) {
                    setSelectable(combine(
                            without(
                                    adjacent(source, SPAWN_DIRECTIONS),
                                    CreatureComponent.mapper,
                                    SpawnerComponent.mapper,
                                    ResourceComponent.mapper
                            ),
                            Arrays.asList(source)
                    ));
                } else if (isTeammateCreature(sourceCard)) {
                    setSelectable(combine(
                            // TODO: process REAPING and SAINT prefix
                            withoutTeammates(
                                    adjacent(source, ACTION_DIRECTIONS),
                                    SpawnerComponent.mapper,
                                    ResourceComponent.mapper
                            ),
                            Arrays.asList(source)
                    ));
                }
            }
        } else {
            setSelectable(combine(
                    getCellsOfTeammate(nothings),
                    getCellsOfTeammate(spawners),
                    getCellsOfTeammate(creatures)
            ));
        }
    }

    private List<Entity> getCellsOf(Iterable<Entity> entities) {
        List<Entity> result = new ArrayList<>();
        for (Entity entity: entities) {
            if (AttachComponent.mapper.has(entity)) {
                result.add(AttachComponent.mapper.get(entity).entity);
            }
        }
        return result;
    }

    private List<Entity> getCellsOfTeammate(Iterable<Entity> entities) {
        List<Entity> result = new ArrayList<>();
        for (Entity entity: entities) {
            if (AttachComponent.mapper.has(entity) && isTeammate(entity)) {
                result.add(AttachComponent.mapper.get(entity).entity);
            }
        }
        return result;
    }

    private List<Entity> combine(Collection<Entity>... collections) {
        List<Entity> result = new ArrayList<>();
        for (Collection<Entity> collection: collections) {
            result.addAll(collection);
        }
        return result;
    }

    /**
     * @param entity which is a cell
     * @param directions
     * @return list of adjacent cells to the given one
     */
    private List<Entity> adjacent(Entity entity, Direction[] directions) {
        CellComponent component = CellComponent.mapper.get(entity);
        List<Entity> result = new ArrayList<>();
        for (Direction direction: directions) {
            int x = component.getX() + direction.getDx();
            int y = component.getY() + direction.getDy();
            if (x >= 0 && x < config.getFieldWidth() && y >= 0 && y < config.getFieldHeight()) {
                result.add(screen.getField()[y][x]);
            }
        }
        return result;
    }

    private List<Entity> without(List<Entity> entities, ComponentMapper... mappers) {
        Iterator<Entity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            if (!AttachComponent.mapper.has(entity)) continue;
            Entity card = AttachComponent.mapper.get(entity).entity;
            for (ComponentMapper mapper: mappers) {
                if (mapper.has(card)) {
                    iterator.remove();
                    break;
                }
            }
        }
        return entities;
    }

    private List<Entity> withoutTeammates(List<Entity> entities, ComponentMapper... mappers) {
        List<Entity> result = without(entities, mappers);
        Iterator<Entity> iterator = result.iterator();
        while (iterator.hasNext()) {
            if (isTeammateCreature(iterator.next())) {
                iterator.remove();
            }
        }
        return result;
    }

    private boolean isTeammate(Entity card) {
        return TeamComponent.mapper.has(card) && TeamComponent.mapper.get(card).id == actionQueue.getCurrentTeamId();
    }

    private boolean isTeammateCreature(Entity card) {
        return CreatureComponent.mapper.has(card) && TeamComponent.mapper.has(card) && TeamComponent.mapper.get(card).id == actionQueue.getCurrentTeamId();
    }


    private void setSelectable(Entity... entities) {
        setSelectable(Arrays.asList(entities));
    }

    private void setSelectable(List<Entity> entities) {
        for (Entity entity: cells) {
            entity.remove(SelectableComponent.class);
        }

        for (Entity entity: entities) {
            entity.add(new SelectableComponent());
        }
    }

    public void clearSelection() {
        for (int i = 0; i < config.getFieldHeight(); i++) {
            for (int j = 0; j < config.getFieldWidth(); j++) {
                Entity entity = screen.getField()[i][j];
                entity.remove(SelectionSourceComponent.class);
                entity.remove(SelectionTargetComponent.class);
            }
        }
    }

    private void showAvailableBuildings() {
        // TODO: show popup with available buildings
    }

    private void move(Entity card, Entity cell) {
        setSelectable();
        clearSelection();

        CellComponent cellComponent = CellComponent.mapper.get(cell);
        manager.attach(cell, card);
        card.add(new InterpolationPositionComponent(
                Interpolation.fastSlow,
                PositionComponent.mapper.get(card).position,
                PositionComponent.mapper.get(cell).position,
                0.3f
        ));
    }

    private void spawn(Entity card, Entity cell) {
        setSelectable();
        clearSelection();

        CellComponent cellComponent = CellComponent.mapper.get(cell);
        SpawnerComponent.mapper.get(card).spawn(cellComponent.getX(), cellComponent.getY());
    }

    private void attack(Entity source, Entity target) {
        setSelectable();
        clearSelection();

        // TODO: implement later
    }

    private class ActionQueue {

        private Team[] teams;
        private int currentTeamIndex = 0;
        private int currentTeamActions = 0;

        ActionQueue(Team[] teams) {
            this.teams = teams;
        }

        Team getCurrentTeam() {
            return teams[currentTeamIndex];
        }

        int getCurrentTeamId() {
            return teams[currentTeamIndex].getId();
        }

        void nextAction() {
            currentTeamActions++;
            if (currentTeamActions >= getCurrentTeam().getSteps()) {
                nextTeam();
            }
        }

        private void nextTeam() {
            currentTeamIndex++;
            if (currentTeamIndex >= teams.length) {
                currentTeamIndex = 0;
            }
            currentTeamActions = 0;
        }
    }
}
