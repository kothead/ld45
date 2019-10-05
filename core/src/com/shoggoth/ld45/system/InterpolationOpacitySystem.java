package com.shoggoth.ld45.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kothead.gdxjam.base.component.SpriteComponent;
import com.shoggoth.ld45.component.InterpolationOpacityComponent;

public class InterpolationOpacitySystem extends IteratingSystem {

    public InterpolationOpacitySystem(int priority) {
        super(Family.all(
                InterpolationOpacityComponent.class,
                SpriteComponent.class
        ).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        InterpolationOpacityComponent component = InterpolationOpacityComponent.mapper.get(entity);
        component.elapsed += deltaTime;

        Sprite sprite = SpriteComponent.mapper.get(entity).sprite;
        sprite.setAlpha(component.getInterpolatedValue());

        if (component.elapsed >= component.duration) {
            entity.remove(InterpolationOpacityComponent.class);
            if (component.next != null) {
                entity.add(component.next);
            }
        }
    }
}
