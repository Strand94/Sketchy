package com.sketchy.game.Controllers;

import com.sketchy.game.Models.Lobby;
import com.sketchy.game.Models.Notepad;
import com.sketchy.game.Models.Sheet;
import com.sketchy.game.SketchyGame;
import com.sketchy.game.Views.LobbyView;
import com.sketchy.game.Views.RewindView;
import com.sketchy.game.Views.View;
import com.sketchy.game.Views.ViewManager;
import com.sketchy.game.communicator.Communicator;

import java.util.ArrayDeque;
import java.util.List;

public class ClientController {

    private final SketchyGame game;
    private final Communicator communicator;
    private final ArrayDeque<View> viewStack;
    private final ViewManager viewManager;

    private Lobby lobby;
    private String playerName;


    public ClientController(SketchyGame game) {
        this.game = game;
        this.communicator = new Communicator(this);
        this.viewStack = new ArrayDeque<>();
        this.lobby = Lobby.LOADING;

        ViewManager.init(this);
        this.viewManager = ViewManager.getInstance();

        showLogin();
    }

    //=========== GAME ==============\\
    public int getPlayerCount() {
        System.out.println("getPlayerCount() -> " + lobby.playerCount);
        return lobby.playerCount;
    }

    public void setPlayerCount(int playerCount) {
        lobby.playerCount = playerCount;
        System.out.println("setPlayerCount(%d)" + playerCount);
    }

    public void startGame() {
        System.out.println("clientController.startGame()");
        showLoading();
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
        showLoading();
        lobby = Lobby.LOADING;
        communicator.createLobby(playerName);
    }

    public void joinLobby(int lobbyId, String playerName) {
        System.out.format("clientController.joinLobby(%s, '%s')\n", lobbyId, playerName);
        showLoading();
        lobby = Lobby.LOADING;
        communicator.joinLobby(lobbyId, playerName);
    }

    public void updateLobby(int lobbyId, List<String> names) throws Exception {
        System.out.format("clientController.updateLobby(%s, players(%d))\n", lobbyId, names.size());
        if (!(getView() instanceof LobbyView)) showLobby();
        LobbyView view = (LobbyView) getView();

        if (lobby == Lobby.LOADING) lobby = new Lobby(lobbyId);
        else if (lobbyId != lobby.lobbyId) {
            throw new Exception("Server and client disagree about lobby id");
        }

        setPlayerCount(names.size());

        view.updatePlayerList(names);
        view.setLobbyId(lobbyId);
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
    private View getView() {
        return viewStack.peek();
    }

    private void setView(View view, boolean replaceCurrent) {
        if (viewStack.size() > 0) {
            viewStack.peek().pause();
            if (replaceCurrent) viewStack.pop().reset();
        }
        this.viewStack.push(view);
        game.setScreen(view);
        view.resume();
        System.out.println("*setView:" + view);
    }

    public void goBack() {
        if (viewStack.size() <= 1) return;
        View from = viewStack.pop();
        from.pause();
        from.reset();
        View to = viewStack.peek();
        to.resume();
        game.setScreen(to);
        System.out.println("*goBack:" + to);
    }

    //=========== REWIND ================\\

    public void rewindShowNext(){
        if (viewStack.peek() instanceof RewindView) {
            RewindView rewindView = (RewindView) viewStack.peek();
            rewindView.showNext();
        }
    }

    public void requestRewind(){
        //todo: Send something to communicator
    }

    //=========== END REWIND ============\\

    public void showLogin() {
        setView(viewManager.loginView, true);
    }

    public void showJoin() {
        setView(viewManager.joinView, false);
    }

    public void showLoading() {
        setView(viewManager.loadingView, false);
    }

    public void showRewind() {
        setView(viewManager.rewindView, true);
    }

    public void showWaiting() {
        setView(viewManager.waitingView, true);
    }

    public void showLobby() {
        setView(viewManager.lobbyView, false);
    }

    public void showGuessView() {
        setView(viewManager.guessView, true);
    }

    public void showDrawView() {
        setView(viewManager.drawView, true);
    }
    //=========== END VIEW ==============\\
}
