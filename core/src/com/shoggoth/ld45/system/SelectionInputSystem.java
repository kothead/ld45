package com.shoggoth.ld45.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kothead.gdxjam.base.component.PositionComponent;
import com.kothead.gdxjam.base.component.SpriteComponent;
import com.shoggoth.ld45.component.CardComponent;
import com.shoggoth.ld45.component.SelectableComponent;
import com.shoggoth.ld45.component.SelectionSourceComponent;
import com.shoggoth.ld45.component.SelectionTargetComponent;
import com.shoggoth.ld45.screen.GameScreen;
import com.shoggoth.ld45.util.RenderConfig;

public class SelectionInputSystem extends EntitySystem implements InputProcessor {

    private GameScreen screen;

    private RenderConfig renderConfig;
    private Rectangle cameraLimits;

    private ImmutableArray<Entity> selectableCards;

    private Vector2 lastLeftTouch = new Vector2();
    private boolean isClicked = false;

    public SelectionInputSystem(int priority, GameScreen screen, RenderConfig renderConfig) {
        super(priority);
        this.screen = screen;
        this.renderConfig = renderConfig;
        cameraLimits = new Rectangle(0, 0, renderConfig.getMaxWidth(), renderConfig.getMaxHeight());
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.screen.addInputProcessor(this);

        selectableCards = engine.getEntitiesFor(Family.all(
                CardComponent.class,
                SelectableComponent.class
        ).get());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (isClicked) {
            boolean selected = false;
            for (Entity entity: selectableCards) {
                Vector3 position = PositionComponent.mapper.get(entity).position;
                Sprite sprite = SpriteComponent.mapper.get(entity).sprite;

                float x = screen.getCamera().position.x - screen.getWorldWidth() / 2.0f + lastLeftTouch.x;
                float y = screen.getCamera().position.y + screen.getWorldHeight() / 2.0f - lastLeftTouch.y;

                if (x >= position.x && x < position.x + sprite.getWidth()
                        && y >= position.y && y < position.y + sprite.getHeight()) {
                    selected = true;
                    screen.selectCard(entity);
                    break;
                }
            }
            if (!selected) screen.selectCard(null);

            isClicked = false;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (!checkProcessing()) return false;

        if (button == Input.Buttons.LEFT) {
            isClicked = true;
            lastLeftTouch.set(screenX, screenY);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
