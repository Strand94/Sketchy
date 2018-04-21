package com.sketchy.game.Models;

public class Sheet {
    public static final Sheet LOADING = new Sheet();

    private String objectiveWord;
    private String base64Drawing;
    private String answer;
    private String playerName;


    public String getObjectiveWord() {
        return objectiveWord;
    }

    public void setObjectiveWord(String objectiveWord) {
        this.objectiveWord = objectiveWord;
    }

    public String getBase64Drawing() {
        return base64Drawing;
    }

    public void setBase64Drawing(String base64Drawing) {
        this.base64Drawing = base64Drawing;
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

    public boolean nextTaskIsDraw() {
        return base64Drawing == null;
    }
}
