package com.sketchy.game;

import android.os.Bundle;
import android.util.Base64;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.sketchy.game.Models.Drawing;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        SketchyGame.base64Interface = new Drawing.Base64Interface() {
            @Override
            public byte[] decodeBase64(String base64) {
                return Base64.decode(base64, Base64.DEFAULT);
            }

            @Override
            public String encodeBase64String(byte[] bytes) {
                return Base64.encodeToString(bytes, Base64.DEFAULT);
            }
        };
        initialize(new SketchyGame(), config);
    }

}
