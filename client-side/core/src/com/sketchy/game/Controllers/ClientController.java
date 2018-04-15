package com.sketchy.game.Controllers;

import com.sketchy.game.Models.Player;
import com.sketchy.game.SketchyGame;
import com.sketchy.game.Views.LobbyView;
import com.sketchy.game.Views.LoginView;
import com.sketchy.game.Views.View;
import com.sketchy.game.Models.Should_this_even_be_here_qm.Sheet;

import java.util.List;

public class ClientController {

    SketchyGame game;
    Player player;
    public View view;

    public ClientController(SketchyGame game) {
        this.game = game;
    }

    public void goToLobby(int lobbyId){

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
        System.out.println("SetView:" + game.clientController.view);

    }

}
