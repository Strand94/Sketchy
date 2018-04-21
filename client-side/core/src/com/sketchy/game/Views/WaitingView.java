package com.sketchy.game.Views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class WaitingView extends View {
    private static final String DEFAULT_TEXT = "Waiting for\nother players...";

    private Label loading;

    WaitingView() {
        // Loading
        loading = new Label(DEFAULT_TEXT, redLabel);
        loading.setColor(Color.RED);

        // Add elements to table
        table.add(loading);
    }

    @Override
    public void reset() {
        setText(DEFAULT_TEXT);
    }

    public void setText(String text) {
        loading.setText(text);
    }

}
