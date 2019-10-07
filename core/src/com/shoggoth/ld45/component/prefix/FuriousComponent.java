package com.shoggoth.ld45.component.prefix;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class FuriousComponent implements Component {
    public static final ComponentMapper<FuriousComponent> mapper = ComponentMapper.getFor(FuriousComponent.class);

    public int count;
    public int done;

    public FuriousComponent(int count) {
        this.count = count;
        done = 0;
    }
}
