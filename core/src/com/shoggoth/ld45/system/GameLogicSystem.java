package com.shoggoth.ld45.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.shoggoth.ld45.component.*;
import com.shoggoth.ld45.screen.GameScreen;

public class GameLogicSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;
    private GameScreen screen;

    public GameLogicSystem(int priority, GameScreen screen) {
        super(priority);
        this.screen = screen;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(Family.all(
                SelectedComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        for (Entity entity : entities) {
            SelectedComponent selectedComponent = entity.getComponent(SelectedComponent.class);
            CellComponent cellComponent = entity.getComponent(CellComponent.class);
            Gdx.app.log("TEST", "type " + selectedComponent.type);
            Entity card = entity.getComponent(AttachComponent.class).entity;
            switch (selectedComponent.type) {
                case SOURCE:
                    if (NothingComponent.mapper.has(card)) {
                        screen.setSelectable(cellComponent.getX(), cellComponent.getY());
                        showAvailableBuildings();
                    } else if (SpawnerComponent.mapper.has(card)) {
                        screen.setSelectable(cellComponent.getX(), cellComponent.getY(), card.getComponent(SpawnerComponent.class).directions);
                    } else if (CreatureComponent.mapper.has(card) && !EnemyComponent.mapper.has(card)) {
                        // set selectable to all enemies in radius
                    }
                    break;
                case TARGET:
                    break;
            }
        }
    }

    private void showAvailableBuildings() {
        // TODO: show popup with available buildings
    }

}
