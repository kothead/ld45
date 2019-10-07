package com.shoggoth.ld45.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class HealthComponent implements Component {
    public static final ComponentMapper<HealthComponent> mapper = ComponentMapper.getFor(HealthComponent.class);

    public int start;
    public int health;
    public int buff;

    public HealthComponent(int health) {
        this.start = health;
        this.health = health;
    }
}
