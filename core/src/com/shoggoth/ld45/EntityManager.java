package com.shoggoth.ld45;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kothead.gdxjam.base.component.PositionComponent;
import com.kothead.gdxjam.base.component.SpriteComponent;
import com.kothead.gdxjam.base.system.RenderSystem;
import com.shoggoth.ld45.component.*;
import com.shoggoth.ld45.screen.GameScreen;
import com.shoggoth.ld45.system.*;
import com.shoggoth.ld45.util.CardSprite;
import com.shoggoth.ld45.util.RenderConfig;

import java.util.ArrayList;

public class EntityManager {

    private Engine engine;
    private GameScreen screen;
    private RenderConfig renderConfig;

    public EntityManager(Engine engine, GameScreen screen, RenderConfig renderConfig) {
        this.engine = engine;
        this.screen = screen;
        this.renderConfig = renderConfig;
        registerSystems();
    }

    public void registerSystems() {
        int priority = 0;
        engine.addSystem(new InterpolationPositionSystem(priority++));
        engine.addSystem(new InterpolationOpacitySystem(priority++));
        engine.addSystem(new InterpolationScaleSystem(priority++));
        engine.addSystem(new InputSystem(priority++, screen, renderConfig));
        engine.addSystem(new RenderSystem(priority++, screen.batch()));
        engine.addSystem(new FieldHighlightRenderSystem(priority++, screen.shapes(), renderConfig));
        engine.addSystem(new GameLogicSystem(priority++, screen, this, renderConfig));
    }

    public Entity addCell(int x, int y) {
        Entity entity = new Entity();
        entity.add(new CellComponent(x, y));
        entity.add(new PositionComponent(
                x * (renderConfig.getCardWidth() + renderConfig.getPadding()) + renderConfig.getMargin(),
                y * (renderConfig.getCardHeight() + renderConfig.getPadding()) + renderConfig.getMargin(),
                0
        ));

        engine.addEntity(entity);
        return entity;
    }

    private Entity addCard(int x, int y) {
        Entity entity = new Entity();
        entity.add(new PositionComponent(
                (renderConfig.getPadding() + renderConfig.getCardWidth()) * x + renderConfig.getMargin(),
                (renderConfig.getPadding() + renderConfig.getCardHeight()) * y + renderConfig.getMargin(),
                0
        ));
        entity.add(new CardComponent());
        attach(screen.getField()[y][x], entity);
        return entity;
    }

    public Entity addSkeleton(int x, int y) {
        Entity entity = addCard(x, y);
        entity.add(new CreatureComponent());
        CardSprite sprite = new CardSprite();
        sprite.setBorder(Assets.images.BORDER);
        sprite.setBackground(Assets.images.BACKGROUND);
        sprite.setBackgroundCosmetics(new ArrayList<AssetDescriptor<TextureRegion>>() {{
            add(Assets.images.RIVER);
            add(Assets.images.GROUND);
//            add(Assets.images.PRESENCE);
        }});
        sprite.setBase(Assets.images.SKELETON);
        sprite.setCosmetics(new ArrayList<AssetDescriptor<TextureRegion>>() {{
                add(Assets.images.COSMETIC);
            }});
        entity.add(new SpriteComponent(sprite));
        engine.addEntity(entity);
        return entity;
    }

    public Entity addZombie(int x, int y) {
        Entity entity = addCard(x, y);
        entity.add(new CreatureComponent());
        CardSprite sprite = new CardSprite();
        sprite.setBorder(Assets.images.BORDER);
        sprite.setBackground(Assets.images.BACKGROUND);
        sprite.setBackgroundCosmetics(new ArrayList<AssetDescriptor<TextureRegion>>() {{
            add(Assets.images.RIVER2);
            add(Assets.images.GROUND2);
            add(Assets.images.PRESENCE2);
        }});
        sprite.setBase(Assets.images.ZOMBIE);
        sprite.setCosmetics(new ArrayList<AssetDescriptor<TextureRegion>>() {{
            add(Assets.images.COSMETIC);
        }});
        entity.add(new SpriteComponent(sprite));
        engine.addEntity(entity);
        return entity;
    }

