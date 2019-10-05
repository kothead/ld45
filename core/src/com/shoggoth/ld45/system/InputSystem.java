package com.shoggoth.ld45.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kothead.gdxjam.base.screen.BaseScreen;
import com.shoggoth.ld45.util.RenderConfig;

public class InputSystem extends EntitySystem implements InputProcessor {

    private BaseScreen screen;

    private Rectangle cameraLimits;

    private Vector2 lastTouch = new Vector2();
    private Vector2 delta = new Vector2(0, 0);
    private boolean isDragging = false;

    public InputSystem(int priority, BaseScreen screen, RenderConfig renderConfig) {
        super(priority);
        this.screen = screen;
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

            float x = delta.x / 3 * -1;
            float y = delta.y / 3;

            x = screen.getCamera().position.x + x;
            y = screen.getCamera().position.y + y;

            x = Math.max(x, cameraLimits.x + camera.viewportWidth / 2f);
            x = Math.min(x, cameraLimits.x + cameraLimits.width - camera.viewportWidth / 2f);

            y = Math.max(y, cameraLimits.y + camera.viewportHeight / 2f);
            y = Math.min(y, cameraLimits.y + cameraLimits.height - camera.viewportHeight / 2f);

            screen.getCamera().position.set(x, y, 0);
            screen.getCamera().update();
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
        if (button == Input.Buttons.RIGHT) {
            isDragging = true;
            lastTouch.set(screenX, screenY);
        }
        return false;
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
