package com.shoggoth.ld45;

import com.badlogic.ashley.core.Engine;
import com.kothead.gdxjam.base.screen.BaseScreen;

public class EntityManager {

    private Engine engine;
    private BaseScreen screen;

    public EntityManager(Engine engine, BaseScreen screen) {
        this.engine = engine;
        this.screen = screen;
        registerSystems();
    }

    public void registerSystems() {

    }

    public void dispose() {
        engine.removeAllEntities();
        engine.removeAllSystems();
    }
}
