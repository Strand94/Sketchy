package com.sketchy.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sketchy.game.Models.Drawing;
import com.sketchy.game.SketchyGame;

import org.apache.commons.codec.binary.Base64;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        SketchyGame.base64Interface = new Drawing.Base64Interface() {
            @Override
            public byte[] decodeBase64(String base64) {
                return Base64.decodeBase64(base64);
            }

            @Override
            public String encodeBase64String(byte[] bytes) {
                return Base64.encodeBase64String(bytes);
            }
        };
        SketchyGame game = new SketchyGame();
        new LwjglApplication(game, config);
        config.width = 720 / 2;
        config.height = 1280 / 2;


    }
}
