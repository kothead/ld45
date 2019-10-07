package com.shoggoth.ld45.util;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.kothead.gdxjam.base.component.PositionComponent;
import com.shoggoth.ld45.EntityManager;
import com.shoggoth.ld45.component.*;
import com.shoggoth.ld45.screen.GameScreen;
import com.shoggoth.ld45.system.AISystem;
import com.shoggoth.ld45.system.FieldHighlightRenderSystem;
import com.shoggoth.ld45.system.InputSystem;
import com.shoggoth.ld45.system.SelectionInputSystem;

import java.util.List;

public class CardSelector {

    private static final float MARGIN_HORIZONTAL = 50.0f;
    private static final float MARGIN_VERTICAL = 40.0f;
    private static final float CARD_SCALE_UP = 1.2f;
    private static final float FLY_OUT_THRESHOLD = 10.0f;
    private static final float COMMON_ANIMATION_DURATION = 0.5f;

    private static final int CARDS_IN_ROW = 3;

    private GameScreen screen;
    private EntityManager manager;
    private RenderConfig config;
    private Entity sourceCard;
    private List<Entity> cards;
    private Entity blackness;

    public CardSelector(GameScreen screen, EntityManager manager, RenderConfig config, Entity sourceCard, List<Entity> cards) {
        this.screen = screen;
        this.manager = manager;
        this.config = config;
        this.sourceCard = sourceCard;
        this.cards = cards;

        for (Entity card: cards) {
            card.add(new SelectableComponent());
        }
    }

    public void animateMoveOut() {
        manager.pause(AISystem.class);
        manager.pause(InputSystem.class);
        manager.pause(FieldHighlightRenderSystem.class);

        Vector3 camera = screen.getCamera().position;
        int rowCount = cards.size() / CARDS_IN_ROW;

        float width = screen.getWorldWidth() - 2 * MARGIN_HORIZONTAL;
        float height = screen.getWorldHeight() - 2 * MARGIN_VERTICAL;

        float cardWidth = config.getCardWidth() * CARD_SCALE_UP;
        float cardHeight = config.getCardHeight() * CARD_SCALE_UP;

        float paddingX = (width / CARDS_IN_ROW - cardWidth) / 2.0f;
        float paddingY = (height / rowCount - cardHeight) / 2.0f;

        float startX = camera.x - screen.getWorldWidth() / 2.0f;
        float startY = camera.y - screen.getWorldHeight() / 2.0f;

        float dx = 2 * paddingX + cardWidth;
        float dy = 2 * paddingY + cardHeight;

        final Vector3 origin = PositionComponent.mapper.get(sourceCard).position;
        int count = 0;
        origin.z = 3.0f;

        for (Entity entity: cards) {
            entity.add(new SelectableComponent());

            float x = startX + MARGIN_HORIZONTAL + paddingX + count % CARDS_IN_ROW * dx;
            float y = startY + MARGIN_VERTICAL + paddingY + count / CARDS_IN_ROW * dy;

            float originx = origin.x + config.getCardWidth() / 2.0f;
            float originy = origin.y + config.getCardHeight() / 2.0f;
            boolean horizontal = Math.abs(camera.x - originx) > Math.abs(camera.y - originy);
            float tx = origin.x + (horizontal ? Math.signum(camera.x - originx) * dx: 0);
            float ty = origin.y + (!horizontal ? Math.signum(camera.y - originy) * dy: 0);

            InterpolationPositionComponent interpolationPosition = new InterpolationPositionComponent(
                    Interpolation.smoother,
                    new Vector3(origin.x, origin.y, 1.0f),
                    new Vector3(tx, ty, 2.0f),
                    0,
                    COMMON_ANIMATION_DURATION
            );
            interpolationPosition.callback = new InterpolationPositionComponent.InterpolationCallback() {
                @Override
                public void onInterpolationFinished(Entity entity) {
                    origin.z = 0.0f;
                    entity.add(new InterpolationScaleComponent(
                            Interpolation.smoother,
                            1.0f,
                            CARD_SCALE_UP,
                            COMMON_ANIMATION_DURATION
                    ));
                }
            };
            interpolationPosition.next = new InterpolationPositionComponent(
                    Interpolation.smoother,
                    new Vector3(tx, ty, 2.0f),
                    new Vector3(x, y, 2.0f),
                    0,
                    COMMON_ANIMATION_DURATION
            );
            interpolationPosition.next.callback = new InterpolationPositionComponent.InterpolationCallback() {
                @Override
                public void onInterpolationFinished(Entity entity) {
                    manager.resume(SelectionInputSystem.class);
                }
            };
            entity.add(interpolationPosition);
            count++;
        }

        createBlackness();
    }

