package com.shoggoth.ld45;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kothead.gdxjam.base.data.loader.TextureRegionLoader;

public final class Assets {
  public static final AssetDescriptor[] ALL = {images.ABYSS, images.BACKGROUND, images.BORDER, images.CEMETERY, images.COSMETIC, images.CRYPT, images.DEMON, images.DESKTILE, images.GROUND, images.GROUND2, images.GROUND3, images.NOTHING, images.PRESENCE, images.PRESENCE2, images.RIVER, images.RIVER2, images.SKELETON, images.ZOMBIE};

  public static final class images {
    public static final AssetDescriptor<TextureRegion> ABYSS = new AssetDescriptor<TextureRegion>("images/pack.atlas#abyss", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "abyss"));

    public static final AssetDescriptor<TextureRegion> BACKGROUND = new AssetDescriptor<TextureRegion>("images/pack.atlas#background", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "background"));

    public static final AssetDescriptor<TextureRegion> BORDER = new AssetDescriptor<TextureRegion>("images/pack.atlas#border", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "border"));

    public static final AssetDescriptor<TextureRegion> CEMETERY = new AssetDescriptor<TextureRegion>("images/pack.atlas#cemetery", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "cemetery"));

    public static final AssetDescriptor<TextureRegion> COSMETIC = new AssetDescriptor<TextureRegion>("images/pack.atlas#cosmetic", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "cosmetic"));

    public static final AssetDescriptor<TextureRegion> CRYPT = new AssetDescriptor<TextureRegion>("images/pack.atlas#crypt", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "crypt"));

    public static final AssetDescriptor<TextureRegion> DEMON = new AssetDescriptor<TextureRegion>("images/pack.atlas#demon", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "demon"));

    public static final AssetDescriptor<TextureRegion> DESKTILE = new AssetDescriptor<TextureRegion>("images/pack.atlas#desktile", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "desktile"));

    public static final AssetDescriptor<TextureRegion> GROUND = new AssetDescriptor<TextureRegion>("images/pack.atlas#ground", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "ground"));

    public static final AssetDescriptor<TextureRegion> GROUND2 = new AssetDescriptor<TextureRegion>("images/pack.atlas#ground2", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "ground2"));

    public static final AssetDescriptor<TextureRegion> GROUND3 = new AssetDescriptor<TextureRegion>("images/pack.atlas#ground3", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "ground3"));

    public static final AssetDescriptor<TextureRegion> NOTHING = new AssetDescriptor<TextureRegion>("images/pack.atlas#nothing", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "nothing"));

    public static final AssetDescriptor<TextureRegion> PRESENCE = new AssetDescriptor<TextureRegion>("images/pack.atlas#presence", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "presence"));

    public static final AssetDescriptor<TextureRegion> PRESENCE2 = new AssetDescriptor<TextureRegion>("images/pack.atlas#presence2", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "presence2"));

    public static final AssetDescriptor<TextureRegion> RIVER = new AssetDescriptor<TextureRegion>("images/pack.atlas#river", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "river"));

    public static final AssetDescriptor<TextureRegion> RIVER2 = new AssetDescriptor<TextureRegion>("images/pack.atlas#river2", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "river2"));

    public static final AssetDescriptor<TextureRegion> SKELETON = new AssetDescriptor<TextureRegion>("images/pack.atlas#skeleton", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "skeleton"));

    public static final AssetDescriptor<TextureRegion> ZOMBIE = new AssetDescriptor<TextureRegion>("images/pack.atlas#zombie", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "zombie"));

    public static final AssetDescriptor[] ALL = {ABYSS, BACKGROUND, BORDER, CEMETERY, COSMETIC, CRYPT, DEMON, DESKTILE, GROUND, GROUND2, GROUND3, NOTHING, PRESENCE, PRESENCE2, RIVER, RIVER2, SKELETON, ZOMBIE};
  }
}
