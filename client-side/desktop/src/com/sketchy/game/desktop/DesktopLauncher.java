package com.sketchy.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sketchy.game.communicator.Communicator;
import com.sketchy.game.SketchyGame;

import java.util.concurrent.TimeUnit;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		SketchyGame game = new SketchyGame();
		new LwjglApplication(game, config);
		config.width = 720/2;
		config.height = 1280/2;

	}
}
