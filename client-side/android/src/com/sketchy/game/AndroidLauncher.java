package com.sketchy.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.sketchy.game.SketchyGame;
import com.sketchy.game.communicator.Communicator;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new SketchyGame(), config);

		try {
			Communicator communicator = new Communicator();
			communicator.joinLobby(1337, "hans gregor");
		} catch (Exception e) {
			System.out.println("Can't connect to server");
		}
	}

}
