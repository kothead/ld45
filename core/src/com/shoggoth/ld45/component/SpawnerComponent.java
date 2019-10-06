package com.shoggoth.ld45.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

public class SpawnerComponent implements Component {
    public static final ComponentMapper<SpawnerComponent> mapper = ComponentMapper.getFor(SpawnerComponent.class);

    public int spawnCount;
    public int healthBuff = 0;
    public int damageBuff = 0;

    private Spawner spawner;

    public SpawnerComponent(Spawner spawner, int spawnCount) {
        this.spawner = spawner;
        this.spawnCount = spawnCount;
    }

    public Entity spawn(int x, int y) {
        Entity entity = spawner.spawn(x, y);
        if (HealthComponent.mapper.has(entity)) {
            HealthComponent.mapper.get(entity).health += healthBuff;
        }
        if (DamageComponent.mapper.has(entity)) {
            DamageComponent.mapper.get(entity).damage += damageBuff;
        }
        return entity;
    }

    public static abstract class Spawner {
        public abstract Entity spawn(int x, int y);
    }
}
