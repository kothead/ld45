package com.shoggoth.ld45.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class CreatureComponent implements Component {
    public static final ComponentMapper<CreatureComponent> mapper = ComponentMapper.getFor(CreatureComponent.class);
}
