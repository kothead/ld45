package com.shoggoth.ld45.screen;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.kothead.gdxjam.base.GdxJam;
import com.kothead.gdxjam.base.GdxJamGame;
import com.kothead.gdxjam.base.screen.BaseScreen;
import com.kothead.gdxjam.base.screen.ScreenBuilder;
import com.kothead.gdxjam.base.util.Direction;
import com.shoggoth.ld45.EntityManager;
import com.shoggoth.ld45.component.SelectableComponent;
import com.shoggoth.ld45.component.SelectedComponent;
import com.shoggoth.ld45.util.RenderConfig;

import java.util.Collections;
import java.util.List;

public class GameScreen extends BaseScreen {

    private EntityManager manager;
    private RenderConfig renderConfig = new RenderConfig();

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
        manager = new EntityManager(GdxJam.engine(), this, renderConfig);
        for (int i = 0; i < renderConfig.getFieldHeight(); i++) {
            for (int j = 0; j < renderConfig.getFieldWidth(); j++) {
                field[i][j] = manager.addCell(j, i);
            }
        }

        getField()[0][0].add(new SelectableComponent());
        manager.addNothing(0, 1);
        manager.addAbyss(0, 0);
        manager.addSkeleton(1, 2);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(1, 1, 1, 1);
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

    public boolean hasSelectionSource() {
        for (int i = 0; i < renderConfig.getFieldHeight(); i++) {
            for (int j = 0; j < renderConfig.getFieldWidth(); j++) {
                Entity entity = getField()[i][j];
                if (SelectedComponent.mapper.has(entity)
                    && SelectedComponent.mapper.get(entity).type == SelectedComponent.SelectionType.SOURCE) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setNonSelectable() {
        for (int i = 0; i < renderConfig.getFieldHeight(); i++) {
            for (int j = 0; j < renderConfig.getFieldWidth(); j++) {
                Entity entity = getField()[i][j];
                entity.remove(SelectableComponent.class);
            }
        }
    }

    public void setSelectable(int x, int y) {
        setSelectable(x, y, Collections.EMPTY_LIST, true);
    }

    public void setSelectable(int x, int y, List<Direction> directions) {
        setSelectable(x, y, directions, false);
    }

    public void setSelectable(int x, int y, List<Direction> directions, boolean selectSelf) {
        setNonSelectable();
        if (x >= 0 && y >= 0 && x < renderConfig.getFieldWidth() && y < renderConfig.getFieldHeight()) {
            if (selectSelf) {
                Entity entity = getField()[y][x];
                entity.add(new SelectableComponent());
            }
            for (Direction direction : directions) {
                int newX = x + direction.getDx();
                int newY = y + direction.getDy();
                if (newX >= 0 && newY >= 0 && newX < renderConfig.getFieldWidth() && newY < renderConfig.getFieldHeight()) {
                    Entity entity = getField()[newY][newX];
                    entity.add(new SelectableComponent());
                }
            }
        }
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
