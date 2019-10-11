package com.shoggoth.ld45.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.kothead.gdxjam.base.GdxJam;
import com.kothead.gdxjam.base.component.PositionComponent;
import com.kothead.gdxjam.base.util.Direction;
import com.shoggoth.ld45.Assets;
import com.shoggoth.ld45.EntityManager;
import com.shoggoth.ld45.LD45;
import com.shoggoth.ld45.component.*;
import com.shoggoth.ld45.component.prefix.FuriousComponent;
import com.shoggoth.ld45.screen.GameScreen;
import com.shoggoth.ld45.util.ActionQueue;
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

    public static Direction[] BUFF_SPAWN_DIRECTIONS = Direction.getDirections();

    private ImmutableArray<Entity> cells;
    private ImmutableArray<Entity> spawners;
    private ImmutableArray<Entity> creatures;
    private ImmutableArray<Entity> nothings;

    private ImmutableArray<Entity> sources;
    private ImmutableArray<Entity> targets;

    private ImmutableArray<Entity> cards;

    private GameScreen screen;
    private EntityManager manager;
    private RenderConfig config;
    private ActionQueue actionQueue;
    private boolean pendingAction = false;

    public GameLogicSystem(int priority, GameScreen screen, EntityManager manager, RenderConfig config, Team[] teams) {
        super(priority);
        this.screen = screen;
        this.manager = manager;
        this.config = config;
        this.actionQueue = screen.getActionQueue();
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
        cards = engine.getEntitiesFor(Family.all(CardComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (getAmountOfCardsForTeam(1) == 0) {
            ((LD45)screen.getGame()).showGameScreen();
        }

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
                        source = target;
                    }
                } else {
                    // TODO: process saint
                    //if (AllyComponent.mapper.has(targetCard))
                    attack(sourceCard, targetCard);
                }

                if (!SelectionSourceComponent.mapper.has(source)) {
                    Gdx.app.log("Test", "Lost selection source component");
                    actionQueue.nextAction();
                }
            } else {
                if (NothingComponent.mapper.has(sourceCard)) {
                    showCardSelector(source, sourceCard);
                    setSelectable(source);
                } else if (SpawnerComponent.mapper.has(sourceCard)) {
                    if (setSelectable(combine(
                            without(
                                    adjacent(source, SPAWN_DIRECTIONS),
                                    CreatureComponent.mapper,
                                    SpawnerComponent.mapper,
                                    ResourceComponent.mapper
                            ),
                            Arrays.asList(source)
                    )).size() == 1 && pendingAction) {
                        Gdx.app.log("test", "SPAWNER");
                        SpawnerComponent.mapper.get(sourceCard).spawned = 0;
                        stopPendingAction();
                    }
                } else if (isTeammateCreature(sourceCard)) {
                    if (setSelectable(combine(
                            // TODO: process REAPING and SAINT prefix
                            withoutTeammates(
                                    adjacent(source, ACTION_DIRECTIONS)
                            ),
                            Arrays.asList(source)
                    )).size() == 1 && pendingAction) {
                        Gdx.app.log("test", "NOTHING TO SELECT AS TARGET");
                        stopPendingAction();
                    }
                }
            }
        } else {
            if (pendingAction) {
                Gdx.app.log("test", "PENDING ACTION WITHOUT SOURCE");
                stopPendingAction();
            }

            if (setSelectable(combine(
                    getCellsOfTeammate(nothings),
                    getCellsOfTeammate(spawners),
                    getCellsOfTeammate(creatures)
            )).size() == 0) {
                Gdx.app.log("test", "NOTHING TO SELECT AS SOURCE FOR PLAYER");
                stopPendingAction();
            };
        }
    }

    private void stopPendingAction() {
        Gdx.app.log("Test", "stopPendingAction()");
        pendingAction = false;
        actionQueue.nextAction();
        clearSelection();
    }

    private List<Entity> getCellsOf(Iterable<Entity> entities) {
        List<Entity> result = new ArrayList<Entity>();
        for (Entity entity: entities) {
            if (AttachComponent.mapper.has(entity)) {
                result.add(AttachComponent.mapper.get(entity).entity);
            }
        }
        return result;
    }

    private List<Entity> getCellsOfTeammate(Iterable<Entity> entities) {
        List<Entity> result = new ArrayList<Entity>();
        for (Entity entity: entities) {
            if (AttachComponent.mapper.has(entity) && isTeammate(entity)) {
                result.add(AttachComponent.mapper.get(entity).entity);
            }
        }
        return result;
    }

    private List<Entity> combine(Collection<Entity>... collections) {
        List<Entity> result = new ArrayList<Entity>();
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
        List<Entity> result = new ArrayList<Entity>();
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
            Entity entity = iterator.next();
            if (!AttachComponent.mapper.has(entity)) continue;
            Entity card = AttachComponent.mapper.get(entity).entity;
            if (isTeammate(card) && !NothingComponent.mapper.has(card)) {
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

    private List<Entity> setSelectable(Entity... entities) {
        return setSelectable(Arrays.asList(entities));
    }

    private List<Entity> setSelectable(List<Entity> entities) {
        for (Entity entity: cells) {
            entity.remove(SelectableComponent.class);
        }

        for (Entity entity: entities) {
            entity.add(new SelectableComponent());
        }

        return entities;
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

    private void showCardSelector(Entity cell, Entity card) {
        setSelectable();
        clearSelection();

        boolean hasResource = false;
        for (Entity entity: adjacent(cell, BUFF_SPAWN_DIRECTIONS)) {
            if (AttachComponent.mapper.has(entity)
                    && ResourceComponent.mapper.has(AttachComponent.mapper.get(entity).entity)) {
                hasResource = true;
                break;
            }
        }

        int teamId = actionQueue.getCurrentTeamId();
        List<Entity> cards = new ArrayList<Entity>();
        cards.add(manager.addBloodRiver(teamId));
        cards.add(manager.addCursedGround(teamId));
        cards.add(manager.addDemonicPresence(teamId));
        if (hasResource) {
            cards.add(manager.addAbyss(teamId));
            cards.add(manager.addCemetery(teamId));
            cards.add(manager.addCrypt(teamId));
        }
        screen.showCardSelection(card, cards);
    }

    private void move(final Entity card, final Entity cell) {
        manager.pause(FieldHighlightRenderSystem.class);
        manager.pause(AISystem.class);
        manager.pause(InputSystem.class);
        manager.pause(GameLogicSystem.class);
        setSelectable();
        clearSelection();

        final Entity oldNothing = manager.detachOldNothing(cell);
        manager.attach(cell, card);

        Vector3 origin = new Vector3(PositionComponent.mapper.get(card).position);
        origin.z = 1.0f;

        InterpolationPositionComponent interpolationPosition = new InterpolationPositionComponent(
                Interpolation.fastSlow,
                origin,
                PositionComponent.mapper.get(cell).position,
                actionQueue.getCurrentTeam().isPlayer() ? 0.0f : 1.0f,
                0.3f
        );
        interpolationPosition.callback = new InterpolationPositionComponent.InterpolationCallback() {
            @Override
            public void onInterpolationFinished(Entity entity) {
                GdxJam.assets().get(Assets.sounds.MOVE).play();
                if (oldNothing != null) getEngine().removeEntity(oldNothing);
                manager.resume(FieldHighlightRenderSystem.class);
                manager.resume(AISystem.class);
                manager.resume(InputSystem.class);
                manager.resume(GameLogicSystem.class);
            }
        };
        card.add(interpolationPosition);

        FuriousComponent furious = FuriousComponent.mapper.get(card);
        if (furious != null) {
            furious.done++;
        }
        if (furious != null && furious.done < furious.count) {
            pendingAction = true;
            cell.add(new SelectionSourceComponent());
        } else {
            if (furious != null) furious.done = 0;
        }
    }

    private void spawn(Entity card, Entity cell) {
        GdxJam.assets().get(Assets.sounds.SPAWN).play();
        manager.pause(FieldHighlightRenderSystem.class);
        manager.pause(AISystem.class);
        manager.pause(InputSystem.class);
        manager.pause(GameLogicSystem.class);
        setSelectable();
        cell.remove(SelectionTargetComponent.class);

        final Entity oldNothing = manager.detachOldNothing(cell);

        CellComponent cellComponent = CellComponent.mapper.get(cell);
        SpawnerComponent spawnerComponent = SpawnerComponent.mapper.get(card);
        Entity spawned = spawnerComponent.spawn(cellComponent.getX(), cellComponent.getY());
        InterpolationPositionComponent component = new InterpolationPositionComponent(
                Interpolation.fastSlow,
                PositionComponent.mapper.get(card).position,
                PositionComponent.mapper.get(spawned).position,
                actionQueue.getCurrentTeam().isPlayer() ? 0.0f : 1.0f,
                1.0f
        );
        component.from.z = -1.0f;
        component.callback = new InterpolationPositionComponent.InterpolationCallback() {
            @Override
            public void onInterpolationFinished(Entity entity) {
                if (oldNothing != null) getEngine().removeEntity(oldNothing);
                manager.resume(FieldHighlightRenderSystem.class);
                manager.resume(AISystem.class);
                manager.resume(InputSystem.class);
                manager.resume(GameLogicSystem.class);
            }
        };
        spawned.add(component);

        spawnerComponent.spawned++;
        if (spawnerComponent.spawned < spawnerComponent.spawnCount) {
            pendingAction = true;
        } else {
            pendingAction = false;
            clearSelection();
            spawnerComponent.spawned = 0;
        }
    }

    private void attack(final Entity source, final Entity target) {
        manager.pause(FieldHighlightRenderSystem.class);
        manager.pause(AISystem.class);
        manager.pause(InputSystem.class);
        manager.pause(GameLogicSystem.class);
        setSelectable();
        Entity cell = AttachComponent.mapper.get(target).entity;
        cell.remove(SelectionTargetComponent.class);

        Vector3 origin = new Vector3(PositionComponent.mapper.get(source).position);

        Vector3 aim = new Vector3(PositionComponent.mapper.get(target).position);
        aim.x += (origin.x - aim.x) / 2;
        aim.y += (origin.y - aim.y) / 2;
        aim.z = 1.0f;

        InterpolationPositionComponent component = new InterpolationPositionComponent(
                Interpolation.slowFast,
                origin,
                aim,
                actionQueue.getCurrentTeam().isPlayer() ? 0.0f : 1.0f,
                0.10f
        );
        component.next = new InterpolationPositionComponent(
                Interpolation.fastSlow,
                aim,
                origin,
                0,
                0.20f
        );
        component.callback = new InterpolationPositionComponent.InterpolationCallback() {
            @Override
            public void onInterpolationFinished(Entity entity) {
                GdxJam.assets().get(Assets.sounds.HIT).play();
                GdxJam.assets().get(Assets.sounds.DAMAGE).play();
            }
        };
        component.next.callback = new InterpolationPositionComponent.InterpolationCallback() {
            @Override
            public void onInterpolationFinished(Entity entity) {
                HealthComponent component = HealthComponent.mapper.get(target);
                component.health -= DamageComponent.mapper.get(source).damage;
                if (component.health <= 0) {
                    manager.removeEntity(target);
                    GdxJam.assets().get(Assets.sounds.DEATH).play();
                }
                manager.resume(FieldHighlightRenderSystem.class);
                manager.resume(AISystem.class);
                manager.resume(InputSystem.class);
                manager.resume(GameLogicSystem.class);
            }
        };
        source.add(component);

        FuriousComponent furious = FuriousComponent.mapper.get(source);
        if (furious != null) {
            furious.done++;
        }
        if (furious != null && furious.done < furious.count) {
            pendingAction = true;
        } else {
            if (furious != null) furious.done = 0;
            clearSelection();
        }
    }

    private int getAmountOfCardsForTeam(int teamId) {
        int amount = 0;
        for (Entity card : cards) {
            if (TeamComponent.mapper.get(card).id == teamId) {
                amount++;
            }
        }
        return amount;
    }

//    public void checkFor(Direction[] directions, ComponentMapper... mappers) {
//        Entity[][] entities = screen.getField();
//            for (Direction direction: NOTHING_SPAWN_DIRECTIONS) {
//                int x = j + direction.getDx();
//                int y = i + direction.getDy();
//                if (x >=0 && x < getFieldWidth()
//                        && y >= 0 && y < config.get ) {
//
//                }
//            }
//    }
}
