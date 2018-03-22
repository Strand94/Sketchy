package com.sketchy.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.sketchy.game.Views.LoginView;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SketchyGame extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    private Socket socket;

    @Override
    public void create() {
        font = new BitmapFont(); // Use default Arial font
        batch = new SpriteBatch();
        this.setScreen(new LoginView(this));
        connectSocket();
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

    public void connectSocket() {
        try {
            socket = IO.socket("http://localhost:8080");
            socket.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