    private void createBlackness() {
        blackness = manager.addBlackness();
        blackness.add(new InterpolationTintComponent(
                Interpolation.smoother,
                Color.BLACK,
                0.0f,
                0.7f,
                COMMON_ANIMATION_DURATION,
                COMMON_ANIMATION_DURATION
        ));
    }

    private void removeBlacknes() {
        InterpolationTintComponent interpolationTint = new InterpolationTintComponent(
                Interpolation.smoother,
                Color.BLACK,
                0.7f,
                0.0f,
                0.0f,
                COMMON_ANIMATION_DURATION
        );
        interpolationTint.callback = new InterpolationTintComponent.InterpolationCallback() {
            @Override
            public void onInterpolationFinished(Entity entity) {
                manager.removeEntity(entity);
            }
        };
        blackness.add(interpolationTint);
    }

    public void animateSelected(Entity selectedCard) {
        manager.pause(SelectionInputSystem.class);
        removeBlacknes();

        for (Entity card: cards) {
            card.remove(SelectableComponent.class);
            Vector3 origin = PositionComponent.mapper.get(card).position;

            if (card == selectedCard) {
                Entity cell = AttachComponent.mapper.get(sourceCard).entity;
                final Entity oldNothing = manager.detachOldNothing(cell);
                Vector3 oldNothingPosition = PositionComponent.mapper.get(sourceCard).position;

                manager.attach(cell, card);

                InterpolationPositionComponent interpolationPosition = new InterpolationPositionComponent(
                        Interpolation.smoother,
                        origin,
                        new Vector3(oldNothingPosition.x, oldNothingPosition.y, 0.0f),
                        0,
                        COMMON_ANIMATION_DURATION
                );
                interpolationPosition.callback = new InterpolationPositionComponent.InterpolationCallback() {
                    @Override
                    public void onInterpolationFinished(Entity entity) {
                        manager.removeEntity(oldNothing);
                        screen.getActionQueue().nextAction();
                    }
                };
                card.add(interpolationPosition);
                card.add(new InterpolationScaleComponent(
                        Interpolation.smoother,
                        CARD_SCALE_UP,
                        1.0f,
                        COMMON_ANIMATION_DURATION
                ));
            } else {
                InterpolationPositionComponent interpolationPosition = new InterpolationPositionComponent(
                        Interpolation.smoother,
                        origin,
                        calcThrowAwayPosition(origin),
                        0,
                        COMMON_ANIMATION_DURATION
                );
                interpolationPosition.callback = new InterpolationPositionComponent.InterpolationCallback() {
                    @Override
                    public void onInterpolationFinished(Entity entity) {
                        manager.removeEntity(entity);
                        manager.resume(InputSystem.class);
                        manager.resume(FieldHighlightRenderSystem.class);
                        manager.resume(AISystem.class);
                    }
                };
                card.add(interpolationPosition);
            }
        }
    }

    private Vector3 calcThrowAwayPosition(Vector3 origin) {
        Vector3 camera = screen.getCamera().position;

        float cardWidth = config.getCardWidth() * CARD_SCALE_UP;
        float cardHeight = config.getCardHeight() * CARD_SCALE_UP;

        boolean left = false;
        boolean right = false;
        boolean top = false;
        boolean bottom = false;

        if (cards.size() / CARDS_IN_ROW > 1) {
            left = camera.x - origin.x - cardWidth / 2.0f - FLY_OUT_THRESHOLD > 0;
            right = camera.x - origin.x - cardWidth / 2.0f + FLY_OUT_THRESHOLD < 0;
            bottom = camera.y - origin.y - cardHeight / 2.0f - FLY_OUT_THRESHOLD > 0;
            top = camera.y - origin.y - cardHeight / 2.0f + FLY_OUT_THRESHOLD < 0;
            if (!left && !right && !top && !bottom) top = true;
        } else {
            top = true;
        }

        float tx = (right
                ? screen.getWorldWidth() / 2.0f + camera.x
                : (left ? camera.x - screen.getWorldWidth() / 2.0f - cardWidth : origin.x));
        float ty = (top
                ? screen.getWorldHeight() / 2.0f + camera.y
                : (bottom ? camera.y - screen.getWorldHeight() / 2.0f - cardHeight : origin.y));
        return new Vector3(tx, ty, 1.0f);
    }
}
;