package com.sketchy.game.Models;

import java.util.ArrayList;
import java.util.List;

public class Notepad {
    private String originalWord;
    private List<Sheet> sheets;
    private List<String> route;


    public Notepad(String originalWord, List<String> route) {
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

    public Sheet getLastSheet() {
        return sheets.get(sheets.size() - 1);
    }

    public void setLastSheet(Sheet sheet) {
        sheets.set(sheets.size() - 1, sheet);
    }

    public List<Sheet> getSheets() {
        return sheets;
    }

    public void setSheets(List<Sheet> sheets) {
        this.sheets = sheets;
    }

    public List<String> getRoute() {
        return route;
    }

    public void setRoute(List<String> route) {
        this.route = route;
    }

}
