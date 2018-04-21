package com.sketchy.game.Models;

public class Sheet {
    public static final Sheet LOADING = new Sheet();

    private String objectiveWord;
    private String drawing;
    private String answer;
    private String playerName;


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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
