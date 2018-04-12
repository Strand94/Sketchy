package com.sketchy.game.communicator;

import com.sketchy.game.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.HashMap;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter.Listener;

import static com.sketchy.game.communicator.Event.*;

public class Communicator {
    private Socket socket;
    private HashMap<Event, Listener> serverEventMap;

    public Communicator() {
        connect();
        populateServerEventMap();
    }

    public void test() {
        startListening(PING, SOCKET_ID);
    }

    private void connect() {
        try {
            socket = IO.socket(Config.SERVER_ADDRESS);
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void populateServerEventMap() {
        serverEventMap = new HashMap<>();
        serverEventMap.put(START_GAME, new Listener() {
            @Override
            public void call(Object... args) {
                onStartGame();
            }
        });
        serverEventMap.put(PING, new Listener() {
            @Override
            public void call(Object... args) {
                onPing();
            }
        });
        serverEventMap.put(SOCKET_ID, new Listener() {
            @Override
            public void call(Object... args) {
                onSocketId((JSONObject) args[0]);
            }
        });
    }

    private void startListening(Event... events) {
        for (Event event : events) socket.on(event.toString(), serverEventMap.get(event));
    }

    private void stopListening(Event... events) {
        for (Event event : events) socket.off(event.toString());
    }

    private void onPing() {
        try {
            System.out.println("ping");
            JSONObject obj = new JSONObject();
            obj.put("id", socket.id());
            socket.emit("pingOK", obj);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void onStartGame() {

    }

    private void onSocketId(JSONObject... obj) {
        System.out.println(obj);
    }

    public void endGame() {

    }

    public void startGame(int lobbyId) {

    }

    public void joinLobby(int lobbyId, String playerName){
        JSONObject obj = new JSONObject();
        try {
            obj.put("lobbyId", lobbyId);
            obj.put("playerName", playerName);
            socket.emit("join-lobby", obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void createLobby() {

    }

    protected void emit(Event event) {
        socket.emit(event.toString());
    }

}
