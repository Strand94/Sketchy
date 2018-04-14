package com.sketchy.game.communicator;

import com.sketchy.game.Config;
import com.sketchy.game.Controllers.ClientController;

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
    private ClientController clientController;

    public Communicator() {
        clientController = new ClientController();
        connect();
        populateServerEventMap();
    }

    private void connect() {
        try {
            socket = IO.socket(Config.SERVER_ADDRESS);
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    // Receive events from server:

    private void populateServerEventMap() {
        serverEventMap = new HashMap<>();
        serverEventMap.put(START_GAME, new Listener() {
            @Override
            public void call(Object... args) {
                onStartGame();
            }
        });
        serverEventMap.put(END_GAME, new Listener() {
            @Override
            public void call(Object... args) {
                onEndGame();
            }
        });
        serverEventMap.put(JOIN_LOBBY, new Listener() {
            @Override
            public void call(Object... args) {
                onJoinLobby();
            }
        });
        serverEventMap.put(CREATE_LOBBY, new Listener() {
            @Override
            public void call(Object... args) {
                onCreateLobby();
            }
        });
        serverEventMap.put(UPDATE_VIEW, new Listener() {
            @Override
            public void call(Object... args) {
                onUpdateView();
            }
        });
        serverEventMap.put(UPDATE_LOBBY, new Listener() {
            @Override
            public void call(Object... args) {
                onUpdateLobby();
            }
        });
        serverEventMap.put(BEGIN_ROUND, new Listener() {
            @Override
            public void call(Object... args) {
                onBeginRound();
            }
        });
        serverEventMap.put(GET_ANSWER, new Listener() {
            @Override
            public void call(Object... args) {
                onGetAnswer();
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

    private void onStartGame() {
        clientController.startGame();
    }

    private void onEndGame() {
        clientController.endGame();
    }

    private void onUpdateView() {
        clientController.updateLobby();
    }

    private void onUpdateLobby() {
        clientController.updateLobby();
    }

    private void onBeginRound() {
        clientController.beginRound();
    }

    private void onGetAnswer() {

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

    private void onSocketId(JSONObject... obj) {
        System.out.println(obj);
    }

    // Send events to server:

    static private class Emit {
        private final String event;
        private final JSONObject obj;
        private Socket socket;
        private Emit(String eventName) {
            this.event = eventName;
            obj = new JSONObject();
        }
        static Emit event(Event event) {
            return new Emit(event.toString());
        }
        Emit to(Socket socket) {
            this.socket = socket;
            return Emit.this;
        }
        Emit with(String name, int value) throws JSONException {
            obj.put(name, value);
            return Emit.this;
        }
        Emit with(String name, String value) throws JSONException {
            obj.put(name, value);
            return Emit.this;
        }
        void send() {
            socket.emit(event, obj);
        }
    }

    public void endGame(int lobbyId) {
        try {
            Emit
                    .event(END_GAME)
                    .to(socket)
                    .with("lobbyId", lobbyId)
                    .send();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void startGame(int lobbyId) {
        try {
            Emit
                    .event(START_GAME)
                    .to(socket)
                    .with("lobbyId", lobbyId)
                    .send();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void joinLobby(int lobbyId, String playerName){
        try {
            Emit
                    .event(JOIN_LOBBY)
                    .to(socket)
                    .with("lobbyId", lobbyId)
                    .with("playerName", playerName)
                    .send();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void createLobby(String playerName) {
        try {
            Emit
                    .event(CREATE_LOBBY)
                    .to(socket)
                    .with("playerName", playerName)
                    .send();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
