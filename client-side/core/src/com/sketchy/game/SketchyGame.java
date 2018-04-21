package com.sketchy.game;

import com.badlogic.gdx.Game;
import com.sketchy.game.Controllers.ClientController;

public class SketchyGame extends Game {

    @Override
    public void create() {
        new ClientController(this);
    }
    
}
