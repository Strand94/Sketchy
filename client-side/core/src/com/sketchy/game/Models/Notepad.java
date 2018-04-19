package com.sketchy.game.Models;

import java.util.ArrayList;
import java.util.List;

public class Notepad {
    private String originalWord;
    private List<Sheet> sheets;
    private List<Player> route;


    public Notepad(String originalWord, List<Player> route) {
        this.originalWord = originalWord;
        this.sheets = new ArrayList<>();
        this.route = route;
    }

    public String getOriginalWord() {
        return originalWord;
    }

    public void setOriginalWord(String originalWord) {
        this.originalWord = originalWord;
    }

    public List<Sheet> getSheets() {
        return sheets;
    }

    public void setSheets(List<Sheet> sheets) {
        this.sheets = sheets;
    }

    public List<Player> getRoute() {
        return route;
    }

    public void setRoute(List<Player> route) {
        this.route = route;
    }
}
