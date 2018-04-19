package com.sketchy.game.Controllers;

import com.sketchy.game.Models.Notepad;
import com.sketchy.game.Models.Player;
import com.sketchy.game.Models.Sheet;
import com.sketchy.game.SketchyGame;
import com.sketchy.game.Views.DrawView;
import com.sketchy.game.Views.LobbyView;
import com.sketchy.game.Views.View;
import com.sketchy.game.communicator.Communicator;

import java.util.ArrayList;
import java.util.List;

public class ClientController {

    private Player player;
    private View view;
    private int lobbyId = 1337; // TODO: Get from communicator
    private Communicator communicator;
    private SketchyGame game;
    private int playerCount = 0;

    public ClientController(SketchyGame game) {
        this.game = game;
        this.communicator = new Communicator(this);
    }

    //=========== GAME ==============\\
    public int getPlayerCount() {
        System.out.println("getPlayerCount() -> " + playerCount);
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
        System.out.println("setPlayerCount() -> " + this.playerCount);
    }

    public void startGame() {
        System.out.println("clientController.startGame() -> ");
        //TODO: Check if it's okay to change view
        game.getClientController().setView(new DrawView(game));
    }

    public void endGame() {
    }

    public void updateView() {
    }

    public void beginRound(Sheet sheet) {
    }

    public void beginRound(Notepad notepad) {
    }

    public SketchyGame getGame() {
        return game;
    }


    //=========== END GAME ==============\\


    //=========== LOBBY ==============\\

    public void createLobby(String playerName) {
        System.out.println(String.format("clientController.createLobby('%s')", playerName));
        communicator.createLobby(playerName);

        //Todo: Check if it's OK to change view
        game.getClientController().setView(new LobbyView(game, this.lobbyId));
    }

    public void joinLobby(int lobbyId, String playerName) {
        System.out.println("clientController.joinLobby() -> " + lobbyId + playerName);
        communicator.joinLobby(lobbyId, playerName);

        //Todo: Check if it's OK to change view
        game.setScreen(new LobbyView(game, this.lobbyId));
    }

    public void updateLobby(int lobbyId, List<Player> players) {
        // TODO: adjust to Players
        System.out.println("clientController.updateLobby() -> " + players.size());
        setPlayerCount(players.size());

        List<String> names = new ArrayList<>();
        for (Player player : players) {
            names.add(player.getName());
        }

        if (view instanceof LobbyView) {
            ((LobbyView) view).updatePlayerList(names);
        }

    }
    //=========== END LOBBY ==============\\


    //=========== PLAYER ==============\\
    public Player getPlayer() {
        System.out.println("clientController.getPlayer() -> " + player.getName());
        return player;
    }

    public void setPlayer(Player player) {
        System.out.println(String.format("clientController.setPlayer('%s')", player.getName()));
        this.player = player;
    }
    //=========== END PLAYER ==============\\


    //=========== VIEW ==============\\
    public View getView() {
        return view;
    }

    public void setView(View view) {
        game.setScreen(view);

        if (this.view != null) {
            this.view.dispose();
        }
        this.view = view;

        System.out.println("*SetView:" + view);
    }
    //=========== END VIEW ==============\\


    public Communicator getCommunicator() {
        return communicator;
    }

}
