package com.sketchy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.sketchy.game.Controllers.ClientController;

public class LoadingView extends View {
    final ClientController controller;

    public LoadingView(final ClientController controller) {
        this.controller = controller;

        // Loading
        Label loading = new Label("Loading...", redLabel);

        // Add elements to table
        table.add(loading).padBottom(45);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.input.setCatchBackKey(true);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            controller.goBack();
        }
    }

}
