package com.shoggoth.ld45.util;

import com.shoggoth.ld45.LD45;
import com.shoggoth.ld45.component.SpawnerComponent;

public class Wave {

    private int limit;
    private SpawnerComponent.Spawner[] spawners;

    private int counter = 0;

    public Wave(int limit, SpawnerComponent.Spawner... spawners) {
        this.limit = limit;
        this.spawners = spawners;
    }

    public int getLimit() {
        return limit;
    }

    public boolean isAllSpawned() {
        return limit == counter;
    }

    public void spawn(int x, int y, int teamId) {
        int index = LD45.random.nextInt(spawners.length);
        spawners[index].spawn(x, y, teamId);
        counter++;
    }
}
