package com.sketchy.game.Controllers;

import com.sketchy.game.Models.Lobby;
import com.sketchy.game.Models.Notepad;
import com.sketchy.game.Models.Sheet;
import com.sketchy.game.SketchyGame;
import com.sketchy.game.Views.LobbyView;
import com.sketchy.game.Views.RewindView;
import com.sketchy.game.Views.SheetView;
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
    private Notepad notepad;

    private boolean isLobbyMaster;

    // Used by rewind
    private List<Notepad> filledNotepads;
    private int sheetIndex, notepadIndex, stepIndex;
    private List<Sheet> sheets;

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
        System.out.format("getPlayerCount() -> %d\n", lobby.playerCount);
        return lobby.playerCount;
    }

    public void setPlayerCount(int playerCount) {
        lobby.playerCount = playerCount;
        System.out.format("setPlayerCount(%d)\n", playerCount);
    }

    public void startGame() {
        System.out.println("clientController.startGame()");
        showLoading();
        communicator.startGame(lobby.lobbyId);
    }

    public void endGame() {
    }

    public void beginRound(Notepad notepad) throws Exception {
        System.out.println("clientController.beginRound(<notepad>)");
        this.notepad = notepad;
        Sheet sheet = notepad.getLastSheet();
        if (sheet.nextTaskIsDraw()) showDrawView();
        else showGuessView();
        SheetView view = (SheetView) getView();
        view.setSheet(sheet);
    }

    public void submit(Sheet sheet) {
        showWaiting();
        notepad.setLastSheet(sheet);
        communicator.sendAnswer(lobby.lobbyId, notepad);
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

        setLobbyMaster(true);
        ViewManager.getInstance().lobbyView.setLobbyMaster();
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
        System.out.format("clientController.getPlayerName() -> %s\n", playerName);
        return playerName;
    }

    public void setPlayer(String playerName) {
        System.out.println(String.format("clientController.setPlayerName('%s')\n", playerName));
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

    public void notifyPlayer(String message) {
        viewStack.peek().showToast(message);
    }

    //=========== REWIND ================\\

    public void startRewind(List<Notepad> notepads) {

        filledNotepads = notepads;
        notepadIndex = 0;
        sheetIndex = 0;
        stepIndex = 0;
        sheets = filledNotepads.get(notepadIndex).getSheets();

        showRewind();
        rewindShowNext();

        if(isLobbyMaster()){
            if (viewStack.peek() instanceof RewindView) {
                RewindView rewindView = (RewindView) viewStack.peek();
                rewindView.setLobbyMaster();
            }
        }
    }

    public void rewindShowNext(){
        if (viewStack.peek() instanceof RewindView) {
            RewindView rewindView = (RewindView) viewStack.peek();

            if (!(sheetIndex < sheets.size()*2+1)){
                System.out.println("No more sheets!");
                if (++notepadIndex < filledNotepads.size()) {
                    System.out.println("New notepad and new sheet");
                    sheets = filledNotepads.get(notepadIndex).getSheets();
                    sheetIndex = 0;
                    stepIndex = 0;
                } else {
                    if (isLobbyMaster) {
                        communicator.rewindFinished(lobby.lobbyId);
                        System.out.println("Sent REWIND_FINISHED event");
                    }
                    return;
                }
            }

            if (stepIndex++ == 0) {
                rewindView.showRewindStep(sheets.get(sheetIndex), true, true);
            } else if (stepIndex++ % 2 == 1) {
                rewindView.showRewindStep(sheets.get(sheetIndex), false, false);
            } else if (stepIndex++ % 2 == 0) {
                rewindView.showRewindStep(sheets.get(sheetIndex), true, false);
                sheetIndex++;
            } else {
                System.out.println("Something wrong");
            }
        }
    }

    public void rewindFinished() {
        showLobby();
    }

    public void requestNextRewindStep() {
        communicator.rewindShowNext(lobby.lobbyId);
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


    public boolean isLobbyMaster() {
        return isLobbyMaster;
    }

    public void setLobbyMaster(boolean lobbyMaster) {
        isLobbyMaster = lobbyMaster;
    }
}
