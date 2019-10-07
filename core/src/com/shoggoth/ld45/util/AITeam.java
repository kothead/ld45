package com.shoggoth.ld45.util;

public class AITeam extends Team {

    private int unitsLimit = 2;

    public AITeam(int id, int steps) {
        super(id, steps, false);
    }

    public int getUnitsLimit() {
        return unitsLimit;
    }
}
