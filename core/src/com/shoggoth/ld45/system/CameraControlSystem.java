package com.shoggoth.ld45.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kothead.gdxjam.base.component.FollowCameraComponent;
import com.kothead.gdxjam.base.component.PositionComponent;
import com.kothead.gdxjam.base.component.SpriteComponent;
import com.kothead.gdxjam.base.screen.BaseScreen;
import com.shoggoth.ld45.EntityManager;

public class CameraControlSystem extends EntitySystem {

    private final static float RETURN_INPUT_THRESHOLD = 1.0f;

    private BaseScreen screen;
    private EntityManager manager;
    private Camera camera;
    private ImmutableArray<Entity> entities;
    private Rectangle cameraLimits;
    private Float maxCameraSpeed;
    private Vector3 origin;

    public CameraControlSystem(int priority, BaseScreen screen, EntityManager manager) {
        super(priority);
        this.screen = screen;
        this.manager = manager;
        this.camera = screen.getCamera();
    }

    @Override
    public void setProcessing(boolean processing) {
        super.setProcessing(processing);
        if (processing) {
            origin = new Vector3(camera.position);
        }
    }

    public void setCameraLimits(Rectangle cameraLimits) {
        this.cameraLimits = cameraLimits;
    }

    public void setMaxCameraSpeed(float speed) {
        this.maxCameraSpeed = speed;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(Family.one(
                PositionComponent.class,
                FollowCameraComponent.class)
                .get());
    }

    @Override
    public void update(float deltaTime) {
        float x = origin.x;
        float y = origin.y;

        if (entities.size() > 0) {
            Entity entity = entities.first();

            Vector3 position = PositionComponent.mapper.get(entity).position;
            Vector2 size = new Vector2();
            if (SpriteComponent.mapper.has(entity)) {
                Sprite sprite = SpriteComponent.mapper.get(entity).sprite;
                size.x = sprite.getX();
                size.y = sprite.getY();
            }

            x = position.x + size.x / 2;
            y = position.y + size.y / 2;
        }

        x = Math.max(x, cameraLimits.x + camera.viewportWidth / 2f);
        x = Math.min(x, cameraLimits.x + cameraLimits.width - camera.viewportWidth / 2f);

        y = Math.max(y, cameraLimits.y + camera.viewportHeight / 2f);
        y = Math.min(y, cameraLimits.y + cameraLimits.height - camera.viewportHeight / 2f);

        if (maxCameraSpeed != null) {
            float dx = camera.position.x - x;
            float dy = camera.position.y - y;

            float speed = maxCameraSpeed * deltaTime;

            if (Math.abs(dx) > speed) {
                x = camera.position.x - Math.signum(dx) * speed;
            }
            if (Math.abs(dy) > speed) {
                y = camera.position.y - Math.signum(dy) * speed;
            }
        }

        camera.position.set(x, y, 0);
        camera.update();

        screen.shapes().setProjectionMatrix(screen.getCamera().combined);
        screen.batch().setProjectionMatrix(screen.getCamera().combined);

        if (Math.abs(x - origin.x) < RETURN_INPUT_THRESHOLD
                && Math.abs(y - origin.y) < RETURN_INPUT_THRESHOLD) {
            manager.pause(CameraControlSystem.class);
            manager.resume(InputSystem.class);
        }
    }
}