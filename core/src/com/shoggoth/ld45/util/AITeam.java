package com.shoggoth.ld45.util;

import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;
import java.util.List;

public class AITeam extends Team {

    private int unitsLimit = 4;
    private List<Entity> unitQueue = new ArrayList<>();

    private int queueIndex = 0;

    public AITeam(int id, int steps) {
        super(id, steps, false);
    }

    public int getUnitsLimit() {
        return unitsLimit;
    }

    public List<Entity> getUnitQueue() {
        return unitQueue;
    }

    public void activateNext() {
        queueIndex++;
    }

    public Entity getActiveUnit() {
        if (queueIndex >= unitQueue.size()) {
            queueIndex = 0;
        }

        return unitQueue.get(queueIndex);
    }

    public void removeUnit(Entity entity) {
        int index = unitQueue.indexOf(entity);
        if (index < queueIndex) {
            queueIndex--;
        }

    }
}
