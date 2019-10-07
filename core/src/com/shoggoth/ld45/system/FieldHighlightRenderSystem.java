package com.shoggoth.ld45.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.kothead.gdxjam.base.GdxJam;
import com.kothead.gdxjam.base.component.PositionComponent;
import com.kothead.gdxjam.base.screen.BaseScreen;
import com.shoggoth.ld45.Assets;
import com.shoggoth.ld45.component.CellComponent;
import com.shoggoth.ld45.component.SelectableComponent;
import com.shoggoth.ld45.component.SelectionSourceComponent;
import com.shoggoth.ld45.screen.GameScreen;
import com.shoggoth.ld45.util.RenderConfig;

public class FieldHighlightRenderSystem extends IteratingSystem {

    private static final float ALPHA_FROM = 0.0f;
    private static final float ALPHA_TO = 1.0f;
    private static final float BLINK_ANIMATION_DURATION = 0.5f;
    private static final float THRESHOLD = 0.1f;

    private GameScreen screen;
    private Sprite border;
    private RenderConfig renderConfig;
    private Interpolation forwardsInterpolation;
    private Interpolation backwardsInterpolation;
    private float elapsed = 0.0f;
    private float alpha;
    private boolean isGoindBack = false;
    private boolean stopping = false;

    private boolean[][] highlights;

    public FieldHighlightRenderSystem(int priority, GameScreen screen, RenderConfig renderConfig) {
        super(Family.all(
                CellComponent.class,
                PositionComponent.class
        ).get(), priority);
        this.screen = screen;
        this.renderConfig = renderConfig;

        highlights = new boolean[renderConfig.getFieldHeight()][renderConfig.getFieldWidth()];

        forwardsInterpolation = Interpolation.smoother;
        backwardsInterpolation = Interpolation.slowFast;
        border = new Sprite(GdxJam.assets().get(Assets.images.SELECTOR));
    }

    public void stopSlowly() {
        stopping = true;
    }

    @Override
    public void setProcessing(boolean processing) {
        super.setProcessing(processing);
        if (processing == false) {
            elapsed = 0.0f;
            alpha = ALPHA_FROM;
            isGoindBack = false;
            for (int i = 0; i < renderConfig.getFieldWidth(); i++) {
                for (int j = 0; j < renderConfig.getFieldHeight(); j++) {
                    highlights[i][j] = false;
                }
            }
        }
        stopping = false;
    }

    @Override
    public void update(float deltaTime) {
        elapsed += deltaTime;
        if (elapsed > BLINK_ANIMATION_DURATION) {
            isGoindBack = !isGoindBack;
            elapsed -= BLINK_ANIMATION_DURATION;
            if (stopping && !isGoindBack) {
                setProcessing(false);
            }
        }
        alpha = isGoindBack
                ? backwardsInterpolation.apply(ALPHA_TO, ALPHA_FROM, elapsed / BLINK_ANIMATION_DURATION)
                : forwardsInterpolation.apply(ALPHA_FROM, ALPHA_TO, elapsed / BLINK_ANIMATION_DURATION);
        if (alpha < THRESHOLD && stopping) {
            setProcessing(false);
        }

        if (alpha < THRESHOLD) {
            super.update(deltaTime);
        }

        screen.batch().begin();
        for (int i = 0; i < renderConfig.getFieldHeight(); i++) {
            for (int j = 0; j < renderConfig.getFieldWidth(); j++) {
                if (highlights[i][j]) {
                    Entity cell = screen.getField()[i][j];
                    Vector3 position = PositionComponent.mapper.get(cell).position;
                    border.setPosition(position.x, position.y);
                    border.draw(screen.batch(), alpha);
                }
            }
        }
        screen.batch().end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CellComponent cell = CellComponent.mapper.get(entity);
        highlights[cell.getY()][cell.getX()] = SelectionSourceComponent.mapper.has(entity)
                || SelectableComponent.mapper.has(entity);
    }
}
