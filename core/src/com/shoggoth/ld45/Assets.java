package com.shoggoth.ld45;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kothead.gdxjam.base.data.loader.TextureRegionLoader;

public final class Assets {
  public static final AssetDescriptor[] ALL = {images.BADLOGIC};

  public static final class images {
    public static final AssetDescriptor<TextureRegion> BADLOGIC = new AssetDescriptor<TextureRegion>("images/pack.atlas#badlogic", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "badlogic"));

    public static final AssetDescriptor[] ALL = {BADLOGIC};
  }
}
