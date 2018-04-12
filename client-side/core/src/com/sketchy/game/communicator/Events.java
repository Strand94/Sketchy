package com.sketchy.game.communicator;

import java.util.HashMap;
import java.util.Map;

public enum Events implements Event {
    START_GAME("start-game"),
    END_GAME("end-game"),
    JOIN_LOBBY("join-lobby"),
    CREATE_LOBBY("create-lobby"),
    SOCKET_ID("socketID"),
    PING("ping"),
    PING_OK("pingOK");


    private static final Map<String, Events> fromString;

    public static class NoSuchEvent extends IllegalArgumentException {}

    static {
        fromString = new HashMap<String, Events>(values().length);
        for (Events event : values()) fromString.put(event.toString(), event);
    }

    public static Events get(String event) {
        if (!fromString.containsKey(event)) throw new NoSuchEvent();
        return fromString.get(event);
    }

    public final String name;

    Events(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
