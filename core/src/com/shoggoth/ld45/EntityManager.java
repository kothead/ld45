package com.shoggoth.ld45;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.kothead.gdxjam.base.GdxJam;
import com.kothead.gdxjam.base.component.PositionComponent;
import com.kothead.gdxjam.base.component.SpriteComponent;
import com.kothead.gdxjam.base.system.RenderSystem;
import com.kothead.gdxjam.base.util.Direction;
import com.shoggoth.ld45.component.*;
import com.shoggoth.ld45.screen.GameScreen;
import com.shoggoth.ld45.system.*;
import com.shoggoth.ld45.util.CardSprite;
import com.shoggoth.ld45.util.RenderConfig;
import com.shoggoth.ld45.util.Team;

import java.util.ArrayList;

public class EntityManager {

    public static Direction[] NOTHING_SPAWN_DIRECTIONS = {
            Direction.DOWN,
            Direction.UP,
            Direction.LEFT,
            Direction.RIGHT
    };

    public static Direction[] BUFF_DIRECTIONS = Direction.getDirections();

    private Engine engine;
    private GameScreen screen;
    private RenderConfig renderConfig;
    private Team[] teams;

    private int cardsCounter = 0;

    public EntityManager(Engine engine, GameScreen screen, RenderConfig renderConfig, Team... teams) {
        this.engine = engine;
        this.screen = screen;
        this.renderConfig = renderConfig;
        this.teams = teams;
        registerSystems();
    }

    public void registerSystems() {
        int priority = 0;
        engine.addSystem(new InterpolationPositionSystem(priority++, this));
        engine.addSystem(new InterpolationTintSystem(priority++));
        engine.addSystem(new InterpolationScaleSystem(priority++));
        engine.addSystem(new AISystem(priority++, screen, renderConfig));
        engine.addSystem(new InputSystem(priority++, screen, renderConfig));
        CameraControlSystem cameraControlSystem = new CameraControlSystem(priority++, screen, this);
        cameraControlSystem.setCameraLimits(new Rectangle(0, 0, renderConfig.getMaxWidth(), renderConfig.getMaxHeight()));
        cameraControlSystem.setMaxCameraSpeed(2000.0f);
        cameraControlSystem.setProcessing(false);
        engine.addSystem(cameraControlSystem);

        SelectionInputSystem selectionInputSystem = new SelectionInputSystem(priority++, screen, renderConfig);
        selectionInputSystem.setProcessing(false);
        engine.addSystem(selectionInputSystem);

        engine.addSystem(new BackgroundRenderSystem(priority++, screen, renderConfig));
        engine.addSystem(new RenderSystem(priority++, screen.batch()));
        engine.addSystem(new FieldHighlightRenderSystem(priority++, screen, renderConfig));
        engine.addSystem(new GameLogicSystem(priority++, screen, this, renderConfig, teams));
    }

    public <T extends EntitySystem> void pause(Class<T> systemType) {
        if (systemType == FieldHighlightRenderSystem.class) {
            engine.getSystem(FieldHighlightRenderSystem.class).stopSlowly();
        } else {
            engine.getSystem(systemType).setProcessing(false);
        }
    }

    public <T extends EntitySystem> void resume(Class<T> systemType) {
        engine.getSystem(systemType).setProcessing(true);
    }

    public void removeEntity(Entity entity) {
        detach(entity);
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
        entity.add(new TeamComponent(teamId, cardsCounter++));

        CardSprite sprite = new CardSprite(renderConfig);
        if (teamId == 1) {
            sprite.setBorder(Assets.images.BORDER);
        } else if (teamId == 2) {
            sprite.setBorder(Assets.images.BORDERENEMY);
        }
        sprite.setBackground(Assets.images.BACKGROUND);
        entity.add(new SpriteComponent(sprite));

        attach(screen.getField()[y][x], entity);
        return entity;
    }

