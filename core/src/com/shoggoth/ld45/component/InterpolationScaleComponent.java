package com.shoggoth.ld45.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Interpolation;

public class InterpolationScaleComponent implements Component {

    public static final ComponentMapper<InterpolationScaleComponent> mapper = ComponentMapper.getFor(InterpolationScaleComponent.class);

    public Interpolation interpolation;
    public float from;
    public float to;
    public float duration;
    public float elapsed;

    public InterpolationScaleComponent next;

    public InterpolationScaleComponent(Interpolation interpolation, float from, float to, float duration) {
        this.interpolation = interpolation;
        this.from = from;
        this.to = to;
        this.duration = duration;
        elapsed = 0.0f;
    }

    public float getInterpolatedValue() {
        return interpolation.apply(from, to, elapsed / duration);
    }
}
