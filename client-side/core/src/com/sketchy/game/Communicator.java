package com.sketchy.game;

import com.sketchy.game.Models.Player;

import io.socket.client.IO;
import io.socket.client.Socket;

import static com.sketchy.game.Config.SERVER_ADDRESS;

public class Communicator {

    private Socket socket;

    public void connect() {
        try {
            socket = IO.socket(SERVER_ADDRESS);
            socket.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void endGame(){

    }

    public void startGame(int lobbyId){

    }

    public void joinLobby(int lobbyId, Player player){

    }

    public void createLobby(int lobbyId, Player player){

    }

}
