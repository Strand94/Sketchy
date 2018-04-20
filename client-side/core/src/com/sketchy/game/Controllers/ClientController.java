package com.sketchy.game.Controllers;

import com.sketchy.game.Models.Dot;
import com.sketchy.game.Models.Lobby;

import com.sketchy.game.Models.Notepad;
import com.sketchy.game.Models.Sheet;
import com.sketchy.game.SketchyGame;
import com.sketchy.game.Views.DrawView;
import com.sketchy.game.Views.GuessView;
import com.sketchy.game.Views.JoinView;
import com.sketchy.game.Views.LoadingView;
import com.sketchy.game.Views.LobbyView;
import com.sketchy.game.Views.LoginView;
import com.sketchy.game.Views.RewindView;
import com.sketchy.game.Views.View;
import com.sketchy.game.communicator.Communicator;

import java.util.Collections;
import java.util.LinkedList;

import java.util.List;
import java.util.Stack;

public class ClientController {

    private final SketchyGame game;
    private final Communicator communicator;
    private final Stack<View> viewStack;
    private final List<View> forDisposal;

    private View nextView;
    private Lobby lobby;
    private String playerName;


    public ClientController(SketchyGame game) {
        this.game = game;
        this.communicator = new Communicator(this);
        this.viewStack = new Stack<>();
        this.forDisposal = Collections.synchronizedList(new LinkedList<View>());

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
        //TODO: Check if it's okay to change view
        showDraw(true);
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
        lobby = Lobby.LOADING;

        loadLobby();
    }

    public void joinLobby(int lobbyId, String playerName) {
        System.out.format("clientController.joinLobby(%s, '%s')\n", lobbyId, playerName);
        communicator.joinLobby(lobbyId, playerName);
        lobby = Lobby.LOADING;

        loadLobby();
    }

    public void updateLobby(int lobbyId, List<String> names) throws Exception {
            if (lobby == Lobby.LOADING) lobby = new Lobby(lobbyId);
            else if (lobbyId != lobby.lobbyId)
                throw new Exception("Server and client disagree about lobby id");

        System.out.format("clientController.updateLobby(%s, players(%d))\n", lobbyId, names.size());
        setPlayerCount(names.size());

        if (nextView instanceof LobbyView) {
            setView(nextView, true);
        }

        if (viewStack.peek() instanceof LobbyView) {
            LobbyView view = (LobbyView) viewStack.peek();
            view.updatePlayerList(names);
            view.setLobbyId(lobbyId);
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
    private void setView(View view, boolean replaceCurrent) {
        if (!viewStack.empty()) {
            if (replaceCurrent) {
                synchronized (forDisposal) {
                    forDisposal.add(viewStack.pop());
                }
            } else {
                viewStack.peek().pause();
            }
        }
        this.viewStack.push(view);
        this.nextView = null;

        game.setScreen(view);
        System.out.println("*setView:" + view);
    }

    public void goBack() {
        if (viewStack.size() <= 1) return;
        View currentView = viewStack.pop();
        if (currentView != null) {
            synchronized (forDisposal) {
                forDisposal.add(currentView);
            }
        }
        this.nextView = null;

        game.setScreen(viewStack.peek());
        System.out.println("*goBack:" + viewStack.peek());
    }

    public void disposeViews() {
        synchronized (forDisposal) {
            for (View view : forDisposal) view.dispose();
            forDisposal.clear();
        }
    }
    //=========== END VIEW ==============\\

    //=========== REWIND ================\\

    public void advanceRewind(){
        if (viewStack.peek() instanceof RewindView) {
            RewindView view = (RewindView) viewStack.peek();
            view.advance();
        }
    }

    public void requestRewind(){
        //todo: Send something to communicator
    }

    //=========== END REWIND ============\\

    public void showLogin() {
        setView(new LoginView(this), true);
    }

    public void showJoin() {
        setView(new JoinView(this), false);
    }

    public void loadLobby() {
        setView(new LoadingView(this), false);
        nextView = new LobbyView(this);
    }

    public void showRewind() {
        //Todo not everyone can be lobby master
        setView(new RewindView(this, true), true);
    }

    public void showGuess(Stack<Dot> drawing) {
        setView(new GuessView(this, drawing), true);
    }

    public void showDraw(boolean first) {
        setView(new DrawView(this), !first);
    }
}
