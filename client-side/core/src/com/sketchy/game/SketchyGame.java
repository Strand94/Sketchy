package com.sketchy.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sketchy.game.Controllers.ClientController;

public class SketchyGame extends Game {

    private SpriteBatch batch;
    private BitmapFont font;
    private ClientController controller;

    @Override
    public void create() {
        font = new BitmapFont(); // Use default Arial font
        batch = new SpriteBatch();
        controller = new ClientController(this);
    }

    @Override
    public void render() {
        super.render();
        controller.disposeViews();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    public SpriteBatch getSpriteBatch() {
        return batch;
    }
}
