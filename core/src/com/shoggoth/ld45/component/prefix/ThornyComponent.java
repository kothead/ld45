package com.shoggoth.ld45.component.prefix;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class ThornyComponent implements Component {
    public static final ComponentMapper<ThornyComponent> mapper = ComponentMapper.getFor(ThornyComponent.class);

    public float damage;

    public ThornyComponent(float damage) {
        this.damage = damage;
    }
}
