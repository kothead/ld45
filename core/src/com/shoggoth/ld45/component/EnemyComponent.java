package com.shoggoth.ld45.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class EnemyComponent implements Component {
    public static final ComponentMapper<EnemyComponent> mapper = ComponentMapper.getFor(EnemyComponent.class);
}
