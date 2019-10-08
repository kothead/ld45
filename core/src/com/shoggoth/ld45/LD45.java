package com.shoggoth.ld45;

import com.kothead.gdxjam.base.GdxJamGame;
import com.kothead.gdxjam.base.context.DefaultContext;
import com.kothead.gdxjam.base.data.GdxJamConfiguration;
import com.kothead.gdxjam.base.screen.LoadingScreen;
import com.shoggoth.ld45.screen.GameScreen;

import java.util.Random;

public class LD45 extends GdxJamGame {

	public static Random random = new Random(13);

	public LD45() {
		super(new GameConfiguration());
	}

	@Override
	public void create () {
		super.create();
		showGameScreen();
	}

	public void showGameScreen() {
		getStateMachine().changeState(
				DefaultContext.create(
						new GameScreen.Builder(),
						new LoadingScreen.Builder(),
						Assets.images.BORDER,
						Assets.images.BORDERENEMY,
						Assets.images.BACKGROUND,
						Assets.images.RIVER,
						Assets.images.RIVER2,
						Assets.images.RIVER3,
						Assets.images.GROUND,
						Assets.images.GROUND2,
						Assets.images.GROUND3,
						Assets.images.PRESENCE,
						Assets.images.PRESENCE2,
						Assets.images.PRESENCE3,
						Assets.images.SKELETON,
						Assets.images.DEMON,
						Assets.images.ZOMBIE,
						Assets.images.CEMETERY,
						Assets.images.CRYPT,
						Assets.images.ABYSS,
						Assets.images.NOTHING,
						Assets.images.DESKTILE,
						Assets.images.BLOODRIVER,
						Assets.images.CURSEDGROUND,
						Assets.images.SELECTOR,
						Assets.images.DEMONIC,
						Assets.images.DAMAGED1,
						Assets.images.DAMAGED2,

						Assets.images.DEVILFURIOUS,
						Assets.images.DEVILHOLY,
						Assets.images.DEVILMIGHTY,
						Assets.images.DEVILOVERLOAD,
						Assets.images.DEVILREAPING,
						Assets.images.DEVILSLASH,
						Assets.images.DEVILTERRY,
						Assets.images.DEVILTHORNY,
						Assets.images.DEVILVAMP,

						Assets.images.ZAMBFURIOUS,
						Assets.images.ZAMBHOLY,
						Assets.images.ZAMBMIGHTY,
						Assets.images.ZAMBOVERLOAD,
						Assets.images.ZAMBREAPING,
						Assets.images.ZAMBSLASH,
						Assets.images.ZAMBTERRY,
						Assets.images.ZAMBTHORNY,
						Assets.images.ZAMBVAMP,

						Assets.images.SKELETONFURIOUS,
						Assets.images.SKELETONHOLY,
						Assets.images.SKELETONMIGHTY,
						Assets.images.SKELETONOVERLOAD,
						Assets.images.SKELETONREAPING,
						Assets.images.SKELETONSLASH,
						Assets.images.SKELETONTERRY,
						Assets.images.SKELETONTHORNY,
						Assets.images.SKELETONVAMP,

						Assets.sounds.MOVE,
						Assets.sounds.TAP,
						Assets.sounds.DAMAGE,
						Assets.sounds.HIT,
						Assets.sounds.SPAWN,
						Assets.sounds.DEATH
				));
	}

	public static class GameConfiguration extends GdxJamConfiguration {
		GameConfiguration() {
			width = 720;
			height = 1280;
			scaleFactor = 1.0f;
		}
	}
}
