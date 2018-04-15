package com.sketchy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.sketchy.game.SketchyGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;


public class LobbyView extends View {
    final SketchyGame game;

    TextButton startGame;
    float remaining = 5;
    boolean startGame_r = false;
    int playerCount = 1;
    Label numberOfPlayers;
    Table buttonTable;
    Table player_table = new Table();


    List<String> newPlayers = new ArrayList<String>(); // for testing only

    public LobbyView(SketchyGame game) {
        this.game = game;

        String lobbyID = "1337"; // TODO: should be given by server
        Label gameidLabel = new Label("LobbyID:"+" "+lobbyID, uiSkin);
        startGame = new TextButton("Start Game", uiSkin);
        numberOfPlayers = new Label(playerCount+"/8", uiSkin);

        table.add(gameidLabel);
        table.row();
        buttonTable = new Table();
        buttonTable.setPosition(screenWidth/2, screenHeight*0.1f);

        table.setFillParent(true);
        stage.addActor(buttonTable);
        table.add(player_table);
        buttonTable.add(startGame);
        buttonTable.row();
        buttonTable.add(numberOfPlayers);

        //TODO remove, testing only
        newPlayers.add("some_dude");
        newPlayers.add("random");
        newPlayers.add("TDT4240slayer");
        newPlayers.add("Mumitrollet");
        newPlayers.add("ElephantDrawer");

        startGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Start game");
                startGameCounter();
            }
        });
    }

    public void updatePlayerList(List<String> players) {
        table.removeActor(player_table);
        player_table = new Table();
        table.add(player_table);

        System.out.print("Adding players: ");
        for (String player : players) {
            addPerson(player);
            System.out.print(player + ",");
        }
        System.out.println("");

        numberOfPlayers.setText(players.size() + "/" + game.MAX_PLAYERS);

    }

    private void addPerson(String name){
        Label playerName = new Label(name, uiSkin);
        player_table.add(playerName);
        player_table.row();
    }

    private void startGameCounter(){
        startGame_r = true;
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(246.0f/256, 195.0f/256, 42.0f/256, 1);


        if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
            newPlayers.add("foo");
            System.out.println("Player foo has joined the server, but client is not updated");
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.U)){
            System.out.println("Refresh player names");
            game.clientController.updateLobby(newPlayers);

        }

        // Countdown. Todo: Move to startGameCounter
        if (remaining > 0.1 && startGame_r == true) {
            float deltaTime = Gdx.graphics.getDeltaTime();
            remaining -= deltaTime;
            startGame.setText(String.format("Start in %.0fs", remaining));
            game.clientController.setView(new DrawView(game));
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
}
