package com.shoggoth.ld45.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class CellComponent implements Component {

    public static final ComponentMapper<CellComponent> mapper = ComponentMapper.getFor(CellComponent.class);

    private int x;
    private int y;

    public CellComponent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
