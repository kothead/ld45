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
						new LoadingScreen.Builder()
						// TODO: add assets here
				));
	}

	public static class GameConfiguration extends GdxJamConfiguration {
		GameConfiguration() {
			width = 720;
			height = 1280;
		}
	}
}
