package com.shoggoth.ld45;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kothead.gdxjam.base.component.PositionComponent;
import com.kothead.gdxjam.base.component.SpriteComponent;
import com.kothead.gdxjam.base.system.RenderSystem;
import com.shoggoth.ld45.component.AttachComponent;
import com.shoggoth.ld45.component.CardComponent;
import com.shoggoth.ld45.component.CellComponent;
import com.shoggoth.ld45.component.SelectableComponent;
import com.shoggoth.ld45.screen.GameScreen;
import com.shoggoth.ld45.system.FieldHighlightRenderSystem;
import com.shoggoth.ld45.system.InputSystem;
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
        engine.addSystem(new InputSystem(priority++, screen, renderConfig));
        engine.addSystem(new RenderSystem(priority++, screen.batch()));
        engine.addSystem(new FieldHighlightRenderSystem(priority++, screen.shapes(), renderConfig));
    }

    public Entity addCell(int x, int y) {
        if (x < 0 || y < 0 || x >= renderConfig.getFieldWidth() || y >= renderConfig.getFieldHeight()) {
            return null;
        }

        Entity entity = new Entity();
        entity.add(new CellComponent());
        entity.add(new SelectableComponent());
        entity.add(new PositionComponent(
                x * (renderConfig.getCardWidth() + renderConfig.getPadding()) + renderConfig.getMargin(),
                y * (renderConfig.getCardHeight() + renderConfig.getPadding()) + renderConfig.getMargin(),
                0
        ));

        engine.addEntity(entity);
        return entity;
    }

    public Entity addCard(int x, int y) {
        if (x < 0 || y < 0 || x >= renderConfig.getFieldWidth() || y >= renderConfig.getFieldHeight()) {
            return null;
        }

        Entity entity = new Entity();
        entity.add(new SelectableComponent());
        entity.add(new PositionComponent(
                (renderConfig.getPadding() + renderConfig.getCardWidth()) * x + renderConfig.getMargin(),
                (renderConfig.getPadding() + renderConfig.getCardHeight()) * y + renderConfig.getMargin(),
                0
        ));
        CardSprite sprite = new CardSprite();
        sprite.setBorder(Assets.images.BORDER);
        sprite.setBackground(Assets.images.BACKGROUND);
        sprite.setBase(Assets.images.SKELETON);
        sprite.setBackgroundCosmetics(new ArrayList<AssetDescriptor<TextureRegion>>() {{
                add(Assets.images.RIVER);
                add(Assets.images.GROUND);
                add(Assets.images.PRESENCE);
            }});
        sprite.setCosmetics(new ArrayList<AssetDescriptor<TextureRegion>>() {{
                add(Assets.images.COSMETIC);
            }});
        entity.add(new SpriteComponent(sprite));
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
