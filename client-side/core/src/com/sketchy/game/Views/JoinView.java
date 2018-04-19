package com.sketchy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.sketchy.game.Config;
import com.sketchy.game.SketchyGame;

public class JoinView extends View {
    final SketchyGame game;

    TextField lobbyField;

    Skin skin;


    public JoinView(final SketchyGame game) {
        this.game = game;

        // Header
        Label header = new Label("Insert LobbyID:", uiSkin);
        header.setColor(Color.RED);


        // TextFields
        lobbyField = new TextField("", uiSkin);
        lobbyField.setColor(Color.CORAL);

        // Buttons
        TextButton join = new TextButton("Join Lobby", uiSkin);

        // Add elements to table
        table.add(header).padBottom(45);
        ;
        table.row();
        table.add(lobbyField).width(300).padBottom(25);
        ;
        table.row();
        table.add(join).width(250).padBottom(25);

        table.debug();

        // Listeners
        join.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onJoin();
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(68.0f / 256, 180.0f / 256, 112.0f / 256, 1);
        Gdx.input.setCatchBackKey(true);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            game.setScreen(new LoginView(game));
        }


    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    private Boolean validLobbyId(String input) {
        try {
            int number = Integer.parseInt(input);
            return number > 0 && number < Config.LOBBIES_CAPACITY;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void onJoin() {
        System.out.println("Join Game");
        if (validLobbyId(lobbyField.getText())) {
            game.getClientController().joinLobby(Integer.parseInt(lobbyField.getText()), game.getClientController().getPlayer().getName());
        } else {
            //TODO: Add alert box to notify invalid lobby id
            System.out.println("Invalid LobbyID");
        }
    }

}