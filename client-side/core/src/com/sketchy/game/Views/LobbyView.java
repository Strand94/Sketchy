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
import com.sketchy.game.SketchyGame;

import java.util.List;

public class LobbyView extends View {
    SketchyGame game;

    private TextButton startGame;
    private float remaining = 5;
    private boolean startGame_r = false;
    private Label numberOfPlayers;
    private Table buttonTable;
    private Table playerTable = new Table();

    public LobbyView(SketchyGame game, int lobbyID) {
        this.game = game;

        Label gameidLabel = new Label("LobbyID:" + " " + Integer.toString(lobbyID), uiSkin);
        gameidLabel.setColor(Color.CYAN);
        startGame = new TextButton("Start Game", uiSkin);
        numberOfPlayers = new Label(game.getClientController().getPlayerCount() + "/" +
                Integer.toString(Config.MAX_PLAYERS), uiSkin);

        table.add(gameidLabel);
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

    public void updatePlayerList(List<String> players) {
        table.removeActor(playerTable);
        playerTable = new Table();
        table.add(playerTable);

        System.out.print("Adding players: ");
        for (String player : players) {
            addPerson(player);
            System.out.print(player + ",");
        }
        System.out.println("");

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
            game.setScreen(new LoginView(game));
        }

        // Countdown. Todo: Move to startGameCounter
        if (remaining > 0.1 && startGame_r == true) {
            float deltaTime = Gdx.graphics.getDeltaTime();
            remaining -= deltaTime;
            startGame.setText(String.format("Start in %.0fs", remaining));
        }
    }

    private void onGameStart() {
        game.getClientController().startGame();
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
