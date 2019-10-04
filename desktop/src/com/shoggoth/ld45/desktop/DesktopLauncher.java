package com.shoggoth.ld45.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.shoggoth.ld45.LD45;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		LD45 game = new LD45();

		config.width = game.getConfiguration().height;
		config.height = game.getConfiguration().width;
		config.samples = 8;
		config.resizable = false;
		new LwjglApplication(game, config);
	}
}
