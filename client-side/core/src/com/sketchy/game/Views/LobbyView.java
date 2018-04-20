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
    private ClientController controller;

    private TextButton startGame;
    private Label lobbyIdLabel, numberOfPlayers;
    private Table buttonTable, playerTable;

    public LobbyView(ClientController controller) {
        this.controller = controller;

        // Tables
        playerTable = new Table();
        buttonTable = new Table();
        stage.addActor(buttonTable);
        
        // Header
        lobbyIdLabel = new Label("LobbyID: \u2014", uiSkin);
        lobbyIdLabel.setColor(Color.CYAN);

        // Buttons
        startGame = new TextButton("Start Game", uiSkin);

        // Labels
        numberOfPlayers = new Label(controller.getPlayerCount() + "/" +
                                    Integer.toString(Config.MAX_PLAYERS), uiSkin);

        // Add to table
        table.add(lobbyIdLabel);
        table.row();
        table.setFillParent(true);
        table.add(playerTable);

        buttonTable.setPosition(screenWidth / 2, screenHeight * 0.1f);
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


    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(246.0f / 256, 195.0f / 256, 42.0f / 256, 1);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            controller.goBack();
        }

    }

    private void onGameStart() {
        controller.startGame();
    }
}
