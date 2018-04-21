package com.sketchy.game.Models;

public class Player {
    public static final Player NO_PLAYER = new Player("<No player>");

    private String name;
    private String address;
    private int points;
    private int lobbyId;
    private boolean isLobbyMaster;

    public Player(String name) {
        this.name = name;
        this.points = 0;
    }

    public void addPoints(int points) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPoints() {
        return points;
    }

    public boolean isLobbyMaster() {
        return isLobbyMaster;
    }

    public int getLobbyId() {
        return lobbyId;
    }

}
