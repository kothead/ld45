package com.shoggoth.ld45.system;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.math.Vector3;
import com.kothead.gdxjam.base.GdxJam;
import com.kothead.gdxjam.base.screen.BaseScreen;
import com.shoggoth.ld45.Assets;
import com.shoggoth.ld45.util.RenderConfig;
import com.shoggoth.ld45.util.TiledBackgroundSprite;

public class BackgroundRenderSystem extends EntitySystem {

    private BaseScreen screen;
    private RenderConfig config;
    private TiledBackgroundSprite background;

    public BackgroundRenderSystem(int priority, BaseScreen screen, RenderConfig config) {
        super(priority);
        this.screen = screen;
        background = new TiledBackgroundSprite(GdxJam.assets().get(Assets.images.DESKTILE),
                config.getMaxWidth(),
                config.getMaxHeight());
        this.config = config;
    }

    @Override
    public void update(float deltaTime) {
        Vector3 focus = screen.getCamera().position;
        background.setPosition(focus.x - screen.getWorldWidth() / 2f,
                focus.y - screen.getWorldHeight() / 2f);
        background.setOffset(-focus.x, -focus.y);

        screen.batch().begin();
        background.draw(screen.batch());
        screen.batch().end();
    }
}