    private Entity addCard(int teamId) {
        Entity entity = new Entity();
        entity.add(new CardComponent());
        entity.add(new TeamComponent(teamId, cardsCounter++));
        entity.add(new PositionComponent(0, 0, 0));

        CardSprite sprite = new CardSprite(renderConfig);
        if (teamId == 1) {
            sprite.setBorder(Assets.images.BORDER);
        } else if (teamId == 2) {
            sprite.setBorder(Assets.images.BORDERENEMY);
        }
        sprite.setBackground(Assets.images.BACKGROUND);
        entity.add(new SpriteComponent(sprite));

        return entity;
    }

    public Entity addSkeleton(int x, int y, int teamId) {
        Entity entity = addCard(x, y, teamId);
        entity.add(new CreatureComponent(CreatureComponent.CreatureType.SKELETON));
        entity.add(new HealthComponent(5));
        entity.add(new DamageComponent(4));
        CardSprite sprite = (CardSprite) SpriteComponent.mapper.get(entity).sprite;
        sprite.setBackgroundCosmetics(new ArrayList<AssetDescriptor<TextureRegion>>() {{
            add(Assets.images.RIVER);
            add(Assets.images.GROUND);
//            add(Assets.images.PRESENCE);
        }});
        sprite.setBase(Assets.images.SKELETON);
        sprite.setCosmetics(new ArrayList<AssetDescriptor<TextureRegion>>() {{
                add(Assets.images.COSMETIC);
            }});
        engine.addEntity(entity);
        return entity;
    }

    public Entity addZombie(int x, int y, int teamId) {
        Entity entity = addCard(x, y, teamId);
        entity.add(new CreatureComponent(CreatureComponent.CreatureType.ZOMBIE));
        entity.add(new HealthComponent(10));
        entity.add(new DamageComponent(5));
        CardSprite sprite = (CardSprite) SpriteComponent.mapper.get(entity).sprite;
        sprite.setBackgroundCosmetics(new ArrayList<AssetDescriptor<TextureRegion>>() {{
            add(Assets.images.RIVER2);
            add(Assets.images.GROUND2);
            add(Assets.images.PRESENCE2);
        }});
        sprite.setBase(Assets.images.ZOMBIE);
//        sprite.setCosmetics(new ArrayList<AssetDescriptor<TextureRegion>>() {{
//            add(Assets.images.COSMETIC);
//        }});
        engine.addEntity(entity);
        return entity;
    }

    public Entity addDemon(int x, int y, int teamId) {
        Entity entity = addCard(x, y, teamId);
        entity.add(new CreatureComponent(CreatureComponent.CreatureType.DEMON));
        entity.add(new HealthComponent(6));
        entity.add(new DamageComponent(10));
        CardSprite sprite = (CardSprite) SpriteComponent.mapper.get(entity).sprite;
        sprite.setBackgroundCosmetics(new ArrayList<AssetDescriptor<TextureRegion>>() {{
            add(Assets.images.RIVER);
            add(Assets.images.GROUND);
            add(Assets.images.PRESENCE);
        }});
        sprite.setBase(Assets.images.DEMON);
        sprite.setCosmetics(new ArrayList<AssetDescriptor<TextureRegion>>() {{
            add(Assets.images.COSMETIC);
        }});
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

        CardSprite sprite = (CardSprite) SpriteComponent.mapper.get(entity).sprite;
        sprite.setBackgroundCosmetics(new ArrayList<AssetDescriptor<TextureRegion>>() {{
            add(Assets.images.RIVER);
            add(Assets.images.GROUND);
            add(Assets.images.PRESENCE);
        }});
        sprite.setBase(Assets.images.CRYPT);
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

        CardSprite sprite = (CardSprite) SpriteComponent.mapper.get(entity).sprite;
        sprite.setBackgroundCosmetics(new ArrayList<AssetDescriptor<TextureRegion>>() {{
//            add(Assets.images.RIVER);
            add(Assets.images.GROUND3);
//            add(Assets.images.PRESENCE);
        }});
        sprite.setBase(Assets.images.CEMETERY);
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

        CardSprite sprite = (CardSprite) SpriteComponent.mapper.get(entity).sprite;
        sprite.setBackgroundCosmetics(new ArrayList<AssetDescriptor<TextureRegion>>() {{
            add(Assets.images.RIVER);
            add(Assets.images.GROUND);
            add(Assets.images.PRESENCE);
        }});
        sprite.setBase(Assets.images.ABYSS);
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
        entity.add(new ResourceComponent(ResourceComponent.ResourceType.RIVER));
        CardSprite sprite = (CardSprite) SpriteComponent.mapper.get(entity).sprite;
        sprite.setBase(Assets.images.BLOODRIVER);
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
        entity.add(new ResourceComponent(ResourceComponent.ResourceType.GROUND));
        CardSprite sprite = (CardSprite) SpriteComponent.mapper.get(entity).sprite;
        sprite.setBase(Assets.images.CURSEDGROUND);
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
        entity.add(new ResourceComponent(ResourceComponent.ResourceType.PRESENCE));
        CardSprite sprite = (CardSprite) SpriteComponent.mapper.get(entity).sprite;
        sprite.setBase(Assets.images.DEMONIC);
        engine.addEntity(entity);
        return entity;
    }

