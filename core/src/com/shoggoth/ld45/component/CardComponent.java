package com.shoggoth.ld45.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class CardComponent implements Component {
    public static final ComponentMapper<CardComponent> mapper = ComponentMapper.getFor(CardComponent.class);
}
