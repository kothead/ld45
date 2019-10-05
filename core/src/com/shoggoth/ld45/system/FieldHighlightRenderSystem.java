package com.shoggoth.ld45.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.shoggoth.ld45.component.SelectableComponent;
import com.shoggoth.ld45.component.SelectedComponent;
import com.shoggoth.ld45.screen.GameScreen;
import com.shoggoth.ld45.util.RenderConfig;

public class FieldHighlightRenderSystem extends EntitySystem {

    private GameScreen screen;
    private ShapeRenderer shapes;
    private RenderConfig renderConfig;

    private Color empty = new Color(0, 1, 0, 1);
    private Color selectable = new Color(0, 0, 1, 1);
    private Color selected = new Color(1, 0, 0, 1);

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
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                int y = i * renderConfig.getCardHeight() + renderConfig.getPadding() * i + renderConfig.getMargin();
                int x = j * renderConfig.getCardWidth() + renderConfig.getPadding() * j + renderConfig.getMargin();

                Entity entity = field[i][j];
                if (entity != null) {
                    if (SelectedComponent.mapper.has(entity)) {
                        shapes.setColor(selected);
                    } else if (SelectableComponent.mapper.has(entity)) {
                        shapes.setColor(selectable);
                    }
                } else {
                    shapes.setColor(empty);
                }
                shapes.rect(x, y, renderConfig.getCardWidth(), renderConfig.getCardHeight());
            }
        }
        shapes.end();
    }
}
