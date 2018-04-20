package com.sketchy.game.Models;

public abstract class Sheet {

    private String objectiveWord;
    private String drawing;
    private String playerName;

    public Sheet(String objectiveWord, String playerName) {
        this.objectiveWord = objectiveWord;
        this.playerName = playerName;
    }

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

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
