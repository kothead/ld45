package com.shoggoth.ld45.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.Gdx;

public class SelectionTargetComponent implements Component {

    public static final ComponentMapper<SelectionTargetComponent> mapper = ComponentMapper.getFor(SelectionTargetComponent.class);

    public SelectionTargetComponent() {
        super();
        Gdx.app.log(SelectionTargetComponent.class.getSimpleName(), "Selection target set");
    }
}