package com.shoggoth.ld45;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kothead.gdxjam.base.data.loader.TextureRegionLoader;

public final class Assets {
  public static final AssetDescriptor[] ALL = {images.BACKGROUND, images.BORDER, images.COSMETIC, images.GROUND, images.PRESENCE, images.RIVER, images.SKELETON};

  public static final class images {
    public static final AssetDescriptor<TextureRegion> BACKGROUND = new AssetDescriptor<TextureRegion>("images/pack.atlas#background", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "background"));

    public static final AssetDescriptor<TextureRegion> BORDER = new AssetDescriptor<TextureRegion>("images/pack.atlas#border", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "border"));

    public static final AssetDescriptor<TextureRegion> COSMETIC = new AssetDescriptor<TextureRegion>("images/pack.atlas#cosmetic", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "cosmetic"));

    public static final AssetDescriptor<TextureRegion> GROUND = new AssetDescriptor<TextureRegion>("images/pack.atlas#ground", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "ground"));

    public static final AssetDescriptor<TextureRegion> PRESENCE = new AssetDescriptor<TextureRegion>("images/pack.atlas#presence", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "presence"));

    public static final AssetDescriptor<TextureRegion> RIVER = new AssetDescriptor<TextureRegion>("images/pack.atlas#river", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "river"));

    public static final AssetDescriptor<TextureRegion> SKELETON = new AssetDescriptor<TextureRegion>("images/pack.atlas#skeleton", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "skeleton"));

    public static final AssetDescriptor[] ALL = {BACKGROUND, BORDER, COSMETIC, GROUND, PRESENCE, RIVER, SKELETON};
  }
}
