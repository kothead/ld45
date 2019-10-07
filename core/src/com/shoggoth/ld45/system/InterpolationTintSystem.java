package com.shoggoth.ld45.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kothead.gdxjam.base.component.SpriteComponent;
import com.shoggoth.ld45.component.InterpolationTintComponent;

public class InterpolationTintSystem extends IteratingSystem {

    public InterpolationTintSystem(int priority) {
        super(Family.all(
                InterpolationTintComponent.class,
                SpriteComponent.class
        ).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        InterpolationTintComponent component = InterpolationTintComponent.mapper.get(entity);
        if (component.delay > 0) {
            component.delay -= deltaTime;
            if (component.delay < 0) {
                component.elapsed -= component.delay;
            }
        } else {
            component.elapsed += deltaTime;
        }
        component.color.a = component.getInterpolatedValue();

        Sprite sprite = SpriteComponent.mapper.get(entity).sprite;
        sprite.setColor(component.color);

        if (component.elapsed >= component.duration) {
            component.color.a = component.to;
            sprite.setColor(component.color);
            entity.remove(InterpolationTintComponent.class);

            if (component.callback != null) {
                component.callback.onInterpolationFinished(entity);
            }
            if (component.next != null) {
                entity.add(component.next);
            }
        }
    }
}
