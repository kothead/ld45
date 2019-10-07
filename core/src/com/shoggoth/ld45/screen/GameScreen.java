package com.shoggoth.ld45.screen;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.kothead.gdxjam.base.GdxJam;
import com.kothead.gdxjam.base.GdxJamGame;
import com.kothead.gdxjam.base.screen.BaseScreen;
import com.kothead.gdxjam.base.screen.ScreenBuilder;
import com.shoggoth.ld45.EntityManager;
import com.shoggoth.ld45.util.*;

import java.util.List;

public class GameScreen extends BaseScreen {

    private EntityManager manager;
    private RenderConfig renderConfig = new RenderConfig();
    private CardSelector selector;

    private ActionQueue actionQueue;
    private Entity[][] field;

    public GameScreen(GdxJamGame game, int fieldWidth, int fieldHeight) {
        super(game);
        field = new Entity[fieldHeight][fieldWidth];
        renderConfig.setFieldWidth(fieldWidth);
        renderConfig.setFieldHeight(fieldHeight);
    }

    @Override
    protected void layoutViewsLandscape(int width, int height) {
        layout(width, height);
    }

    @Override
    protected void layoutViewsPortrait(int width, int height) {
        layout(width, height);
    }

    protected void layout(int width, int height) {
        Team players = new Team(1, 2, true);
        Team enemies = new AITeam(2, 3);
        actionQueue = new ActionQueue(players, enemies);

        manager = new EntityManager(GdxJam.engine(), this, renderConfig, players, enemies);
        for (int i = 0; i < renderConfig.getFieldHeight(); i++) {
            for (int j = 0; j < renderConfig.getFieldWidth(); j++) {
                field[i][j] = manager.addCell(j, i);
            }
        }

        manager.addNothing(0, 1, players.getId());
        manager.addAbyss(0, 0, players.getId());
        manager.addZombie(1, 2, players.getId());

        manager.addCemetery(5, 1, players.getId());
        manager.addDemon(5, 2, players.getId());
        manager.addSkeleton(4, 2, players.getId());
    }

    public void showCardSelection(Entity sourceCard, List<Entity> cards) {
        selector = new CardSelector(this, GdxJam.engine(), renderConfig, sourceCard, cards);
        selector.animateMoveOut();
    }

    public void selectCard(Entity card) {
        selector.animateSelected(card);
        selector = null;
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
    }

    @Override
    public void dispose() {
        super.dispose();
        manager.dispose();
    }

    public Entity[][] getField() {
        return field;
    }

    public ActionQueue getActionQueue() {
        return actionQueue;
    }

    public EntityManager getEntityManager() {
        return manager;
    }

    public static final class Builder implements ScreenBuilder<GameScreen> {

        private int fieldWidth = 10;
        private int fieldHeight = 10;

        @Override
        public GameScreen build(GdxJamGame game) {
            return new GameScreen(game, fieldWidth, fieldHeight);
        }

        public Builder setFieldSize(int width, int height) {
            this.fieldWidth = width;
            this.fieldHeight = height;
            return this;
        }
    }
}
