package com.shoggoth.ld45.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;

public class InterpolationTintComponent implements Component{

    public static final ComponentMapper<InterpolationTintComponent> mapper = ComponentMapper.getFor(InterpolationTintComponent.class);

    public Interpolation interpolation;
    public Color color;
    public float from;
    public float to;
    public float elapsed;
    public float delay;
    public float duration;

    public InterpolationTintComponent next;
    public InterpolationCallback callback;

    public InterpolationTintComponent(Interpolation interpolation,
                                      Color color,
                                      float from, float to,
                                      float delay,
                                      float duration) {
        this.interpolation = interpolation;
        this.color = color;
        this.delay = delay;
        this.duration = duration;
        this.from = from;
        this.to = to;
        elapsed = 0.0f;
    }

    public float getInterpolatedValue() {
        return interpolation.apply(from, to, elapsed / duration);
    }

    public interface InterpolationCallback {
        void onInterpolationFinished(Entity entity);
    }
}
