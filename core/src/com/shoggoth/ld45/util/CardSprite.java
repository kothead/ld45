package com.shoggoth.ld45.util;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kothead.gdxjam.base.GdxJam;

import java.util.ArrayList;
import java.util.List;

public class CardSprite extends Sprite {

    private Sprite border;
    private Sprite background;
    private List<Sprite> backgroundCosmetics;
    private Sprite base;
    private List<Sprite> cosmetics;

    public CardSprite(RenderConfig config) {
        super();
        setSize(config.getCardWidth(), config.getCardHeight());
    }

    public void setBorder(AssetDescriptor<TextureRegion> asset) {
        border = new Sprite(GdxJam.assets().get(asset));
    }

    public void setBackground(AssetDescriptor<TextureRegion> asset) {
        background = new Sprite(GdxJam.assets().get(asset));
    }

    public void setBackgroundCosmetics(List<AssetDescriptor<TextureRegion>> assets) {
        backgroundCosmetics = new ArrayList<>();
        for (AssetDescriptor<TextureRegion> asset : assets) {
            backgroundCosmetics.add(new Sprite(GdxJam.assets().get(asset)));
        }
    }

    public void setBase(AssetDescriptor<TextureRegion> asset) {
        base = new Sprite(GdxJam.assets().get(asset));
    }

    public void setCosmetics(List<AssetDescriptor<TextureRegion>> assets) {
        cosmetics = new ArrayList<>();
        for (AssetDescriptor<TextureRegion> asset : assets) {
            cosmetics.add(new Sprite(GdxJam.assets().get(asset)));
        }
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        if (border != null) {
            border.setPosition(x, y);
        }
        if (background != null) {
            background.setPosition(x, y);
        }
        if (backgroundCosmetics != null) {
            for (Sprite sprite : backgroundCosmetics) {
                sprite.setPosition(x, y);
            }
        }
        if (base != null) {
            base.setPosition(x, y);
        }
        if (cosmetics != null) {
            for (Sprite sprite : cosmetics) {
                sprite.setPosition(x, y);
            }
        }
    }

    @Override
    public void setScale(float scaleXY) {
        super.setOrigin(0, 0);
        super.setScale(scaleXY);
        if (border != null) {
            border.setOrigin(0, 0);
            border.setScale(scaleXY);
        }
        if (background != null) {
            background.setOrigin(0, 0);
            background.setScale(scaleXY);
        }
        if (backgroundCosmetics != null) {
            for (Sprite sprite : backgroundCosmetics) {
                sprite.setOrigin(0, 0);
                sprite.setScale(scaleXY);
            }
        }
        if (base != null) {
            base.setOrigin(0, 0);
            base.setScale(scaleXY);
        }
        if (cosmetics != null) {
            for (Sprite sprite : cosmetics) {
                sprite.setOrigin(0, 0);
                sprite.setScale(scaleXY);
            }
        }
    }

    @Override
    public void draw(Batch batch) {
        if (background != null) {
            background.draw(batch);
        }
        if (backgroundCosmetics != null) {
            for (Sprite sprite : backgroundCosmetics) {
                sprite.draw(batch);
            }
        }
        if (base != null) {
            base.draw(batch);
        }
        if (cosmetics != null) {
            for (Sprite sprite : cosmetics) {
                sprite.draw(batch);
            }
        }
        if (border != null) {
            border.draw(batch);
        }
    }

    @Override
    public float getWidth() {
        return super.getWidth() * getScaleX();
    }

    @Override
    public float getHeight() {
        return super.getHeight() * getScaleY();
    }
}
