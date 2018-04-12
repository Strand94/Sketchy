package com.sketchy.game.communicator;

import java.util.HashMap;
import java.util.Map;

public enum Event {
    START_GAME("start-game"),
    END_GAME("end-game"),
    JOIN_LOBBY("join-lobby"),
    CREATE_LOBBY("create-lobby"),
    SOCKET_ID("socketID"),
    PING("ping"),
    PING_OK("pingOK");


    private static final Map<String, Event> fromString;

    public static class NoSuchEvent extends IllegalArgumentException {}

    static {
        fromString = new HashMap<String, Event>(values().length);
        for (Event event : values()) fromString.put(event.toString(), event);
    }

    public static Event get(String event) {
        if (!fromString.containsKey(event)) throw new NoSuchEvent();
        return fromString.get(event);
    }

    public final String name;

    Event(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
