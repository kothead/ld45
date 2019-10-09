package com.shoggoth.ld45.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kothead.gdxjam.base.GdxJam;
import com.shoggoth.ld45.Assets;
import com.shoggoth.ld45.component.*;
import com.shoggoth.ld45.component.prefix.FuriousComponent;
import com.shoggoth.ld45.screen.GameScreen;
import com.shoggoth.ld45.util.RenderConfig;

public class InputSystem extends EntitySystem implements InputProcessor {

    private static final float CAMERA_SPEED = 400.0f;
    private static final float MOVEMENT_MARGIN = 20;

    private GameScreen screen;

    private RenderConfig renderConfig;
    private Rectangle cameraLimits;

    private Vector2 lastLeftTouch = new Vector2();
    private Vector2 lastRightTouch = new Vector2();
    private Vector2 delta = new Vector2(0, 0);
    private boolean isDragging = false;
    private boolean isClicked = false;

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

        Camera camera = screen.getCamera();
        boolean updated = false;
        float x = camera.position.x;
        float y = camera.position.y;

        if (isDragging) {
            x = camera.position.x - delta.x;
            y = camera.position.y + delta.y;
            updated = true;

            delta.setZero();
        } else {
            int px = Gdx.input.getX();
            int py = Gdx.input.getY();

            if (px <= MOVEMENT_MARGIN
                    || Gdx.input.isKeyPressed(Input.Keys.A)
                    || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                x -= CAMERA_SPEED * deltaTime;
                updated = true;
            } else if (px >= screen.getWorldWidth() - MOVEMENT_MARGIN
                    || Gdx.input.isKeyPressed(Input.Keys.D)
                    || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                x += CAMERA_SPEED * deltaTime;
                updated = true;
            }

            if (py <= MOVEMENT_MARGIN
                    || Gdx.input.isKeyPressed(Input.Keys.W)
                    || Gdx.input.isKeyPressed(Input.Keys.UP)) {
                y += CAMERA_SPEED * deltaTime;
                updated = true;
            } else if (py >= screen.getWorldHeight() - MOVEMENT_MARGIN
                    || Gdx.input.isKeyPressed(Input.Keys.S)
                    || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                y -= CAMERA_SPEED * deltaTime;
                updated = true;
            }
        }

        if (updated) {
            x = Math.max(x, cameraLimits.x + camera.viewportWidth / 2f);
            x = Math.min(x, cameraLimits.x + cameraLimits.width - camera.viewportWidth / 2f);

            y = Math.max(y, cameraLimits.y + camera.viewportHeight / 2f);
            y = Math.min(y, cameraLimits.y + cameraLimits.height - camera.viewportHeight / 2f);

            camera.position.set(x, y, 0);
            camera.update();

            screen.shapes().setProjectionMatrix(screen.getCamera().combined);
            screen.batch().setProjectionMatrix(screen.getCamera().combined);
        }

        if (isClicked) {
            GdxJam.assets().get(Assets.sounds.TAP).play();

            Vector3 position = countPosition(lastLeftTouch.x, lastLeftTouch.y);
            Entity source = getSelectionSource();
            boolean deselectSource = false;

            if (position.x >= 0 && position.y >= 0) {
                Entity entity = screen.getField()[(int) position.y][(int) position.x];
                if (entity != null && SelectableComponent.mapper.has(entity)) {
                    if (SelectionSourceComponent.mapper.has(entity)) {
                        deselectSource = true;
                    } else if (source != null) {
                        entity.add(new SelectionTargetComponent());
                    } else {
                        entity.add(new SelectionSourceComponent());
                    }
                } else {
                    deselectSource = true;
                }
            } else {
                deselectSource = true;
            }

            if (source != null && deselectSource) {
                source.remove(SelectionSourceComponent.class);
                resetCardForCell(source);
            }

            isClicked = false;
        }
    }

    private void resetCardForCell(Entity cell) {
        if (!AttachComponent.mapper.has(cell)) return;

        Entity card = AttachComponent.mapper.get(cell).entity;
        if (SpawnerComponent.mapper.has(card)) {
            SpawnerComponent.mapper.get(card).spawned = 0;
        }
        if (FuriousComponent.mapper.has(card)) {
            FuriousComponent.mapper.get((card)).done = 0;
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
        } else if (button == Input.Buttons.RIGHT) {
            isDragging = true;
            lastRightTouch.set(screenX, screenY);
        }
        return false;
    }

    private Vector3 countPosition(float screenX, float screenY) {
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
        if (!checkProcessing()) return false;

        if (button == Input.Buttons.RIGHT) {
            isDragging = false;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!checkProcessing()) return false;

        if (isDragging) {
            Vector2 newTouch = new Vector2(screenX, screenY);
            // delta will now hold the difference between the last and the current touch positions
            // delta.x > 0 means the touch moved to the right, delta.x < 0 means a move to the left
            delta = newTouch.cpy().sub(lastRightTouch);

            lastRightTouch = newTouch;
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

    public Entity getSelectionSource() {
        for (int i = 0; i < renderConfig.getFieldHeight(); i++) {
            for (int j = 0; j < renderConfig.getFieldWidth(); j++) {
                Entity entity = screen.getField()[i][j];
                if (SelectionSourceComponent.mapper.has(entity)) return entity;
            }
        }
        return null;
    }
}
