package com.shoggoth.ld45.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.kothead.gdxjam.base.component.PositionComponent;
import com.kothead.gdxjam.base.system.RenderSystem;
import com.shoggoth.ld45.EntityManager;
import com.shoggoth.ld45.component.InterpolationPositionComponent;

public class InterpolationPositionSystem extends IteratingSystem {

    public InterpolationPositionSystem(int priority) {
        super(Family.all(
                InterpolationPositionComponent.class,
                PositionComponent.class
        ).get(), priority);
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
            }
        }
    }

    private void forceSortZ() {
        getEngine().getSystem(RenderSystem.class).forceSort();
    }
}
