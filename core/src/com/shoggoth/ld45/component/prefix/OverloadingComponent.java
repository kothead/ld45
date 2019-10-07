package com.shoggoth.ld45.component.prefix;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class OverloadingComponent implements Component {
    public static final ComponentMapper<OverloadingComponent> mapper = ComponentMapper.getFor(OverloadingComponent.class);
}
