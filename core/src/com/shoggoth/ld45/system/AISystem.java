package com.shoggoth.ld45.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.shoggoth.ld45.component.*;
import com.shoggoth.ld45.screen.GameScreen;
import com.shoggoth.ld45.util.AITeam;
import com.shoggoth.ld45.util.ActionQueue;
import com.shoggoth.ld45.util.RenderConfig;
import com.shoggoth.ld45.util.Team;

import java.util.Random;

public class AISystem extends EntitySystem {

    private Random random = new Random();

    private GameScreen screen;
    private RenderConfig renderConfig;
    private ActionQueue actionQueue;

    private ImmutableArray<Entity> selectables;

    public AISystem(int priority, GameScreen screen, RenderConfig renderConfig) {
        super(priority);
        this.screen = screen;
        this.renderConfig = renderConfig;
        this.actionQueue = screen.getActionQueue();
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        selectables = engine.getEntitiesFor(Family.all(SelectableComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (actionQueue.getCurrentTeam().isPlayer()) {
            return;
        }

        AITeam team = (AITeam) actionQueue.getCurrentTeam();

        if (team.getUnitQueue().size() < team.getUnitsLimit()) {
            Vector2 position = getRandomEmptyCoordinate();
            if (position.x != -1 || position.y != -1) {
                Entity unit = addRandomUnit(team, position);
                team.getUnitQueue().add(unit);
                actionQueue.nextAction();
                return;
            }
        }

        Entity card = team.getActiveUnit();
        Entity cell = AttachComponent.mapper.get(card).entity;
        if (SelectableComponent.mapper.has(cell) && !SelectionSourceComponent.mapper.has(cell)) {
            cell.add(new SelectionSourceComponent());
            return;
        }

        Entity enemy = findEnemyUnitInAdjacentCells(team);
        if (enemy != null) {
            enemy.add(new SelectionTargetComponent());
            team.activateNext();
            return;
        }

        goToNearestUnit();
        // TODO: find nearest unit and go there
    }

    private void goToNearestUnit() {
        int[][] path = new int[renderConfig.getFieldHeight()][renderConfig.getFieldWidth()];
        int d = 0;

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

        int perimeter = 2 * renderConfig.getFieldWidth() + 2 * renderConfig.getFieldHeight();
        int attempts = 0;
        while ((position.x == -1 || position.y == -1) && attempts < perimeter) {
            int randomInt = random.nextInt(perimeter);
            int x = 0;
            int y = 0;
            if (randomInt < renderConfig.getFieldWidth()) {
                x = randomInt;
            } else if (randomInt < renderConfig.getFieldWidth() + renderConfig.getFieldHeight()) {
                x = renderConfig.getFieldWidth() - 1;
                y = randomInt - renderConfig.getFieldWidth();
            } else if (randomInt < renderConfig.getFieldWidth() * 2 + renderConfig.getFieldHeight()) {
                x = renderConfig.getFieldWidth() - 1;
                y = randomInt - renderConfig.getFieldWidth() - renderConfig.getFieldHeight();
            } else {
                y = randomInt - renderConfig.getFieldHeight() - renderConfig.getFieldWidth() * 2;
            }

            Entity entity = screen.getField()[y][x];
            if (!AttachComponent.mapper.has(entity) || NothingComponent.mapper.has(AttachComponent.mapper.get(entity).entity)) {
                position.set(x, y);
            }

            attempts++;
        }
        return position;
    }
}
