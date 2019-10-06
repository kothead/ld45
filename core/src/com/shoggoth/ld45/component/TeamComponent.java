package com.shoggoth.ld45.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class TeamComponent implements Component {

    public static final ComponentMapper<TeamComponent> mapper = ComponentMapper.getFor(TeamComponent.class);

    public int id;

    public TeamComponent(int id) {
        this.id = id;
    }
}
