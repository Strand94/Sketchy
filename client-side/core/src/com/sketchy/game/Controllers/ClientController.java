package com.sketchy.game.Controllers;

import com.sketchy.game.Models.Player;
import com.sketchy.game.SketchyGame;
import com.sketchy.game.Views.LobbyView;
import com.sketchy.game.Views.View;
import com.sketchy.game.Models.Sheet;
import com.sketchy.game.communicator.Communicator;

import java.util.List;

public class ClientController {

    private SketchyGame game;
    private Player player;
    private Communicator communicator;
    private View view;


    public ClientController(SketchyGame game) {
        this.game = game;
        this.communicator = new Communicator(this);
    }

    public void startGame() {

    }

    public void endGame() {

    }

    public void goToLobby(int lobbyId){

    }

    public void updateView() {

    }

    public void updateLobby(List<String> names){
        if (view instanceof LobbyView){
            ((LobbyView) view).updatePlayerList(names);
        }

    }

    public void ping(){

    }

    public void beginRound(Sheet sheet){

    }

    public void getAnswer(){

    }

    public void setView(View view){
        game.setScreen(view);
        this.view.dispose();
        this.view = view;
        System.out.println("SetView:" + view);

    }

    public SketchyGame getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }

    public Communicator getCommunicator() {
        return communicator;
    }

    public View getView() {
        return view;
    }
}
