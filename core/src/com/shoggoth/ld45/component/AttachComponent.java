package com.shoggoth.ld45.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

public class AttachComponent implements Component {
    public static final ComponentMapper<AttachComponent> mapper = ComponentMapper.getFor(AttachComponent.class);

    public Entity entity;

    public AttachComponent(Entity entity) {
        this.entity = entity;
    }
}
