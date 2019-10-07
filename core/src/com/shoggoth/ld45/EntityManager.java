package com.shoggoth.ld45;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.kothead.gdxjam.base.GdxJam;
import com.kothead.gdxjam.base.component.PositionComponent;
import com.kothead.gdxjam.base.component.SpriteComponent;
import com.kothead.gdxjam.base.system.RenderSystem;
import com.shoggoth.ld45.component.*;
import com.shoggoth.ld45.screen.GameScreen;
import com.shoggoth.ld45.system.*;
import com.shoggoth.ld45.util.CardSprite;
import com.shoggoth.ld45.util.RenderConfig;
import com.shoggoth.ld45.util.Team;

import java.util.ArrayList;

public class EntityManager {

    private Engine engine;
    private GameScreen screen;
    private RenderConfig renderConfig;
    private Team[] teams;

    public EntityManager(Engine engine, GameScreen screen, RenderConfig renderConfig, Team... teams) {
        this.engine = engine;
        this.screen = screen;
        this.renderConfig = renderConfig;
        this.teams = teams;
        registerSystems();
    }

    public void registerSystems() {
        int priority = 0;
        engine.addSystem(new InterpolationPositionSystem(priority++));
        engine.addSystem(new InterpolationTintSystem(priority++));
        engine.addSystem(new InterpolationScaleSystem(priority++));
        engine.addSystem(new AISystem(priority++, screen, renderConfig));
        engine.addSystem(new InputSystem(priority++, screen, renderConfig));

        SelectionInputSystem system = new SelectionInputSystem(priority++, screen, renderConfig);
        system.setProcessing(false);
        engine.addSystem(system);

        engine.addSystem(new BackgroundRenderSystem(priority++, screen, renderConfig));
        engine.addSystem(new RenderSystem(priority++, screen.batch()));
        engine.addSystem(new FieldHighlightRenderSystem(priority++, screen.shapes(), renderConfig));
        engine.addSystem(new GameLogicSystem(priority++, screen, this, renderConfig, teams));
    }

    public <T extends EntitySystem> void pause(Class<T> systemType) {
        engine.getSystem(systemType).setProcessing(false);
    }

    public <T extends EntitySystem> void resume(Class<T> systemType) {
        engine.getSystem(systemType).setProcessing(true);
    }

