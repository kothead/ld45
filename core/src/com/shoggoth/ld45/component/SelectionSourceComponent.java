package com.shoggoth.ld45.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.Gdx;

public class SelectionSourceComponent implements Component {

    public static final ComponentMapper<SelectionSourceComponent> mapper = ComponentMapper.getFor(SelectionSourceComponent.class);

    public SelectionSourceComponent() {
        super();
        Gdx.app.log(SelectionSourceComponent.class.getSimpleName(), "Selection source set");
    }
}
