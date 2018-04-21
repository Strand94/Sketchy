package com.sketchy.game.Models;

public class Sheet {
    public static final Sheet LOADING = new Sheet();

    private String objectiveWord;
    private String base64Drawing;
    private String answer;
    private String drawer;
    private String guesser;

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

    public String getDrawer() {
        return drawer;
    }

    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getGuesser() {
        return guesser;
    }

    public void setGuesser(String guesser) {
        this.guesser = guesser;
    }

    public boolean nextTaskIsDraw() {
        return base64Drawing == null;
    }
}
