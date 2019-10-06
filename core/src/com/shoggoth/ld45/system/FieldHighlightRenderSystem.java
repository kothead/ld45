package com.shoggoth.ld45.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.kothead.gdxjam.base.component.PositionComponent;
import com.shoggoth.ld45.component.CellComponent;
import com.shoggoth.ld45.component.SelectableComponent;
import com.shoggoth.ld45.component.SelectedComponent;
import com.shoggoth.ld45.util.RenderConfig;

public class FieldHighlightRenderSystem extends IteratingSystem {

    private ShapeRenderer shapes;
    private RenderConfig renderConfig;

    private Color empty = new Color(0, 1, 0, 1);
    private Color selectable = new Color(0, 0, 1, 1);
    private Color selected = new Color(1, 0, 0, 1);

    public FieldHighlightRenderSystem(int priority, ShapeRenderer renderer, RenderConfig renderConfig) {
        super(Family.all(
                CellComponent.class,
                PositionComponent.class
        ).get(), priority);
        this.shapes = renderer;
        this.renderConfig = renderConfig;
    }

    @Override
    public void update(float deltaTime) {
        shapes.begin(ShapeRenderer.ShapeType.Line);
        super.update(deltaTime);
        shapes.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (SelectedComponent.mapper.has(entity)) {
            shapes.setColor(selected);
        } else if (SelectableComponent.mapper.has(entity)) {
            shapes.setColor(selectable);
        } else {
            shapes.setColor(empty);
        }

        Vector3 position = PositionComponent.mapper.get(entity).position;
        shapes.rect(position.x, position.y, renderConfig.getCardWidth(), renderConfig.getCardHeight());
    }
}
