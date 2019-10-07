package com.shoggoth.ld45.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.kothead.gdxjam.base.util.Direction;
import com.shoggoth.ld45.component.*;
import com.shoggoth.ld45.screen.GameScreen;
import com.shoggoth.ld45.util.AITeam;
import com.shoggoth.ld45.util.ActionQueue;
import com.shoggoth.ld45.util.RenderConfig;
import com.shoggoth.ld45.util.Team;

import java.util.*;

public class AISystem extends SortedIteratingSystem {

    private Random random = new Random();

    private GameScreen screen;
    private RenderConfig config;
    private ActionQueue actionQueue;

    private ImmutableArray<Entity> selectables;
    private ImmutableArray<Entity> teamCards;

    private int lastUnitId = -1;
    private List<Entity> unitQueue = new ArrayList<>();

    public AISystem(int priority, GameScreen screen, RenderConfig renderConfig) {
        super(Family.all(TeamComponent.class).get(),
                new ZComparator(),priority);
        this.screen = screen;
        this.config = renderConfig;
        this.actionQueue = screen.getActionQueue();
    }

    private static class ZComparator implements Comparator<Entity> {
        @Override
        public int compare(Entity entity1, Entity entity2) {
            return (int) Math.signum(TeamComponent.mapper.get(entity1).componentId
                    - TeamComponent.mapper.get(entity2).componentId);
        }
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        selectables = engine.getEntitiesFor(Family.all(SelectableComponent.class).get());
        teamCards = engine.getEntitiesFor(Family.all(TeamComponent.class).exclude(NothingComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        if (actionQueue.getCurrentTeam().isPlayer()) {
            return;
        }

        Gdx.app.log("Test", "---------------------");

        unitQueue.clear();
        super.update(deltaTime);

        AITeam team = (AITeam) actionQueue.getCurrentTeam();

        if (unitQueue.isEmpty()) {
            Gdx.app.log("Test", "No AI units, a new one should be created");
        } else {
            Gdx.app.log("Test", unitQueue.size() + " / " + team.getUnitsLimit() + " units");
        }

        //spawn new units
        if (unitQueue.size() < team.getUnitsLimit()) {
            Vector2 position = getRandomEmptyCoordinate();
            if (position.x != -1 || position.y != -1) {
                addRandomUnit(team, position);
                Gdx.app.log("Test", "New unit at " + position.x + ", " + position.y);
                actionQueue.nextAction();
                return;
            }
        }

        //select active unit
        Entity card = getActiveUnit();
        Gdx.app.log("Test", "Card " + TeamComponent.mapper.get(card).componentId + " is at work");
        Entity cell = AttachComponent.mapper.get(card).entity;
        if (SelectableComponent.mapper.has(cell) && !SelectionSourceComponent.mapper.has(cell)) {
            cell.add(new SelectionSourceComponent());
            Gdx.app.log("Test", "Unit at " + CellComponent.mapper.get(cell).getX() + ", " + CellComponent.mapper.get(cell).getY() + " is set to active");
            return;
        }

        // attack
        Entity enemy = findEnemyUnitInAdjacentCells(team);
        if (enemy != null) {
            enemy.add(new SelectionTargetComponent());
            Gdx.app.log("Test", "Unit at " + CellComponent.mapper.get(enemy).getX() + ", " + CellComponent.mapper.get(enemy).getY() + " is set to target");
            activateNext();
            return;
        }

        // go
        List<Vector2> path = getPathToNearestEnemyCard(cell, team);
        if (path.isEmpty()) {
            cell.remove(SelectionSourceComponent.class);
            activateNext();
            Gdx.app.log("Test", "Path is empty, next action");
            // TODO: detect if all unites are passed
            actionQueue.nextAction();
            return;
        }

        int x = (int) path.get(path.size() - 1).x;
        int y = (int) path.get(path.size() - 1).y;
        screen.getField()[y][x].add(new SelectionTargetComponent());
        activateNext();
        Gdx.app.log("Test", "Moving: " + x + ", " + y + " is set to target");
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AITeam team = (AITeam) actionQueue.getCurrentTeam();

        if (TeamComponent.mapper.get(entity).id == team.getId()) {
            unitQueue.add(entity);
        }
    }

    private List<Vector2> getPathToNearestEnemyCard(Entity cell, AITeam team) {
        List<Vector2> resultPath = new ArrayList<>();
        CellComponent cellComponent = CellComponent.mapper.get(cell);

        int[][] path = new int[config.getFieldHeight()][config.getFieldWidth()];
        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path[i].length; j++) {
                path[i][j] = Integer.MAX_VALUE;
            }
        }

        Queue<Vector2> queue = new ArrayDeque<>();
        queue.add(new Vector2(cellComponent.getX(), cellComponent.getY()));

        path[cellComponent.getY()][cellComponent.getX()] = 0;

        while (!queue.isEmpty()) {
            Vector2 point = queue.poll();
            for (Direction direction: GameLogicSystem.ACTION_DIRECTIONS) {
                int x = (int) point.x + direction.getDx();
                int y = (int) point.y + direction.getDy();
                if (x >= 0 && x < config.getFieldWidth() && y >= 0 && y < config.getFieldHeight()) {
                    if (path[y][x] == Integer.MAX_VALUE && isValidToMove(x, y, team)) {
                        path[y][x] = path[(int) point.y][(int) point.x] + 1;
                        queue.add(new Vector2(x, y));
                    }
                }
            }
        }

