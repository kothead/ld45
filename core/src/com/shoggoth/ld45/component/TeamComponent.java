package com.shoggoth.ld45.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class TeamComponent implements Component {

    public static final ComponentMapper<TeamComponent> mapper = ComponentMapper.getFor(TeamComponent.class);

    public int id;
    public int componentId;

    public TeamComponent(int id, int componentId) {
        this.id = id;
        this.componentId = componentId;
    }
}
