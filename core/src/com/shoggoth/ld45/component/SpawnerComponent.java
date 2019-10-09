package com.shoggoth.ld45.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

public class SpawnerComponent implements Component {
    public static final ComponentMapper<SpawnerComponent> mapper = ComponentMapper.getFor(SpawnerComponent.class);

    public int spawnCount;
    public int limit;
    public int healthBuff = 0;
    public int damageBuff = 0;
    public int spawnBuff = 0;
    public int spawned = 0;

    private Spawner spawner;

    public SpawnerComponent(Spawner spawner, int spawnCount, int limit) {
        this.spawner = spawner;
        this.spawnCount = spawnCount;
        this.limit = limit;
    }

    public Entity spawn(int x, int y) {
        Entity entity = spawner.spawn(x, y);
        if (HealthComponent.mapper.has(entity)) {
            HealthComponent.mapper.get(entity).health += healthBuff;
            HealthComponent.mapper.get(entity).buff = healthBuff;
        }
        if (DamageComponent.mapper.has(entity)) {
            DamageComponent.mapper.get(entity).damage += damageBuff;
            DamageComponent.mapper.get(entity).buff = damageBuff;
        }
        return entity;
    }

    public static abstract class Spawner {
        public abstract Entity spawn(int x, int y);
    }
}
