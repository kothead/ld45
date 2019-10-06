package com.shoggoth.ld45.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class SelectedComponent implements Component {

    public static final ComponentMapper<SelectedComponent> mapper = ComponentMapper.getFor(SelectedComponent.class);

    public SelectionType type;

    public SelectedComponent(SelectionType type) {
        this.type = type;
    }

    public enum SelectionType {
        SOURCE, TARGET
    }
}
