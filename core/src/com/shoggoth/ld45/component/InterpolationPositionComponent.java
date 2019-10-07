package com.shoggoth.ld45.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;

public class InterpolationPositionComponent implements Component {

    public static final ComponentMapper<InterpolationPositionComponent> mapper = ComponentMapper.getFor(InterpolationPositionComponent.class);

    public Interpolation interpolation;
    public Vector3 from;
    public Vector3 to;
    public float elapsed;
    public float delay;
    public float duration;

    public InterpolationPositionComponent next;
    public InterpolationCallback callback;

    public InterpolationPositionComponent(Interpolation interpolation,
                                          Vector3 from, Vector3 to,
                                          float delay,
                                          float duration) {
        this.interpolation = interpolation;
        this.from = new Vector3(from);
        this.to = new Vector3(to);
        this.delay = delay;
        this.duration = duration;
        elapsed = 0.0f;
    }

    public float getInterpolatedX() {
        return interpolation.apply(from.x, to.x, elapsed / duration);
    }

    public float getInterpolatedY() {
        return interpolation.apply(from.y, to.y, elapsed / duration);
    }

    public float getInterpolatedZ() {
        return interpolation.apply(from.z, to.z, elapsed / duration);
    }

    public interface InterpolationCallback {
        void onInterpolationFinished(Entity entity);
    }
}
