package com.shoggoth.ld45.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.shoggoth.ld45.screen.GameScreen;
import com.shoggoth.ld45.util.RenderConfig;

public class FieldHighlightRenderSystem extends EntitySystem {

    private GameScreen screen;
    private ShapeRenderer shapes;
    private RenderConfig renderConfig;

    public FieldHighlightRenderSystem(int priority, GameScreen screen, RenderConfig renderConfig) {
        super(priority);
        this.screen = screen;
        this.shapes = screen.shapes();
        this.renderConfig = renderConfig;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        Entity[][] field = screen.getField();

        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(0, 1, 0, 1);
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                int x = i * renderConfig.getCardWidth() + renderConfig.getPadding() * i + renderConfig.getMargin();
                int y = j * renderConfig.getCardHeight() + renderConfig.getPadding() * j + renderConfig.getMargin();
                shapes.rect(x, y, renderConfig.getCardWidth(), renderConfig.getCardHeight());
            }
        }
        shapes.end();
    }
}
