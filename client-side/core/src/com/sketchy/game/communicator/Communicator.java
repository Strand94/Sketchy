package com.sketchy.game.communicator;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import com.sketchy.game.Config;
import com.sketchy.game.Controllers.ClientController;
import com.sketchy.game.Models.Notepad;
import com.sketchy.game.Models.Player;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter.Listener;

import static com.sketchy.game.communicator.Event.*;
import static com.sketchy.game.communicator.JSONTranslator.*;

public class Communicator {
    private Socket socket;
    private ClientController clientController;

    public Communicator(ClientController clientController) {
        connect(false);
        setServerEvents();
        this.clientController = clientController;

    }

    public Communicator(ClientController clientController, boolean local) {
        connect(local);
        setServerEvents();
        this.clientController = clientController;
    }

    private void connect(boolean local) {
        if (local) {
            try {
                socket = IO.socket(Config.LOCAL_PORT);  // TODO: broken for some reason
                socket.connect();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            try {
                socket = IO.socket(Config.SERVER_ADDRESS);
                socket.connect();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    // Receive events from server:

    private void setServerEvents() {
        socket.on(START_GAME.toString(), new Listener() {
            @Override
            public void call(Object... args) {
                onStartGame();
            }
        });
        socket.on(END_GAME.toString(), new Listener() {
            @Override
            public void call(Object... args) {
                onEndGame();
            }
        });
        socket.on(UPDATE_VIEW.toString(), new Listener() {
            @Override
            public void call(Object... args) {
                onUpdateView();
            }
        });
        socket.on(UPDATE_LOBBY.toString(), new Listener() {
            @Override
            public void call(Object... args) {
                onUpdateLobby((int) args[0], jsonToPlayerList(args[1]));

            }
        });
        socket.on(BEGIN_ROUND.toString(), new Listener() {
            @Override
            public void call(Object... args) {
                Notepad notepad = (Notepad) args[0];
                onBeginRound(notepad);
            }
        });
        socket.on(GET_ANSWER.toString(), new Listener() {
            @Override
            public void call(Object... args) {
                onGetAnswer();
            }
        });
        socket.on(PING.toString(), new Listener() {
            @Override
            public void call(Object... args) {
                onPing();
            }
        });
        socket.on(SOCKET_ID.toString(), new Listener() {
            @Override
            public void call(Object... args) {
                onSocketId((JSONObject) args[0]);
            }
        });
    }

    private void onStartGame() {
        clientController.startGame();
    }

    private void onEndGame() {
        clientController.endGame();
    }

    private void onUpdateView() {
        clientController.updateView();
    }

    private void onUpdateLobby(int lobbyId, List<Player> members) {
        clientController.updateLobby(lobbyId, members);
    }

    private void onBeginRound(Notepad notepad) {
        clientController.beginRound(notepad);
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

    private List<Player> jsonToPlayerList(Object json)  {
        try {
            Type listType = new TypeToken<List<Player>>() {}.getType();
            Gson gson = new Gson();
            return gson.fromJson(json.toString(), listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String playerListToString(ArrayList<Player> players) {
        // TODO: test this
        try {
            Type listType = new TypeToken<List<Player>>() {}.getType();
            final List<Player> target = new LinkedList<>();
            target.addAll(players);

            Gson gson = new Gson();
            return gson.toJson(target, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
