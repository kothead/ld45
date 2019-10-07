package com.shoggoth.ld45.component.prefix;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class VampiricComponent implements Component {
    public static final ComponentMapper<VampiricComponent> mapper = ComponentMapper.getFor(VampiricComponent.class);

    public int heal;

    public VampiricComponent(int heal) {
        this.heal = heal;
    }
}
