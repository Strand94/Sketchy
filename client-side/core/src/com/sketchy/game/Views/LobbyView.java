package com.sketchy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.sketchy.game.Config;
import com.sketchy.game.Controllers.ClientController;

import java.util.List;
import java.util.Locale;

public class LobbyView extends View {
    ClientController controller;

    private TextButton startGame;
    private float remaining = 5;
    private boolean startGame_r = false;
    private Label lobbyIdLabel;
    private Label numberOfPlayers;
    private Table buttonTable;
    private Table playerTable = new Table();

    public LobbyView(ClientController controller) {
        this.controller = controller;

        lobbyIdLabel = new Label("LobbyID: \u2014", uiSkin);
        lobbyIdLabel.setColor(Color.CYAN);
        startGame = new TextButton("Start Game", uiSkin);
        numberOfPlayers = new Label(controller.getPlayerCount() + "/" +
                Integer.toString(Config.MAX_PLAYERS), uiSkin);

        table.add(lobbyIdLabel);
        table.row();
        buttonTable = new Table();
        buttonTable.setPosition(screenWidth / 2, screenHeight * 0.1f);

        table.setFillParent(true);
        stage.addActor(buttonTable);
        table.add(playerTable);
        buttonTable.add(startGame);
        buttonTable.row();
        buttonTable.add(numberOfPlayers);

        startGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onGameStart();
            }
        });
    }

    public void setLobbyId(int lobbyId) {
        lobbyIdLabel.setText(String.format(Locale.GERMAN, "LobbyID: %05d", lobbyId));
    }

    public void updatePlayerList(List<String> players) {
        table.removeActor(playerTable);
        playerTable = new Table();
        table.add(playerTable);

        System.out.print("Adding players: ");
        for (String player : players) {
            addPerson(player);
            System.out.print(player + ",");
        }
        System.out.println();

        numberOfPlayers.setText(players.size() + "/" + Config.MAX_PLAYERS);

    }

    private void addPerson(String name) {
        Label playerName = new Label(name, uiSkin);
        playerName.setColor(Color.CYAN);
        playerTable.add(playerName);
        playerTable.row();
    }

    private void startGameCounter() {
        startGame_r = true;
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(246.0f / 256, 195.0f / 256, 42.0f / 256, 1);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            controller.showLogin();
        }

        // Countdown. Todo: Move to startGameCounter
        if (remaining > 0.1 && startGame_r == true) {
            float deltaTime = Gdx.graphics.getDeltaTime();
            remaining -= deltaTime;
            startGame.setText(String.format("Start in %.0fs", remaining));
        }
    }

    private void onGameStart() {
        controller.startGame();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