    public Entity addNothing(int x, int y, int teamId) {
        Entity entity = addCard(x, y, teamId);
        CardSprite sprite = (CardSprite) SpriteComponent.mapper.get(entity).sprite;
        sprite.setBase(Assets.images.NOTHING);
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

        buffAround(cell, card, 1);
    }

    public void detach(Entity card) {
        if (AttachComponent.mapper.has(card)) {
            Entity cell = AttachComponent.mapper.get(card).entity;
            buffAround(cell, card, -1);

            cell.remove(AttachComponent.class);
            card.remove(AttachComponent.class);
        }
    }

    public void buffAround(Entity cell, Entity card, int delta) {
        if (ResourceComponent.mapper.has(card)) {
            Entity[][] cells = screen.getField();
            for (Direction direction: BUFF_DIRECTIONS) {
                int x = CellComponent.mapper.get(cell).getX() + direction.getDx();
                int y = CellComponent.mapper.get(cell).getY() + direction.getDy();

                if (x >= 0 && x < renderConfig.getFieldWidth()
                        && y >= 0 && y < renderConfig.getFieldHeight()
                        && AttachComponent.mapper.has(cells[y][x])) {
                    Entity adjacentCard = AttachComponent.mapper.get(cells[y][x]).entity;
                    SpawnerComponent spawner = SpawnerComponent.mapper.get(adjacentCard);
                    if (spawner == null) continue;

                    switch (ResourceComponent.mapper.get(card).resourceType) {
                        case RIVER:
                            spawner.healthBuff += delta;
                            break;

                        case GROUND:
                            spawner.spawnCount += delta;
                            break;

                        case PRESENCE:
                            spawner.damageBuff += delta;
                            break;
                    }
                }
            }
        }
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

    public void checkForNothing() {
        Entity[][] entities = screen.getField();
        for (int i = 0; i < renderConfig.getFieldHeight(); i++) {
            for (int j = 0; j < renderConfig.getFieldWidth(); j++) {
                if (AttachComponent.mapper.has(entities[i][j])) continue;

                for (Direction direction: NOTHING_SPAWN_DIRECTIONS) {
                    int x = j + direction.getDx();
                    int y = i + direction.getDy();

                    if (x >=0 && x < renderConfig.getFieldWidth()
                            && y >= 0 && y < renderConfig.getFieldHeight()
                            && AttachComponent.mapper.has(entities[y][x])) {
                        Entity card = AttachComponent.mapper.get(entities[y][x]).entity;
                        if (ResourceComponent.mapper.has(card) || SpawnerComponent.mapper.has(card)) {
                            addNothing(j, i, TeamComponent.mapper.get(card).id);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void dispose() {
        engine.removeAllEntities();
        engine.removeAllSystems();
    }
}
