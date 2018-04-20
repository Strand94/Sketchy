package com.sketchy.game.Models;

public class Guess extends Sheet {

    private String answer;

    public Guess(String objectiveWord, String playerName, String answer) {
        super(objectiveWord, playerName);
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
