package com.sketchy.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sketchy.game.Controllers.ClientController;

public class SketchyGame extends Game {

    private ClientController controller;

    @Override
    public void create() {
        controller = new ClientController(this);
    }

    @Override
    public void render() {
        super.render();
        controller.disposeViews();
    }

    @Override
    public void dispose() {

    }
    
}