    public Entity addDemon(int x, int y) {
        Entity entity = addCard(x, y);
        entity.add(new CreatureComponent());
        CardSprite sprite = new CardSprite();
        sprite.setBorder(Assets.images.BORDER);
        sprite.setBackground(Assets.images.BACKGROUND);
        sprite.setBackgroundCosmetics(new ArrayList<AssetDescriptor<TextureRegion>>() {{
            add(Assets.images.RIVER);
            add(Assets.images.GROUND);
            add(Assets.images.PRESENCE);
        }});
        sprite.setBase(Assets.images.DEMON);
        sprite.setCosmetics(new ArrayList<AssetDescriptor<TextureRegion>>() {{
            add(Assets.images.COSMETIC);
        }});
        entity.add(new SpriteComponent(sprite));
        engine.addEntity(entity);
        return entity;
    }

    public Entity addCrypt(int x, int y) {
        Entity entity = addCard(x, y);
        entity.add(new HealthComponent(1));
        entity.add(new SpawnerComponent(new SpawnerComponent.Spawner() {
            @Override
            public Entity spawn(int x, int y) {
                Entity entity = addSkeleton(x, y);
                entity.add(new AllyComponent());
                return entity;
            }
        }, 2, 4));

        CardSprite sprite = new CardSprite();
        sprite.setBorder(Assets.images.BORDER);
        sprite.setBackground(Assets.images.BACKGROUND);
        sprite.setBackgroundCosmetics(new ArrayList<AssetDescriptor<TextureRegion>>() {{
            add(Assets.images.RIVER);
            add(Assets.images.GROUND);
            add(Assets.images.PRESENCE);
        }});
        sprite.setBase(Assets.images.CRYPT);
        entity.add(new SpriteComponent(sprite));
        engine.addEntity(entity);
        return entity;
    }

    public Entity addCemetery(int x, int y) {
        Entity entity = addCard(x, y);
        entity.add(new HealthComponent(1));
        entity.add(new SpawnerComponent(new SpawnerComponent.Spawner() {
            @Override
            public Entity spawn(int x, int y) {
                Entity entity = addZombie(x, y);
                entity.add(new AllyComponent());
                return entity;
            }
        }, 1, 2));

        CardSprite sprite = new CardSprite();
        sprite.setBorder(Assets.images.BORDER);
        sprite.setBackground(Assets.images.BACKGROUND);
        sprite.setBackgroundCosmetics(new ArrayList<AssetDescriptor<TextureRegion>>() {{
//            add(Assets.images.RIVER);
            add(Assets.images.GROUND3);
//            add(Assets.images.PRESENCE);
        }});
        sprite.setBase(Assets.images.CEMETERY);
        entity.add(new SpriteComponent(sprite));
        engine.addEntity(entity);
        return entity;
    }

    public Entity addAbyss(int x, int y) {
        Entity entity = addCard(x, y);
        entity.add(new HealthComponent(1));
        entity.add(new SpawnerComponent(new SpawnerComponent.Spawner() {
            @Override
            public Entity spawn(int x, int y) {
                Entity entity = addSkeleton(x, y);
                entity.add(new AllyComponent());
                return entity;
            }
        }, 1, 3));

        CardSprite sprite = new CardSprite();
        sprite.setBorder(Assets.images.BORDER);
        sprite.setBackground(Assets.images.BACKGROUND);
        sprite.setBackgroundCosmetics(new ArrayList<AssetDescriptor<TextureRegion>>() {{
            add(Assets.images.RIVER);
            add(Assets.images.GROUND);
            add(Assets.images.PRESENCE);
        }});
        sprite.setBase(Assets.images.ABYSS);
        entity.add(new SpriteComponent(sprite));
        engine.addEntity(entity);
        return entity;
    }

    public Entity addNothing(int x, int y) {
        Entity entity = addCard(x, y);
        CardSprite sprite = new CardSprite();
        sprite.setBorder(Assets.images.BORDER);
        sprite.setBackground(Assets.images.BACKGROUND);
        sprite.setBase(Assets.images.NOTHING);
        entity.add(new SpriteComponent(sprite));
        entity.add(new NothingComponent());
        engine.addEntity(entity);
        return entity;
    }

    public void attach(Entity cell, Entity card) {
        if (!CellComponent.mapper.has(cell)) return;
        if (!CardComponent.mapper.has(card)) return;
        if (AttachComponent.mapper.has(cell)) return;
        if (AttachComponent.mapper.has(card)) {
            AttachComponent.mapper.get(card).entity.remove(AttachComponent.class);
            card.remove(AttachComponent.class);
        }

        cell.add(new AttachComponent(card));
        card.add(new AttachComponent(cell));
    }

    public void dispose() {
        engine.removeAllEntities();
        engine.removeAllSystems();
    }
}
