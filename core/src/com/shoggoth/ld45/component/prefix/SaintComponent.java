package com.shoggoth.ld45.component.prefix;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class SaintComponent implements Component {
    public static final ComponentMapper<SaintComponent> mapper = ComponentMapper.getFor(SaintComponent.class);

    public int heal;

    public SaintComponent(int heal) {
        this.heal = heal;
    }
}
