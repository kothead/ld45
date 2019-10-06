package com.shoggoth.ld45.util;

public class Team {

    private int id;
    private int steps;
    private boolean isPlayer;

    public Team(int id, int steps, boolean isPlayer) {
        this.id = id;
        this.steps = steps;
        this.isPlayer = isPlayer;
    }

    public int getId() {
        return id;
    }

    public int getSteps() {
        return steps;
    }

    public boolean isPlayer() {
        return isPlayer;
    }
}
