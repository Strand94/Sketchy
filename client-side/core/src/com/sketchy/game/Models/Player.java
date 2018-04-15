package com.sketchy.game.Models;

public class Player {
    String name;
    String clientAdress;
    int points = 0;

    public Player(String name) {
        this.name = name;
    }

    public void addPoints(int points){

    }

    public String getName() {
        return name;
    }
}
