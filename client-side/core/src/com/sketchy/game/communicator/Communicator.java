package com.sketchy.game.communicator;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.sketchy.game.Config;
import com.sketchy.game.Controllers.ClientController;
import com.sketchy.game.Models.Notepad;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static com.sketchy.game.communicator.Event.*;

public class Communicator {
    public abstract class Listener implements Emitter.Listener {
        private JSONObject params;

        @Override
        public void call(Object... args) {
            params = (JSONObject) args[0];
            try {
                call(params);
            } catch (JSONException e) {
                System.out.println(params);
                e.printStackTrace();
            }
        }

        protected void call(JSONObject params) throws JSONException {
        }
    }

    private Socket socket;
    private ClientController clientController;

    public Communicator(ClientController clientController) {
        connect();
        setServerEvents();
        this.clientController = clientController;

    }

    private void connect() {
        try {
            System.out.format("Connecting to server at [%s]\n", Config.SERVER_ADDRESS);
            socket = IO.socket(Config.SERVER_ADDRESS);
            socket.connect()
                    .on(Socket.EVENT_CONNECT, new Listener() {
                        @Override
                        public void call(Object... args) {
                            System.out.println("Connected");
                        }
                    })
                    .on(Socket.EVENT_CONNECT_TIMEOUT, new Listener() {
                        @Override
                        public void call(Object... args) {
                            System.out.println("Connection timed out");
                        }
                    })
                    .on(Socket.EVENT_CONNECT_ERROR, new Listener() {
                        @Override
                        public void call(Object... args) {
                            System.out.format("Connection error: %s\n", args[0].toString());
                        }
                    });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    // Receive events from server:

    private void setServerEvents() {
        socket.on(PING.toString(), new Listener() {
            @Override
            protected void call(JSONObject params) throws JSONException {
                onPing();
            }
        });
        socket.on(START_GAME.toString(), new Listener() {
            @Override
            protected void call(JSONObject params) throws JSONException {
                onStartGame();
            }
        });
        socket.on(END_GAME.toString(), new Listener() {
            @Override
            protected void call(JSONObject params) throws JSONException {
                onEndGame();
            }
        });
        socket.on(UPDATE_LOBBY.toString(), new Listener() {
            @Override
            protected void call(JSONObject params) throws JSONException {
                onUpdateLobby(
                        params.getInt("lobbyId"),
                        jsonToPlayerNames(params.getString("playerList"))
                );
            }
        });
        socket.on(START_REWIND.toString(), new Listener() {
            @Override
            public void call(JSONObject params) throws JSONException {
                onStartRewind(jsonToNotepadList(params.getString("notepadList")));
            }
        });
        socket.on(REWIND_SHOW_NEXT.toString(), new Listener() {
            @Override
            protected void call(JSONObject params) throws JSONException {
                onRewindShowNext();
            }
        });
        socket.on(REWIND_FINISHED.toString(), new Listener() {
            @Override
            protected void call(JSONObject params) throws JSONException {
                onRewindFinished();
            }
        });
        socket.on(BEGIN_ROUND.toString(), new Listener() {
            @Override
            public void call(JSONObject params) throws JSONException {
                onBeginRound(jsonToNotepad(params.getString("notepad")));
            }
        });

    }

    private void onStartGame() {
        clientController.startGame();
    }

    private void onEndGame() {
        clientController.endGame();
    }

    private void onUpdateLobby(int lobbyId, List<String> playerNames) {
        try {
            clientController.updateLobby(lobbyId, playerNames);
        } catch (Exception e) {
            e.printStackTrace(); //TODO: Error message
        }
    }

    private void onBeginRound(Notepad notepad) {
        try {
            clientController.beginRound(notepad);
        } catch (Exception e) {
            e.printStackTrace();// TODO: Error message
        }
    }

    private void onGetAnswer() {

    }

    private void onStartRewind(List<Notepad> notepadList) {
        clientController.showRewind();
    }

    private void onRewindShowNext() {
        clientController.rewindShowNext();
    }

    private void onRewindFinished() {
        // TODO: onRewindFinished
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

    public void joinLobby(int lobbyId, String playerName) {
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

    public void sendAnswer(int lobbyId, Notepad notepad) {
        try {
            Emit
                    .event(SEND_ANSWER)
                    .to(socket)
                    .with("lobbyId", lobbyId)
                    .with("notepad", notepadToJson(notepad))
                    .send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onPing() {
        try {
            System.out.println("ping");
            Emit
                    .event(PING_OK)
                    .to(socket)
                    .with("id", socket.id())
                    .send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Source: http://www.javadoc.io/doc/com.google.code.gson/gson/2.8.2

    private static List<String> jsonToPlayerNames(String json) throws JsonParseException {
        Type listType = new TypeToken<List<String>>() {}.getType();
        Gson gson = new Gson();
        return gson.fromJson(json, listType);
    }

    private static List<Notepad> jsonToNotepadList(String json) throws JsonParseException {
        Type listType = new TypeToken<List<Notepad>>(){}.getType();
        Gson gson = new Gson();
        return gson.fromJson(json, listType);
    }

    private static Notepad jsonToNotepad(String json) throws JsonParseException {
        Type type = new TypeToken<Notepad>(){}.getType();
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    private static String notepadToJson(Notepad notepad) throws Exception {
        Type type = new TypeToken<Notepad>() {}.getType();
        Gson gson = new Gson();
        return gson.toJson(notepad, type);
    }



}
