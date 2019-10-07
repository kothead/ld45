package com.shoggoth.ld45.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kothead.gdxjam.base.component.SpriteComponent;
import com.shoggoth.ld45.component.InterpolationScaleComponent;

public class InterpolationScaleSystem extends IteratingSystem {

    public InterpolationScaleSystem(int priority) {
        super(Family.all(
                InterpolationScaleComponent.class,
                SpriteComponent.class
        ).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        InterpolationScaleComponent component = InterpolationScaleComponent.mapper.get(entity);
        component.elapsed += deltaTime;

        Sprite sprite = SpriteComponent.mapper.get(entity).sprite;
        sprite.setScale(component.getInterpolatedValue());

        if (component.elapsed >= component.duration) {
            sprite.setScale(component.to);
            entity.remove(InterpolationScaleComponent.class);
            if (component.next != null) {
                entity.add(component.next);
            }
        }
    }
}
