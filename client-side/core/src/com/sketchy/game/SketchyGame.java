package com.sketchy.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.sketchy.game.Controllers.ClientController;
import com.sketchy.game.Views.LoginView;
public class SketchyGame extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public Communicator communicator;
    public ClientController clientController;

    @Override
    public void create() {
        font = new BitmapFont(); // Use default Arial font
        batch = new SpriteBatch();
        communicator = new Communicator();
        clientController = new ClientController(this);
        this.setScreen(new LoginView(this));
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