    public void removeEntity(Entity entity) {
        engine.removeEntity(entity);
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

    private Entity addCard(int x, int y, int teamId) {
        Entity entity = new Entity();
        entity.add(new PositionComponent(
                (renderConfig.getPadding() + renderConfig.getCardWidth()) * x + renderConfig.getMargin(),
                (renderConfig.getPadding() + renderConfig.getCardHeight()) * y + renderConfig.getMargin(),
                0
        ));
        entity.add(new CardComponent());
        entity.add(new TeamComponent(teamId));
        attach(screen.getField()[y][x], entity);
        return entity;
    }

    private Entity addCard(int teamId) {
        Entity entity = new Entity();
        entity.add(new CardComponent());
        entity.add(new TeamComponent(teamId));
        entity.add(new PositionComponent(0, 0, 0));
        return entity;
    }

    public Entity addSkeleton(int x, int y, int teamId) {
        Entity entity = addCard(x, y, teamId);
        entity.add(new CreatureComponent());
        CardSprite sprite = new CardSprite(renderConfig);
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

    public Entity addZombie(int x, int y, int teamId) {
        Entity entity = addCard(x, y, teamId);
        entity.add(new CreatureComponent());
        CardSprite sprite = new CardSprite(renderConfig);
        sprite.setBorder(Assets.images.BORDER);
        sprite.setBackground(Assets.images.BACKGROUND);
        sprite.setBackgroundCosmetics(new ArrayList<AssetDescriptor<TextureRegion>>() {{
            add(Assets.images.RIVER2);
            add(Assets.images.GROUND2);
            add(Assets.images.PRESENCE2);
        }});
        sprite.setBase(Assets.images.ZOMBIE);
//        sprite.setCosmetics(new ArrayList<AssetDescriptor<TextureRegion>>() {{
//            add(Assets.images.COSMETIC);
//        }});
        entity.add(new SpriteComponent(sprite));
        engine.addEntity(entity);
        return entity;
    }

    public Entity addDemon(int x, int y, int teamId) {
        Entity entity = addCard(x, y, teamId);
        entity.add(new CreatureComponent());
        CardSprite sprite = new CardSprite(renderConfig);
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

    public Entity addCrypt(int x, int y, int teamId) {
        Entity entity = addCard(x, y, teamId);
        return addCrypt(entity);
    }

    public Entity addCrypt(int teamId) {
        Entity entity = addCard(teamId);
        return addCrypt(entity);
    }

    private Entity addCrypt(Entity entity) {
        final int teamId = TeamComponent.mapper.get(entity).id;

        entity.add(new HealthComponent(1));
        entity.add(new SpawnerComponent(new SpawnerComponent.Spawner() {
            @Override
            public Entity spawn(int x, int y) {
                Entity entity = addSkeleton(x, y, teamId);
                return entity;
            }
        }, 2, 4));

        CardSprite sprite = new CardSprite(renderConfig);
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

    public Entity addCemetery(int x, int y, int teamId) {
        Entity entity = addCard(x, y, teamId);
        return addCemetery(entity);
    }

    public Entity addCemetery(int teamId) {
        Entity entity = addCard(teamId);
        return addCemetery(entity);
    }

    private Entity addCemetery(Entity entity) {
        final int teamId = TeamComponent.mapper.get(entity).id;

        entity.add(new HealthComponent(1));
        entity.add(new SpawnerComponent(new SpawnerComponent.Spawner() {
            @Override
            public Entity spawn(int x, int y) {
                Entity entity = addZombie(x, y, teamId);
                return entity;
            }
        }, 1, 2));

        CardSprite sprite = new CardSprite(renderConfig);
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

    public Entity addAbyss(int x, int y, int teamId) {
        Entity entity = addCard(x, y, teamId);
        return addAbyss(entity);
    }

    public Entity addAbyss(int teamId) {
        Entity entity = addCard(teamId);
        return addAbyss(entity);
    }

    private Entity addAbyss(Entity entity) {
        final int teamId = TeamComponent.mapper.get(entity).id;

        entity.add(new HealthComponent(1));
        entity.add(new SpawnerComponent(new SpawnerComponent.Spawner() {
            @Override
            public Entity spawn(int x, int y) {
                Entity entity = addDemon(x, y, teamId);
                return entity;
            }
        }, 1, 3));

        CardSprite sprite = new CardSprite(renderConfig);
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

    public Entity addBloodRiver(int x, int y, int teamId) {
        Entity entity = addCard(x, y, teamId);
        return addBloodRiver(entity);
    }

    public Entity addBloodRiver(int teamId) {
        Entity entity = addCard(teamId);
        return addBloodRiver(entity);
    }

    private Entity addBloodRiver(Entity entity) {
        entity.add(new HealthComponent(1));
        CardSprite sprite = new CardSprite(renderConfig);
        sprite.setBorder(Assets.images.BORDER);
        sprite.setBackground(Assets.images.BACKGROUND);
        sprite.setBase(Assets.images.RIVER3);
        entity.add(new SpriteComponent(sprite));
        engine.addEntity(entity);
        return entity;
    }

    public Entity addCursedGround(int x, int y, int teamId) {
        Entity entity = addCard(x, y, teamId);
        return addCursedGround(entity);
    }

    public Entity addCursedGround(int teamId) {
        Entity entity = addCard(teamId);
        return addCursedGround(entity);
    }

    private Entity addCursedGround(Entity entity) {
        entity.add(new HealthComponent(1));
        CardSprite sprite = new CardSprite(renderConfig);
        sprite.setBorder(Assets.images.BORDER);
        sprite.setBackground(Assets.images.BACKGROUND);
        sprite.setBase(Assets.images.GROUND3);
        entity.add(new SpriteComponent(sprite));
        engine.addEntity(entity);
        return entity;
    }

    public Entity addDemonicPresence(int x, int y, int teamId) {
        Entity entity = addCard(x, y, teamId);
        return addDemonicPresence(entity);
    }

    public Entity addDemonicPresence(int teamId) {
        Entity entity = addCard(teamId);
        return addDemonicPresence(entity);
    }

    private Entity addDemonicPresence(Entity entity) {
        entity.add(new HealthComponent(1));
        CardSprite sprite = new CardSprite(renderConfig);
        sprite.setBorder(Assets.images.BORDER);
        sprite.setBackground(Assets.images.BACKGROUND);
        sprite.setBase(Assets.images.PRESENCE3);
        entity.add(new SpriteComponent(sprite));
        engine.addEntity(entity);
        return entity;
    }

    public Entity addNothing(int x, int y, int teamId) {
        Entity entity = addCard(x, y, teamId);
        CardSprite sprite = new CardSprite(renderConfig);
        sprite.setBorder(Assets.images.BORDER);
        sprite.setBackground(Assets.images.BACKGROUND);
        sprite.setBase(Assets.images.NOTHING);
        entity.add(new SpriteComponent(sprite));
        entity.add(new NothingComponent());
        engine.addEntity(entity);
        return entity;
    }

    public Entity addBlackness() {
        Entity entity = new Entity();
        Sprite sprite = new Sprite(GdxJam.assets().get(Assets.images.DESKTILE));
        sprite.setSize(screen.getWorldWidth(), screen.getWorldHeight());
        entity.add(new SpriteComponent(sprite));
        entity.add(new PositionComponent(
                screen.getCamera().position.x - screen.getWorldWidth() / 2.0f,
                screen.getCamera().position.y - screen.getWorldHeight() / 2.0f,
                1.0f
        ));
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

    public Entity detachOldNothing(Entity cell) {
        Entity oldNothing = null;
        if (AttachComponent.mapper.has(cell)) {
            oldNothing = AttachComponent.mapper.get(cell).entity;
            if (NothingComponent.mapper.has(oldNothing)) {
                PositionComponent.mapper.get(oldNothing).position.z = -1.0f;
                oldNothing.remove(AttachComponent.class);
                cell.remove(AttachComponent.class);
            } else {
                oldNothing = null;
            }
        }
        return oldNothing;
    }

    public void dispose() {
        engine.removeAllEntities();
        engine.removeAllSystems();
    }
}