        List<Entity> enemies = getCellsWithEnemies(team);

        if (enemies.isEmpty()) {
            Gdx.app.log("Test", "No enemies found");
            // TODO: AI wins
        }

        int minPath = Integer.MAX_VALUE;
        Entity target = null;

        for (Entity enemyCell : enemies) {
            CellComponent cellComponent1 = CellComponent.mapper.get(enemyCell);
            int x = cellComponent1.getX();
            int y = cellComponent1.getY();
            if (minPath > path[y][x]) {
                minPath = path[y][x];
                target = enemyCell;
            }
        }

        if (target == null) {
            Gdx.app.log("Test", "No target");
            activateNext();
            return resultPath;
        }

        Gdx.app.log("Test", "Target is at " + CellComponent.mapper.get(target).getX() + " " + CellComponent.mapper.get(target).getY());

        Vector2 position = new Vector2(CellComponent.mapper.get(target).getX(), CellComponent.mapper.get(target).getY());
        while (path[(int) position.y][(int)position.x] != 0) {
            for (Direction direction: GameLogicSystem.ACTION_DIRECTIONS) {
                int x = (int) position.x + direction.getDx();
                int y = (int) position.y + direction.getDy();
                if (x >= 0 && x < config.getFieldWidth() && y >= 0 && y < config.getFieldHeight() &&
                        path[(int) position.y][(int) position.x] - path[y][x] == 1) {
                    if (path[y][x] != 0) {
                        resultPath.add(new Vector2(x, y));
                    }
                    position.set(x, y);
                    break;
                }
            }
        }

        String s = "";
        for (Vector2 vector2 : resultPath) {
            s += "[" + vector2.x + ", " + vector2 + "], ";
        }
        Gdx.app.log("Test", "Path: " + s);

        return resultPath;
    }

    private boolean isValidToMove(int x, int y, AITeam team) {
        Entity cell = screen.getField()[y][x];
        return !(AttachComponent.mapper.has(cell) && TeamComponent.mapper.get(AttachComponent.mapper.get(cell).entity).id == team.getId());
    }

    private Entity addRandomUnit(AITeam team, Vector2 position) {
        int unitType = random.nextInt(3);
        Entity unit;
        switch (unitType) {
            case 0:
                unit = screen.getEntityManager().addSkeleton((int) position.x, (int) position.y, team.getId());
                break;
            case 1:
                unit = screen.getEntityManager().addZombie((int) position.x, (int) position.y, team.getId());
                break;
            case 2:
            default:
                unit = screen.getEntityManager().addDemon((int) position.x, (int) position.y, team.getId());
                break;
        }
        return unit;
    }

    private Entity findEnemyUnitInAdjacentCells(Team team) {
        for (Entity entity : selectables) {
            if (AttachComponent.mapper.has(entity)) {
                Entity card = AttachComponent.mapper.get(entity).entity;
                if (!NothingComponent.mapper.has(card) && TeamComponent.mapper.get(card).id != team.getId()) {
                   return entity;
                }
            }
        }
        return null;
    }

    private Vector2 getRandomEmptyCoordinate() {
        Vector2 position = new Vector2(-1, -1);

        int perimeter = 2 * config.getFieldWidth() + 2 * config.getFieldHeight();
        int attempts = 0;
        while ((position.x == -1 || position.y == -1) && attempts < perimeter) {
            int randomInt = random.nextInt(perimeter);
            int x = 0;
            int y = 0;
            if (randomInt < config.getFieldWidth()) {
                x = randomInt;
            } else if (randomInt < config.getFieldWidth() + config.getFieldHeight()) {
                x = config.getFieldWidth() - 1;
                y = randomInt - config.getFieldWidth();
            } else if (randomInt < config.getFieldWidth() * 2 + config.getFieldHeight()) {
                x = config.getFieldWidth() - 1;
                y = randomInt - config.getFieldWidth() - config.getFieldHeight();
            } else {
                y = randomInt - config.getFieldHeight() - config.getFieldWidth() * 2;
            }

            Entity entity = screen.getField()[y][x];
            if (!AttachComponent.mapper.has(entity) || NothingComponent.mapper.has(AttachComponent.mapper.get(entity).entity)) {
                position.set(x, y);
            }

            attempts++;
        }
        return position;
    }

    private List<Entity> getCellsWithEnemies(Team team) {
        List<Entity> result = new ArrayList<>();
        for (Entity entity : teamCards) {
            if (TeamComponent.mapper.get(entity).id != team.getId()) {
                result.add(AttachComponent.mapper.get(entity).entity);
            }
        }
        return result;
    }

    public void activateNext() {
        lastUnitId++;
    }

    public Entity getActiveUnit() {
        if (unitQueue.isEmpty()) {
            return null;
        }

        for (Entity entity : unitQueue) {
            if (TeamComponent.mapper.get(entity).componentId >= lastUnitId) {
                return entity;
            }
        }

        lastUnitId = TeamComponent.mapper.get(unitQueue.get(0)).componentId;
        return unitQueue.get(0);
    }
}
