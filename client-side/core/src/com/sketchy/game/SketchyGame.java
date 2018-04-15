package com.sketchy.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.sketchy.game.Controllers.ClientController;
import com.sketchy.game.Views.LoginView;
import com.sketchy.game.communicator.Communicator;

public class SketchyGame extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    private Communicator communicator;
    private ClientController clientController;

    public int MAX_PLAYERS = 8;

    @Override
    public void create() {
        font = new BitmapFont(); // Use default Arial font
        batch = new SpriteBatch();
        clientController = new ClientController(this);


        LoginView loginView = new LoginView(this);
        this.setScreen(loginView);
        clientController.setView(loginView);

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
