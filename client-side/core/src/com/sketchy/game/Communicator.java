package com.sketchy.game;

import com.sketchy.game.Models.Player;

import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

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

    public void test() {
        // test-funksjon som blir kalt når desktopLauncher kjøres

        // ta imot et json object fra eventet "socketID", som blir sendt fra server ved connection
        socket.on("socketID", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject obj = (JSONObject)args[0];
                System.out.println(obj);
            }
        });

        socket.on("ping", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    System.out.println("ping");
                    JSONObject obj = new JSONObject();
                    obj.put("id", socket.id());
                    socket.emit("pingOK", obj);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
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
