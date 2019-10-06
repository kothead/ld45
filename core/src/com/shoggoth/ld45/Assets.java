package com.shoggoth.ld45;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kothead.gdxjam.base.data.loader.TextureRegionLoader;

public final class Assets {
  public static final AssetDescriptor[] ALL = {images.GROUND_2, images.PRESENCE_2, images.RIVER_2, images.ABYSS, images.BACKGROUND, images.BORDER, images.CEMETERY, images.COSMETIC, images.CRYPT, images.DEMON, images.GROUND, images.NOTHING, images.PRESENCE, images.RIVER, images.SKELETON, images.ZOMBIE};

  public static final class images {
    public static final AssetDescriptor<TextureRegion> GROUND_2 = new AssetDescriptor<TextureRegion>("images/pack.atlas#ground", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "ground", 2));

    public static final AssetDescriptor<TextureRegion> PRESENCE_2 = new AssetDescriptor<TextureRegion>("images/pack.atlas#presence", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "presence", 2));

    public static final AssetDescriptor<TextureRegion> RIVER_2 = new AssetDescriptor<TextureRegion>("images/pack.atlas#river", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "river", 2));

    public static final AssetDescriptor<TextureRegion> ABYSS = new AssetDescriptor<TextureRegion>("images/pack.atlas#abyss", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "abyss"));

    public static final AssetDescriptor<TextureRegion> BACKGROUND = new AssetDescriptor<TextureRegion>("images/pack.atlas#background", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "background"));

    public static final AssetDescriptor<TextureRegion> BORDER = new AssetDescriptor<TextureRegion>("images/pack.atlas#border", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "border"));

    public static final AssetDescriptor<TextureRegion> CEMETERY = new AssetDescriptor<TextureRegion>("images/pack.atlas#cemetery", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "cemetery"));

    public static final AssetDescriptor<TextureRegion> COSMETIC = new AssetDescriptor<TextureRegion>("images/pack.atlas#cosmetic", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "cosmetic"));

    public static final AssetDescriptor<TextureRegion> CRYPT = new AssetDescriptor<TextureRegion>("images/pack.atlas#crypt", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "crypt"));

    public static final AssetDescriptor<TextureRegion> DEMON = new AssetDescriptor<TextureRegion>("images/pack.atlas#demon", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "demon"));

    public static final AssetDescriptor<TextureRegion> GROUND = new AssetDescriptor<TextureRegion>("images/pack.atlas#ground", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "ground"));

    public static final AssetDescriptor<TextureRegion> NOTHING = new AssetDescriptor<TextureRegion>("images/pack.atlas#nothing", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "nothing"));

    public static final AssetDescriptor<TextureRegion> PRESENCE = new AssetDescriptor<TextureRegion>("images/pack.atlas#presence", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "presence"));

    public static final AssetDescriptor<TextureRegion> RIVER = new AssetDescriptor<TextureRegion>("images/pack.atlas#river", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "river"));

    public static final AssetDescriptor<TextureRegion> SKELETON = new AssetDescriptor<TextureRegion>("images/pack.atlas#skeleton", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "skeleton"));

    public static final AssetDescriptor<TextureRegion> ZOMBIE = new AssetDescriptor<TextureRegion>("images/pack.atlas#zombie", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "zombie"));

    public static final AssetDescriptor[] ALL = {GROUND_2, PRESENCE_2, RIVER_2, ABYSS, BACKGROUND, BORDER, CEMETERY, COSMETIC, CRYPT, DEMON, GROUND, NOTHING, PRESENCE, RIVER, SKELETON, ZOMBIE};
  }
}
