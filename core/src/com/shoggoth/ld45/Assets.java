package com.shoggoth.ld45;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kothead.gdxjam.base.data.loader.TextureRegionLoader;

public final class Assets {
  public static final AssetDescriptor[] ALL = {images.ABYSS, images.BACKGROUND, images.BLOODRIVER, images.BORDER, images.BORDERENEMY, images.CEMETERY, images.COSMETIC, images.CRYPT, images.CURSEDGROUND, images.DEMON, images.DEMONIC, images.DESKTILE, images.GROUND, images.GROUND2, images.GROUND3, images.NOTHING, images.PRESENCE, images.PRESENCE2, images.PRESENCE3, images.RIVER, images.RIVER2, images.RIVER3, images.SELECTOR, images.SKELETON, images.ZOMBIE, sounds.CARD, sounds.TAP};

  public static final class images {
    public static final AssetDescriptor<TextureRegion> ABYSS = new AssetDescriptor<TextureRegion>("images\\pack.atlas#abyss", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "abyss"));

    public static final AssetDescriptor<TextureRegion> BACKGROUND = new AssetDescriptor<TextureRegion>("images\\pack.atlas#background", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "background"));

    public static final AssetDescriptor<TextureRegion> BLOODRIVER = new AssetDescriptor<TextureRegion>("images\\pack.atlas#bloodriver", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "bloodriver"));

    public static final AssetDescriptor<TextureRegion> BORDER = new AssetDescriptor<TextureRegion>("images\\pack.atlas#border", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "border"));

    public static final AssetDescriptor<TextureRegion> BORDERENEMY = new AssetDescriptor<TextureRegion>("images\\pack.atlas#borderenemy", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "borderenemy"));

    public static final AssetDescriptor<TextureRegion> CEMETERY = new AssetDescriptor<TextureRegion>("images\\pack.atlas#cemetery", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "cemetery"));

    public static final AssetDescriptor<TextureRegion> COSMETIC = new AssetDescriptor<TextureRegion>("images\\pack.atlas#cosmetic", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "cosmetic"));

    public static final AssetDescriptor<TextureRegion> CRYPT = new AssetDescriptor<TextureRegion>("images\\pack.atlas#crypt", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "crypt"));

    public static final AssetDescriptor<TextureRegion> CURSEDGROUND = new AssetDescriptor<TextureRegion>("images\\pack.atlas#cursedground", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "cursedground"));

    public static final AssetDescriptor<TextureRegion> DEMON = new AssetDescriptor<TextureRegion>("images\\pack.atlas#demon", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "demon"));

    public static final AssetDescriptor<TextureRegion> DEMONIC = new AssetDescriptor<TextureRegion>("images\\pack.atlas#demonic", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "demonic"));

    public static final AssetDescriptor<TextureRegion> DESKTILE = new AssetDescriptor<TextureRegion>("images\\pack.atlas#desktile", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "desktile"));

    public static final AssetDescriptor<TextureRegion> GROUND = new AssetDescriptor<TextureRegion>("images\\pack.atlas#ground", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "ground"));

    public static final AssetDescriptor<TextureRegion> GROUND2 = new AssetDescriptor<TextureRegion>("images\\pack.atlas#ground2", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "ground2"));

    public static final AssetDescriptor<TextureRegion> GROUND3 = new AssetDescriptor<TextureRegion>("images\\pack.atlas#ground3", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "ground3"));

    public static final AssetDescriptor<TextureRegion> NOTHING = new AssetDescriptor<TextureRegion>("images\\pack.atlas#nothing", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "nothing"));

    public static final AssetDescriptor<TextureRegion> PRESENCE = new AssetDescriptor<TextureRegion>("images\\pack.atlas#presence", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "presence"));

    public static final AssetDescriptor<TextureRegion> PRESENCE2 = new AssetDescriptor<TextureRegion>("images\\pack.atlas#presence2", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "presence2"));

    public static final AssetDescriptor<TextureRegion> PRESENCE3 = new AssetDescriptor<TextureRegion>("images\\pack.atlas#presence3", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "presence3"));

    public static final AssetDescriptor<TextureRegion> RIVER = new AssetDescriptor<TextureRegion>("images\\pack.atlas#river", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "river"));

    public static final AssetDescriptor<TextureRegion> RIVER2 = new AssetDescriptor<TextureRegion>("images\\pack.atlas#river2", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "river2"));

    public static final AssetDescriptor<TextureRegion> RIVER3 = new AssetDescriptor<TextureRegion>("images\\pack.atlas#river3", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "river3"));

    public static final AssetDescriptor<TextureRegion> SELECTOR = new AssetDescriptor<TextureRegion>("images\\pack.atlas#selector", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "selector"));

    public static final AssetDescriptor<TextureRegion> SKELETON = new AssetDescriptor<TextureRegion>("images\\pack.atlas#skeleton", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "skeleton"));

    public static final AssetDescriptor<TextureRegion> ZOMBIE = new AssetDescriptor<TextureRegion>("images\\pack.atlas#zombie", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "zombie"));

    public static final AssetDescriptor[] ALL = {ABYSS, BACKGROUND, BLOODRIVER, BORDER, BORDERENEMY, CEMETERY, COSMETIC, CRYPT, CURSEDGROUND, DEMON, DEMONIC, DESKTILE, GROUND, GROUND2, GROUND3, NOTHING, PRESENCE, PRESENCE2, PRESENCE3, RIVER, RIVER2, RIVER3, SELECTOR, SKELETON, ZOMBIE};
  }

  public static final class sounds {
    public static final AssetDescriptor<Sound> CARD = new AssetDescriptor<Sound>("sounds\\card.wav", Sound.class);

    public static final AssetDescriptor<Sound> TAP = new AssetDescriptor<Sound>("sounds\\tap.wav", Sound.class);

    public static final AssetDescriptor[] ALL = {CARD, TAP};
  }
}
