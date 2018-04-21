package com.sketchy.game.Views;

import com.sketchy.game.Controllers.ClientController;

public class ViewManager {
    public static class AlreadyInstantiated extends RuntimeException {
        private AlreadyInstantiated(String message) {
            super(message);
        }
    }

    private static ViewManager instance;

    public final LoginView loginView;
    public final JoinView joinView;
    public final LoadingView loadingView;
    public final LobbyView lobbyView;
    public final WaitingView waitingView;
    public final DrawView drawView;
    public final GuessView guessView;
    public final RewindView rewindView;

    public static void init(ClientController controller) {
        if (instance != null) {
            throw new AlreadyInstantiated("ViewManager singleton already initiated");
        }
        instance = new ViewManager(controller);
    }

    public static ViewManager getInstance() {
        return instance;
    }

    private ViewManager(ClientController controller) {
        loginView = new LoginView(controller);
        joinView = new JoinView(controller);
        loadingView = new LoadingView(controller);
        lobbyView = new LobbyView(controller);
        lobbyView.setLobbyMaster();  //Todo not everyone can be lobby master
        waitingView = new WaitingView();
        drawView = new DrawView(controller);
        guessView = new GuessView(controller);
        rewindView = new RewindView(controller);
        rewindView.setLobbyMaster();  //Todo not everyone can be lobby master
    }
}
