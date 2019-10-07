package com.shoggoth.ld45.util;

import com.badlogic.gdx.Gdx;

public class ActionQueue {

    private Team[] teams;
    private int currentTeamIndex = 0;
    private int currentTeamActions = 0;

    public ActionQueue(Team... teams) {
        this.teams = teams;
    }

    public Team getCurrentTeam() {
        return teams[currentTeamIndex];
    }

    public int getCurrentTeamId() {
        return teams[currentTeamIndex].getId();
    }

    public void nextAction() {
        Gdx.app.log("Test", "Action " + currentTeamActions + " of team " + currentTeamIndex + " is completed");
        currentTeamActions++;
        if (currentTeamActions >= getCurrentTeam().getSteps()) {
            nextTeam();
        }
    }

    private void nextTeam() {
        currentTeamIndex++;
        if (currentTeamIndex >= teams.length) {
            currentTeamIndex = 0;
        }
        currentTeamActions = 0;
    }
}
