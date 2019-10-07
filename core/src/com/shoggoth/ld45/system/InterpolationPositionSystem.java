package com.shoggoth.ld45.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector3;
import com.kothead.gdxjam.base.component.FollowCameraComponent;
import com.kothead.gdxjam.base.component.PositionComponent;
import com.kothead.gdxjam.base.system.RenderSystem;
import com.shoggoth.ld45.EntityManager;
import com.shoggoth.ld45.component.InterpolationPositionComponent;
import com.shoggoth.ld45.component.TeamComponent;

public class InterpolationPositionSystem extends IteratingSystem {

    private EntityManager manager;

    public InterpolationPositionSystem(int priority, EntityManager manager) {
        super(Family.all(
                InterpolationPositionComponent.class,
                PositionComponent.class
        ).get(), priority);
        this.manager = manager;
    }

    @Override
    public void update(float deltaTime) {
        if (getEntities().size() > 0) {
            Entity entity = getEntities().first();
            if (TeamComponent.mapper.get(entity).id != 1
                    && !FollowCameraComponent.mapper.has(entity)) {
                manager.pause(InputSystem.class);
                manager.resume(CameraControlSystem.class);
                entity.add(new FollowCameraComponent());
            }
        }

        super.update(deltaTime);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        InterpolationPositionComponent component = InterpolationPositionComponent.mapper.get(entity);
        if (component.delay > 0) {
            component.delay -= deltaTime;
            if (component.delay < 0) {
                component.elapsed -= component.delay;
            }
        } else {
            component.elapsed += deltaTime;
        }

        Vector3 position = PositionComponent.mapper.get(entity).position;
        float oldZ = position.z;
        position.set(
                component.getInterpolatedX(),
                component.getInterpolatedY(),
                component.getInterpolatedZ()
        );

        if (component.elapsed == 0.0f
                || Math.signum(oldZ) != Math.signum(position.z)
                || component.elapsed >= component.duration) {
            forceSortZ();
        }

        if (component.elapsed >= component.duration) {
            position.set(component.to);
            entity.remove(InterpolationPositionComponent.class);
            forceSortZ();

            if (component.callback != null) {
                component.callback.onInterpolationFinished(entity);
            }

            if (component.next != null) {
                entity.add(component.next);
            } else {
                entity.remove(FollowCameraComponent.class);
            }
        }
    }

    private void forceSortZ() {
        getEngine().getSystem(RenderSystem.class).forceSort();
    }
}
