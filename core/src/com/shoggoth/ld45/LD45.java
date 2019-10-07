package com.shoggoth.ld45;

import com.kothead.gdxjam.base.GdxJamGame;
import com.kothead.gdxjam.base.context.DefaultContext;
import com.kothead.gdxjam.base.data.GdxJamConfiguration;
import com.kothead.gdxjam.base.screen.LoadingScreen;
import com.shoggoth.ld45.screen.GameScreen;

public class LD45 extends GdxJamGame {

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
						Assets.images.COSMETIC,
						Assets.images.CEMETERY,
						Assets.images.CRYPT,
						Assets.images.ABYSS,
						Assets.images.NOTHING,
						Assets.images.DESKTILE
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
