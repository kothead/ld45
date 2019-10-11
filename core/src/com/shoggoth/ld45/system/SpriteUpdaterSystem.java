package com.shoggoth.ld45.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kothead.gdxjam.base.GdxJam;
import com.kothead.gdxjam.base.component.SpriteComponent;
import com.shoggoth.ld45.Assets;
import com.shoggoth.ld45.component.*;
import com.shoggoth.ld45.util.CardSprite;

import java.util.ArrayList;
import java.util.List;

public class SpriteUpdaterSystem extends IteratingSystem {

    public SpriteUpdaterSystem(int priority) {
        super(Family.all(SpriteComponent.class, CardComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SpriteComponent spriteComponent = SpriteComponent.mapper.get(entity);
        if (spriteComponent.sprite instanceof CardSprite) {
            CardSprite sprite = (CardSprite) spriteComponent.sprite;
            HealthComponent healthComponent = HealthComponent.mapper.get(entity);

            boolean stop = false;
            List<AssetDescriptor<TextureRegion>> backgrounds = new ArrayList<AssetDescriptor<TextureRegion>>();

            if (healthComponent != null && CreatureComponent.mapper.has(entity)) {
                float relation = healthComponent.health / (float) healthComponent.start;
                if (healthComponent.health < 4) {
                    sprite.setDamage(Assets.images.DAMAGED2);
                } else if (relation < 1f) {
                    sprite.setDamage(Assets.images.DAMAGED1);
                } else {
                    sprite.setDamage(null);
                }

                stop = chooseBackground(backgrounds, healthComponent.buff, Assets.images.RIVER3, Assets.images.RIVER2, Assets.images.RIVER);
            }

            DamageComponent damageComponent = DamageComponent.mapper.get(entity);
            if (damageComponent != null && !stop) {
                stop = chooseBackground(backgrounds, damageComponent.buff, Assets.images.PRESENCE3, Assets.images.PRESENCE2, Assets.images.PRESENCE);
            }

            SpawnerComponent spawnerComponent = SpawnerComponent.mapper.get(entity);
            if (spawnerComponent != null && !stop) {
                stop = chooseBackground(backgrounds, spawnerComponent.healthBuff, Assets.images.RIVER3, Assets.images.RIVER2, Assets.images.RIVER);
                if (!stop) stop = chooseBackground(backgrounds, spawnerComponent.damageBuff, Assets.images.PRESENCE3, Assets.images.PRESENCE2, Assets.images.PRESENCE);
                if (!stop) stop = chooseBackground(backgrounds, spawnerComponent.spawnBuff, Assets.images.GROUND3, Assets.images.GROUND2, Assets.images.GROUND);
            }

            sprite.setBackgroundCosmetics(backgrounds);
        }
    }

    private boolean chooseBackground(List<AssetDescriptor<TextureRegion>> backgrounds,
                                  int value,
                                  AssetDescriptor<TextureRegion> max,
                                  AssetDescriptor<TextureRegion> middle,
                                  AssetDescriptor<TextureRegion> low) {
        if (value >= 3) {
            backgrounds.clear();
            backgrounds.add(max);
            return true;
        } else if (value == 2) {
            backgrounds.add(middle);
        } else if (value == 1) {
            backgrounds.add(low);
        }
        return false;
    }
}
