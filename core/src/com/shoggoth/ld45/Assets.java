package com.shoggoth.ld45;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kothead.gdxjam.base.data.loader.TextureRegionLoader;

public final class Assets {
  public static final AssetDescriptor[] ALL = {images.ABYSS, images.BACKGROUND, images.BLOODRIVER, images.BORDER, images.BORDERENEMY, images.CEMETERY, images.CRYPT, images.CURSEDGROUND, images.DAMAGED1, images.DAMAGED2, images.DEMON, images.DEMONIC, images.DEVILFURIOUS, images.DEVILHOLY, images.DEVILMIGHTY, images.DEVILOVERLOAD, images.DEVILREAPING, images.DEVILSLASH, images.DEVILTERRY, images.DEVILTHORNY, images.DEVILVAMP, images.GROUND, images.GROUND2, images.GROUND3, images.NOTHING, images.PRESENCE, images.PRESENCE2, images.PRESENCE3, images.RIVER, images.RIVER2, images.RIVER3, images.SELECTOR, images.SKELETON, images.SKELETONFURIOUS, images.SKELETONHOLY, images.DESKTILE, images.SKELETONMIGHTY, images.SKELETONOVERLOAD, images.SKELETONREAPING, images.SKELETONSLASH, images.SKELETONTERRY, images.SKELETONTHORNY, images.SKELETONVAMP, images.ZAMBFURIOUS, images.ZAMBHOLY, images.ZAMBMIGHTY, images.ZAMBOVERLOAD, images.ZAMBREAPING, images.ZAMBSLASH, images.ZAMBTERRY, images.ZAMBTHORNY, images.ZAMBVAMP, images.ZOMBIE, sounds.DAMAGE, sounds.DEATH, sounds.HIT, sounds.MOVE, sounds.SPAWN, sounds.TAP};

  public static final class images {
    public static final AssetDescriptor<TextureRegion> ABYSS = new AssetDescriptor<TextureRegion>("images\\pack.atlas#abyss", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "abyss"));

