package com.sketchy.game.Models;

public class Player {
    String name;
    String address;
    int points;
    int lobbyId;
    boolean isLobbyMaster;

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
