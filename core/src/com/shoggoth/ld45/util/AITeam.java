package com.shoggoth.ld45.util;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.shoggoth.ld45.component.TeamComponent;

public class AITeam extends Team {

    private ImmutableArray<Entity> units;
    private int unitsLimit = 2;

    public AITeam(int id, int steps, ImmutableArray<Entity> units) {
        super(id, steps, false);
        this.units = units;
    }

    public int getUnitsLimit() {
        return unitsLimit;
    }

    @Override
    public int getSteps() {
        int allies = 0;
        for (Entity entity: units) {
            if (TeamComponent.mapper.has(entity) && TeamComponent.mapper.get(entity).id == getId()) {
                allies++;
            }
        }
        return Math.max(super.getSteps(), allies);
    }
}