    public static final AssetDescriptor<TextureRegion> BACKGROUND = new AssetDescriptor<TextureRegion>("images\\pack.atlas#background", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "background"));

    public static final AssetDescriptor<TextureRegion> BLOODRIVER = new AssetDescriptor<TextureRegion>("images\\pack.atlas#bloodriver", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "bloodriver"));

    public static final AssetDescriptor<TextureRegion> BORDER = new AssetDescriptor<TextureRegion>("images\\pack.atlas#border", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "border"));

    public static final AssetDescriptor<TextureRegion> BORDERENEMY = new AssetDescriptor<TextureRegion>("images\\pack.atlas#borderenemy", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "borderenemy"));

    public static final AssetDescriptor<TextureRegion> CEMETERY = new AssetDescriptor<TextureRegion>("images\\pack.atlas#cemetery", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "cemetery"));

    public static final AssetDescriptor<TextureRegion> CRYPT = new AssetDescriptor<TextureRegion>("images\\pack.atlas#crypt", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "crypt"));

    public static final AssetDescriptor<TextureRegion> CURSEDGROUND = new AssetDescriptor<TextureRegion>("images\\pack.atlas#cursedground", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "cursedground"));

    public static final AssetDescriptor<TextureRegion> DAMAGED1 = new AssetDescriptor<TextureRegion>("images\\pack.atlas#damaged1", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "damaged1"));

    public static final AssetDescriptor<TextureRegion> DAMAGED2 = new AssetDescriptor<TextureRegion>("images\\pack.atlas#damaged2", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "damaged2"));

    public static final AssetDescriptor<TextureRegion> DEMON = new AssetDescriptor<TextureRegion>("images\\pack.atlas#demon", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "demon"));

    public static final AssetDescriptor<TextureRegion> DEMONIC = new AssetDescriptor<TextureRegion>("images\\pack.atlas#demonic", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "demonic"));

    public static final AssetDescriptor<TextureRegion> DEVILFURIOUS = new AssetDescriptor<TextureRegion>("images\\pack.atlas#devilfurious", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "devilfurious"));

    public static final AssetDescriptor<TextureRegion> DEVILHOLY = new AssetDescriptor<TextureRegion>("images\\pack.atlas#devilholy", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "devilholy"));

    public static final AssetDescriptor<TextureRegion> DEVILMIGHTY = new AssetDescriptor<TextureRegion>("images\\pack.atlas#devilmighty", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "devilmighty"));

    public static final AssetDescriptor<TextureRegion> DEVILOVERLOAD = new AssetDescriptor<TextureRegion>("images\\pack.atlas#deviloverload", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "deviloverload"));

    public static final AssetDescriptor<TextureRegion> DEVILREAPING = new AssetDescriptor<TextureRegion>("images\\pack.atlas#devilreaping", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "devilreaping"));

    public static final AssetDescriptor<TextureRegion> DEVILSLASH = new AssetDescriptor<TextureRegion>("images\\pack.atlas#devilslash", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "devilslash"));

    public static final AssetDescriptor<TextureRegion> DEVILTERRY = new AssetDescriptor<TextureRegion>("images\\pack.atlas#devilterry", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "devilterry"));

    public static final AssetDescriptor<TextureRegion> DEVILTHORNY = new AssetDescriptor<TextureRegion>("images\\pack.atlas#devilthorny", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "devilthorny"));

    public static final AssetDescriptor<TextureRegion> DEVILVAMP = new AssetDescriptor<TextureRegion>("images\\pack.atlas#devilvamp", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "devilvamp"));

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

    public static final AssetDescriptor<TextureRegion> SKELETONFURIOUS = new AssetDescriptor<TextureRegion>("images\\pack.atlas#skeletonfurious", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "skeletonfurious"));

    public static final AssetDescriptor<TextureRegion> SKELETONHOLY = new AssetDescriptor<TextureRegion>("images\\pack.atlas#skeletonholy", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "skeletonholy"));

    public static final AssetDescriptor<TextureRegion> DESKTILE = new AssetDescriptor<TextureRegion>("images\\pack.atlas#desktile", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "desktile"));

    public static final AssetDescriptor<TextureRegion> SKELETONMIGHTY = new AssetDescriptor<TextureRegion>("images\\pack.atlas#skeletonmighty", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "skeletonmighty"));

    public static final AssetDescriptor<TextureRegion> SKELETONOVERLOAD = new AssetDescriptor<TextureRegion>("images\\pack.atlas#skeletonoverload", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "skeletonoverload"));

    public static final AssetDescriptor<TextureRegion> SKELETONREAPING = new AssetDescriptor<TextureRegion>("images\\pack.atlas#skeletonreaping", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "skeletonreaping"));

    public static final AssetDescriptor<TextureRegion> SKELETONSLASH = new AssetDescriptor<TextureRegion>("images\\pack.atlas#skeletonslash", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "skeletonslash"));

    public static final AssetDescriptor<TextureRegion> SKELETONTERRY = new AssetDescriptor<TextureRegion>("images\\pack.atlas#skeletonterry", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "skeletonterry"));

    public static final AssetDescriptor<TextureRegion> SKELETONTHORNY = new AssetDescriptor<TextureRegion>("images\\pack.atlas#skeletonthorny", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "skeletonthorny"));

    public static final AssetDescriptor<TextureRegion> SKELETONVAMP = new AssetDescriptor<TextureRegion>("images\\pack.atlas#skeletonvamp", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "skeletonvamp"));

    public static final AssetDescriptor<TextureRegion> ZAMBFURIOUS = new AssetDescriptor<TextureRegion>("images\\pack.atlas#zambfurious", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "zambfurious"));

    public static final AssetDescriptor<TextureRegion> ZAMBHOLY = new AssetDescriptor<TextureRegion>("images\\pack.atlas#zambholy", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "zambholy"));

    public static final AssetDescriptor<TextureRegion> ZAMBMIGHTY = new AssetDescriptor<TextureRegion>("images\\pack.atlas#zambmighty", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "zambmighty"));

    public static final AssetDescriptor<TextureRegion> ZAMBOVERLOAD = new AssetDescriptor<TextureRegion>("images\\pack.atlas#zamboverload", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "zamboverload"));

    public static final AssetDescriptor<TextureRegion> ZAMBREAPING = new AssetDescriptor<TextureRegion>("images\\pack.atlas#zambreaping", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "zambreaping"));

    public static final AssetDescriptor<TextureRegion> ZAMBSLASH = new AssetDescriptor<TextureRegion>("images\\pack.atlas#zambslash", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "zambslash"));

    public static final AssetDescriptor<TextureRegion> ZAMBTERRY = new AssetDescriptor<TextureRegion>("images\\pack.atlas#zambterry", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "zambterry"));

    public static final AssetDescriptor<TextureRegion> ZAMBTHORNY = new AssetDescriptor<TextureRegion>("images\\pack.atlas#zambthorny", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "zambthorny"));

    public static final AssetDescriptor<TextureRegion> ZAMBVAMP = new AssetDescriptor<TextureRegion>("images\\pack.atlas#zambvamp", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "zambvamp"));

    public static final AssetDescriptor<TextureRegion> ZOMBIE = new AssetDescriptor<TextureRegion>("images\\pack.atlas#zombie", TextureRegion.class, new TextureRegionLoader.TextureRegionParameter("images/pack.atlas", "zombie"));

    public static final AssetDescriptor[] ALL = {ABYSS, BACKGROUND, BLOODRIVER, BORDER, BORDERENEMY, CEMETERY, CRYPT, CURSEDGROUND, DAMAGED1, DAMAGED2, DEMON, DEMONIC, DEVILFURIOUS, DEVILHOLY, DEVILMIGHTY, DEVILOVERLOAD, DEVILREAPING, DEVILSLASH, DEVILTERRY, DEVILTHORNY, DEVILVAMP, GROUND, GROUND2, GROUND3, NOTHING, PRESENCE, PRESENCE2, PRESENCE3, RIVER, RIVER2, RIVER3, SELECTOR, SKELETON, SKELETONFURIOUS, SKELETONHOLY, DESKTILE, SKELETONMIGHTY, SKELETONOVERLOAD, SKELETONREAPING, SKELETONSLASH, SKELETONTERRY, SKELETONTHORNY, SKELETONVAMP, ZAMBFURIOUS, ZAMBHOLY, ZAMBMIGHTY, ZAMBOVERLOAD, ZAMBREAPING, ZAMBSLASH, ZAMBTERRY, ZAMBTHORNY, ZAMBVAMP, ZOMBIE};
  }

  public static final class sounds {
    public static final AssetDescriptor<Sound> DAMAGE = new AssetDescriptor<Sound>("sounds\\damage.wav", Sound.class);

    public static final AssetDescriptor<Sound> DEATH = new AssetDescriptor<Sound>("sounds\\death.wav", Sound.class);

    public static final AssetDescriptor<Sound> HIT = new AssetDescriptor<Sound>("sounds\\hit.wav", Sound.class);

    public static final AssetDescriptor<Sound> MOVE = new AssetDescriptor<Sound>("sounds\\move.wav", Sound.class);

    public static final AssetDescriptor<Sound> SPAWN = new AssetDescriptor<Sound>("sounds\\spawn.wav", Sound.class);

    public static final AssetDescriptor<Sound> TAP = new AssetDescriptor<Sound>("sounds\\tap.wav", Sound.class);

    public static final AssetDescriptor[] ALL = {DAMAGE, DEATH, HIT, MOVE, SPAWN, TAP};
  }
}
