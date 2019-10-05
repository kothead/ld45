package com.shoggoth.ld45.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector3;
import com.kothead.gdxjam.base.component.PositionComponent;
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
        component.elapsed += deltaTime;

        Vector3 position = PositionComponent.mapper.get(entity).position;
        position.set(
                component.getInterpolatedX(),
                component.getInterpolatedY(),
                component.getInterpolatedZ()
        );

        if (component.elapsed >= component.duration) {
            entity.remove(InterpolationPositionComponent.class);
            if (component.next != null) {
                entity.add(component.next);
            }
        }
    }
}
