package com.sketchy.game.Controllers;

import com.sketchy.game.Models.Notepad;
import com.sketchy.game.Models.Sheet;
import com.sketchy.game.SketchyGame;
import com.sketchy.game.Views.DrawView;
import com.sketchy.game.Views.GuessView;
import com.sketchy.game.Views.JoinView;
import com.sketchy.game.Views.LobbyView;
import com.sketchy.game.Views.LoginView;
import com.sketchy.game.Views.View;
import com.sketchy.game.communicator.Communicator;

import java.util.List;
import java.util.Stack;

public class ClientController {

    private SketchyGame game;
    private Communicator communicator;
    private View view;
    private String playerName;
    private int lobbyId;
    private int playerCount = 0;

    public ClientController(SketchyGame game) {
        this.game = game;
        this.communicator = new Communicator(this);

        showLogin();
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
        showDraw();
    }

    public void endGame() {
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
        System.out.format("clientController.createLobby('%s')\n", playerName);
        communicator.createLobby(playerName);

        //Todo: Check if it's OK to change view
        showLobby();
    }

    public void joinLobby(int lobbyId, String playerName) {
        System.out.format("clientController.joinLobby(%s, '%s')\n", lobbyId, playerName);
        communicator.joinLobby(lobbyId, playerName);

        //Todo: Check if it's OK to change view
        showLobby();
    }

    public void updateLobby(int lobbyId, List<String> names) {
        this.lobbyId = lobbyId;

        System.out.format("clientController.updateLobby(%s, players(%d))\n", lobbyId, names.size());
        setPlayerCount(names.size());

        if (view instanceof LobbyView) {
            ((LobbyView) view).updatePlayerList(names);
            ((LobbyView) view).setLobbyId(lobbyId);
        }

    }
    //=========== END LOBBY ==============\\


    //=========== PLAYER ==============\\
    public String getPlayerName() {
        System.out.println("clientController.getPlayerName() -> " + playerName);
        return playerName;
    }

    public void setPlayer(String playerName) {
        System.out.println(String.format("clientController.setPlayerName('%s')", playerName));
        this.playerName = playerName;
    }
    //=========== END PLAYER ==============\\


    //=========== VIEW ==============\\
    private void setView(View view) {
        game.setScreen(view);

        if (this.view != null) {
            this.view.dispose();
        }
        this.view = view;

        System.out.println("*SetView:" + view);
    }
    //=========== END VIEW ==============\\

    public void showLogin() {
        setView(new LoginView(this));
    }

    public void showJoin() {
        setView(new JoinView(this));
    }

    public void showLobby() {
        setView(new LobbyView(this));
    }

    public void showGuess(Stack<DrawView.Dots> drawing) {
        setView(new GuessView(this, drawing));
    }

    private void showDraw() {
        setView(new DrawView(this));
    }
}
