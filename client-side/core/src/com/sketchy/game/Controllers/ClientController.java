package com.sketchy.game.Controllers;

import com.sketchy.game.Models.Notepad;
import com.sketchy.game.Models.Player;
import com.sketchy.game.SketchyGame;
import com.sketchy.game.Views.DrawView;
import com.sketchy.game.Views.JoinView;
import com.sketchy.game.Views.LobbyView;
import com.sketchy.game.Views.View;
import com.sketchy.game.Models.Sheet;
import com.sketchy.game.communicator.Communicator;

import java.util.List;

public class ClientController {

    private Player player;
    private View view;
    private int lobbyId = 1337; // TODO: Get from communicator
    private Communicator communicator;
    private SketchyGame game;
    private int playerCount = 0;
    public int MAX_PLAYERS = 8;

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

    public void endGame() {}

    public void updateView() {}

    public void beginRound(Sheet sheet){}

    public void beginRound(Notepad notepad){}

    public SketchyGame getGame() {
        return game;
    }


    //=========== END GAME ==============\\



    //=========== LOBBY ==============\\

    public void CreateLobby(String playername){
        System.out.println(String.format("clientController.CreateLobby('%s')", playername));
        communicator.createLobby(playername);

        //Todo: Check if it's OK to change view
        game.getClientController().setView(new LobbyView(game, this.lobbyId));
    }

    public void JoinLobby(int lobbyId, String playername){
        System.out.println("clientController.JoinLobby() -> " + lobbyId + playername);
        communicator.joinLobby(lobbyId, playername);

        //Todo: Check if it's OK to change view
        game.setScreen(new LobbyView(game, this.lobbyId));
    }

    public void updateLobby(List<String> names){
        System.out.println("clientController.updateLobby() -> " + names.size());
        setPlayerCount(names.size());

        if (view instanceof LobbyView){
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

    public void setView(View view){
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
