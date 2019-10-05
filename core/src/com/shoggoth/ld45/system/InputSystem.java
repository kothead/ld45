package com.shoggoth.ld45.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.shoggoth.ld45.component.SelectableComponent;
import com.shoggoth.ld45.component.SelectedComponent;
import com.shoggoth.ld45.screen.GameScreen;
import com.shoggoth.ld45.util.RenderConfig;

public class InputSystem extends EntitySystem implements InputProcessor {

    private GameScreen screen;

    private RenderConfig renderConfig;
    private Rectangle cameraLimits;

    private Vector2 lastTouch = new Vector2();
    private Vector2 delta = new Vector2(0, 0);
    private boolean isDragging = false;

    public InputSystem(int priority, GameScreen screen, RenderConfig renderConfig) {
        super(priority);
        this.screen = screen;
        this.renderConfig = renderConfig;
        cameraLimits = new Rectangle(0, 0, renderConfig.getMaxWidth(), renderConfig.getMaxHeight());
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.screen.addInputProcessor(this);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (isDragging) {
            Camera camera = screen.getCamera();

            float x = -delta.x;
            float y = delta.y;

            x = screen.getCamera().position.x + x;
            y = screen.getCamera().position.y + y;

            x = Math.max(x, cameraLimits.x + camera.viewportWidth / 2f);
            x = Math.min(x, cameraLimits.x + cameraLimits.width - camera.viewportWidth / 2f);

            y = Math.max(y, cameraLimits.y + camera.viewportHeight / 2f);
            y = Math.min(y, cameraLimits.y + cameraLimits.height - camera.viewportHeight / 2f);

            screen.getCamera().position.set(x, y, 0);
            screen.getCamera().update();

            delta.setZero();
        }

        screen.shapes().setProjectionMatrix(screen.getCamera().combined);
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
        if (button == Input.Buttons.LEFT) {
            Vector3 position = countPosition(screenX, screenY);
            if (position.x >= 0 && position.y >= 0) {
                Entity entity = screen.getField()[(int) position.y][(int) position.x];
                if (entity != null && SelectableComponent.mapper.has(entity)) {
                    if (SelectedComponent.mapper.has(entity)) {
                        entity.remove(SelectedComponent.class);
                    } else {
                        entity.add(new SelectedComponent());
                    }
                }
            }
        } else if (button == Input.Buttons.RIGHT) {
            isDragging = true;
            lastTouch.set(screenX, screenY);
        }
        return false;
    }

    private Vector3 countPosition(int screenX, int screenY) {
        Vector3 coordinates = new Vector3(screenX, screenY,0);
        Vector3 position = new Vector3(-1, -1,0);
        screen.getCamera().unproject(coordinates);
        if (coordinates.x >= renderConfig.getMargin() &&
                coordinates.x < renderConfig.getMaxWidth() - renderConfig.getMargin() &&
                coordinates.y >= renderConfig.getMargin() &&
                coordinates.y < renderConfig.getMaxHeight() - renderConfig.getMargin()) {
            float nx = (coordinates.x - renderConfig.getMargin()) / (renderConfig.getPadding() + renderConfig.getCardWidth());
            float px = (coordinates.x - renderConfig.getMargin()) % (renderConfig.getPadding() + renderConfig.getCardWidth());
            float ny = (coordinates.y - renderConfig.getMargin()) / (renderConfig.getPadding() + renderConfig.getCardHeight());
            float py = (coordinates.y - renderConfig.getMargin()) % (renderConfig.getPadding() + renderConfig.getCardHeight());
            if (px <= renderConfig.getCardWidth() && py <= renderConfig.getCardHeight()) {
                position.x = nx;
                position.y = ny;
            }
        }
        return position;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.RIGHT) {
            isDragging = false;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isDragging) {
            Vector2 newTouch = new Vector2(screenX, screenY);
            // delta will now hold the difference between the last and the current touch positions
            // delta.x > 0 means the touch moved to the right, delta.x < 0 means a move to the left
            delta = newTouch.cpy().sub(lastTouch);

            lastTouch = newTouch;
        }
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
