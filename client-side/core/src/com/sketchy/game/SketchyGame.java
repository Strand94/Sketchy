package com.sketchy.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.sketchy.game.Views.LoginView;
public class SketchyGame extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public Communicator communicator;

    @Override
    public void create() {
        font = new BitmapFont(); // Use default Arial font
        batch = new SpriteBatch();
        this.setScreen(new LoginView(this));
        communicator = new Communicator();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
