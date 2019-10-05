package com.shoggoth.ld45.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Interpolation;

public class InterpolationOpacityComponent implements Component{

    public static final ComponentMapper<InterpolationOpacityComponent> mapper = ComponentMapper.getFor(InterpolationOpacityComponent.class);

    public Interpolation interpolation;
    public float from;
    public float to;
    public float elapsed;
    public float duration;

    public InterpolationOpacityComponent next;

    public InterpolationOpacityComponent(Interpolation interpolation,
                                         float from, float to,
                                         float duration) {
        this.interpolation = interpolation;
        this.duration = duration;
        this.from = from;
        this.to = to;
        elapsed = 0.0f;
    }
}
