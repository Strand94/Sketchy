package com.sketchy.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sketchy.game.Models.Notepad;
import com.sketchy.game.SketchyGame;

import java.util.ArrayList;
import java.util.List;

import static com.sketchy.game.communicator.Communicator.*;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        SketchyGame game = new SketchyGame();
        new LwjglApplication(game, config);
        config.width = 720 / 2;
        config.height = 1280 / 2;

        String notepadJson = "[{\"originalWord\":\"lemmen\",\"sheets\":[{\"objectiveWord\":\"hest\",\"drawing\":null,\"playerName\":\"Per\",\"answer\":null}],\"route\":[\"testAddress\"]}]";



    }
}
