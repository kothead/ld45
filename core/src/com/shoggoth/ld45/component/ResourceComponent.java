package com.shoggoth.ld45.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class ResourceComponent implements Component {
    public static final ComponentMapper<ResourceComponent> mapper = ComponentMapper.getFor(ResourceComponent.class);
}
