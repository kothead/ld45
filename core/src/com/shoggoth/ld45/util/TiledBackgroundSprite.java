package com.shoggoth.ld45.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class TiledBackgroundSprite extends Sprite {

    private Vector2 offset = new Vector2(0.0f, 0.0f);

    public TiledBackgroundSprite(TextureRegion region) {
        super(region);
    }

    public TiledBackgroundSprite(TextureRegion region, float width, float height) {
        this(region);
        setSize(width, height);
    }

    @Override
    public void draw(Batch batch) {
        float x = offset.x % getRegionWidth() - getRegionWidth();
        float y = offset.y % getRegionHeight() - getRegionHeight();

        for (float i = getY() + y; i < getY() + getHeight(); i += getRegionHeight()) {
            for (float j = getX() + x; j < getX() + getWidth(); j += getRegionWidth()) {
                // V2 and V1 should be reversed, because the coordinate system of textures
                // rendering in a sprite batch is from bottom to top, but the system
                // which is used in a texture region is from top to bottom
                batch.draw(getTexture(), j, i, getRegionWidth(), getRegionHeight(),
                        getU(), getV2(), getU2(), getV());
            }
        }
    }

    public void offset(Vector2 offset) {
        offset(offset.x, offset.y);
    }

    public void offset(float x, float y) {
        offset.add(x, y);
        cutExcess();
    }

    public void setOffset(Vector2 offset) {
        setOffset(offset.x, offset.y);
    }

    public void setOffset(float x, float y) {
        offset.set(x, y);
        cutExcess();
    }

    private void cutExcess() {
        if (Math.abs(offset.x) > getRegionWidth()) offset.x -= Math.copySign(getRegionWidth(), offset.x);
        if (Math.abs(offset.y) > getRegionHeight()) offset.y -= Math.copySign(getRegionHeight(), offset.y);
    }
}
