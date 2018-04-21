package com.sketchy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;
import com.sketchy.game.Config;
import com.sketchy.game.Controllers.ClientController;

import java.util.List;
import java.util.Locale;

public class LobbyView extends View {
    private static final String INITIAL_LOBBY_ID_LABEL_TEXT = "LobbyID: \u2014";

    private ClientController controller;

    private TextButton startGame;
    private Label lobbyIdLabel, numberOfPlayers;
    private Table buttonTable, playerTable;

    private Timer.Task timer;
    private int counter = Config.START_GAME_TIMER;

    LobbyView(ClientController controller) {
        this.controller = controller;

        // Tables
        playerTable = new Table();
        buttonTable = new Table();
        stage.addActor(buttonTable);

        // Header
        lobbyIdLabel = new Label(INITIAL_LOBBY_ID_LABEL_TEXT, uiSkin);
        lobbyIdLabel.setColor(Color.CYAN);

        // Buttons
        startGame = new TextButton("Start Game", uiSkin);

        // Labels
        numberOfPlayers = new Label(getNumberOfPlayersText(), uiSkin);

        // Add to table
        table.add(lobbyIdLabel).top().padTop(0.07f * getScreenHeight());
        table.row();
        table.setFillParent(true);

        buttonTable.setPosition(getScreenWidth() / 2, getScreenHeight() * 0.1f);
        buttonTable.add(startGame);
        buttonTable.row();
        buttonTable.add(numberOfPlayers);

        table.add(playerTable).expandY().top().padTop(0.1f * getScreenHeight());

        // Listeners
        startGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onGameStart();
            }
        });

        // Timer
        timer = new Timer.Task(){
            @Override
            public void run(){
                countDown();
            }
        };
    }

    public void setLobbyId(int lobbyId) {
        lobbyIdLabel.setText(String.format(Locale.GERMAN, "LobbyID: %05d", lobbyId));
    }

    public void updatePlayerList(List<String> players) {
        playerTable.reset();

        System.out.print("Adding players: ");
        boolean first = true;
        for (String player : players) {
            addPerson(player);
            if (first) first = false;
            else System.out.print(", ");
            System.out.print(player);
        }
        System.out.println();

        numberOfPlayers.setText(players.size() + "/" + Config.MAX_PLAYERS);

    }

    private void addPerson(String name) {
        Label playerName = new Label(name, uiSkin);
        playerName.setColor(Color.CYAN);
        playerTable.add(playerName).colspan(2);
        playerTable.row();
    }

    private String getNumberOfPlayersText() {
        return controller.getPlayerCount() + "/" + Integer.toString(Config.MAX_PLAYERS);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(246.0f / 256, 195.0f / 256, 42.0f / 256, 1);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            controller.goBack();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            startTimer();
        }
    }

    @Override
    public void reset() {
        playerTable.reset();
        lobbyIdLabel.setText(INITIAL_LOBBY_ID_LABEL_TEXT);
        numberOfPlayers.setText(getNumberOfPlayersText());
        timer.cancel();
        counter = Config.START_GAME_TIMER;
    }

    private void onGameStart() {
        controller.startGame();
    }

    private void countDown(){
        startGame.setText(String.format(String.format(Locale.GERMAN, "   Starting in %ds   ", counter)));
        counter--;
    }

    public void startTimer(){
        Timer.schedule(timer, 0f, 1f, Config.START_GAME_TIMER);
    }
}
