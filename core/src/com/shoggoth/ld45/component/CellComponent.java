package com.shoggoth.ld45.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class CellComponent implements Component {
    public static final ComponentMapper<CellComponent> mapper = ComponentMapper.getFor(CellComponent.class);
}
