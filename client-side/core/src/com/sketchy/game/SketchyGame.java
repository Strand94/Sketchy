package com.sketchy.game;

import com.badlogic.gdx.Game;
import com.sketchy.game.Controllers.ClientController;
import com.sketchy.game.Models.Drawing;

public class SketchyGame extends Game {

    public static Drawing.Base64Interface base64Interface;

    @Override
    public void create() {
        new ClientController(this);
    }
    
}
