package com.shoggoth.ld45.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class DamageComponent implements Component {
    public static final ComponentMapper<DamageComponent> mapper = ComponentMapper.getFor(DamageComponent.class);

    public int buff;
    public int damage;

    public DamageComponent(int damage) {
        this.damage = damage;
    }
}
