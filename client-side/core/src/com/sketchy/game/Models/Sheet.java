package com.sketchy.game.Models;

public abstract class Sheet {

    private String objectiveWord;
    private String drawing;
    private Player player;

    public String getObjectiveWord() {
        return objectiveWord;
    }

    public void setObjectiveWord(String objectiveWord) {
        this.objectiveWord = objectiveWord;
    }

    public String getDrawing() {
        return drawing;
    }

    public void setDrawing(String drawing) {
        this.drawing = drawing;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
