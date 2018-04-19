package com.sketchy.game.Models;

public class Lobby {
    public static final Lobby LOADING = new Lobby(0);

    public final int lobbyId;
    public int playerCount;

    public Lobby(int lobbyId) {
        this.lobbyId = lobbyId;
        this.playerCount = 0;
    }
}
