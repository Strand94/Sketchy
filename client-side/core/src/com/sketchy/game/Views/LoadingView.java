package com.sketchy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.sketchy.game.Controllers.ClientController;

public class LoadingView extends WaitingView {
    private static final String DEFAULT_TEXT = "Loading...";

    private final ClientController controller;

    LoadingView(final ClientController controller) {
        super();
        this.controller = controller;
        setText(DEFAULT_TEXT);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.input.setCatchBackKey(true);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            controller.goBack();
        }
    }

    @Override
    public void reset() {
        setText(DEFAULT_TEXT);
    }
}
