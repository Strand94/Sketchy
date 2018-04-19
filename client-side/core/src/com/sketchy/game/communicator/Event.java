package com.sketchy.game.communicator;

import java.util.HashMap;
import java.util.Map;

public enum Event {
    START_GAME("start-game"),
    END_GAME("end-game"),
    JOIN_LOBBY("join-lobby"),
    CREATE_LOBBY("create-lobby"),
    UPDATE_VIEW("update-view"),
    UPDATE_LOBBY("update-lobby"),
    BEGIN_ROUND("begin-round"),
    GET_ANSWER("get-answer"),
    SOCKET_ID("socketID"),
    PING("ping"),
    PING_OK("pingOK"),
    START_REWIND("start-rewind"),
    REWIND_SHOW_NEXT("rewind-show-next"),
    REWIND_FINISHED("rewind-finished");


    private static final Map<String, Event> fromStringMap;

    // Maps event names to Event objects
    public static class NoSuchEvent extends IllegalArgumentException {
    }

    static {
        fromStringMap = new HashMap<String, Event>(values().length);
        for (Event event : values()) fromStringMap.put(event.toString(), event);
    }

    public static Event fromString(String event) {
        if (!fromStringMap.containsKey(event)) throw new NoSuchEvent();
        return fromStringMap.get(event);
    }

    public static boolean exists(String event) {
        return fromStringMap.containsKey(event);
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
