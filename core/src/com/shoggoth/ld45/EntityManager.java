package com.shoggoth.ld45;

import com.badlogic.ashley.core.Engine;
import com.shoggoth.ld45.screen.GameScreen;
import com.shoggoth.ld45.system.FieldHighlightRenderSystem;
import com.shoggoth.ld45.system.InputSystem;
import com.shoggoth.ld45.util.RenderConfig;

public class EntityManager {

    private Engine engine;
    private GameScreen screen;
    private RenderConfig renderConfig;

    public EntityManager(Engine engine, GameScreen screen, RenderConfig renderConfig) {
        this.engine = engine;
        this.screen = screen;
        this.renderConfig = renderConfig;
        registerSystems();
    }

    public void registerSystems() {
        int priority = 0;
        engine.addSystem(new InputSystem(priority++, screen, renderConfig));
        engine.addSystem(new FieldHighlightRenderSystem(priority++, screen, renderConfig));
    }

    public void dispose() {
        engine.removeAllEntities();
        engine.removeAllSystems();
    }
}
