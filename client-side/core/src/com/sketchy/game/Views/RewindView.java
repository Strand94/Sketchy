package com.sketchy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.sketchy.game.Controllers.ClientController;
import com.sketchy.game.Models.Sheet;

public class RewindView extends View {
    private final ClientController clientController;

    private Label who, guessWord, guessLabel, imageLabel, image;
    private TextButton next;

    private boolean isLobbyMaster = false;

    RewindView(final ClientController clientController) {
        this.clientController = clientController;

        // Labels
        who = new Label("if you see this something is wrong", blueLabel);
        guessLabel = new Label("Guessed", blueLabel);
        imageLabel = new Label("Drew", blueLabel);
        guessWord = new Label("Chimpanzee", blueLabel);
        image = new Label("", blueLabel);

        // Buttons
        next = new TextButton("Next", uiSkin);
        next.setVisible(false);

        // Listeners
        next.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clientController.requestNextRewindStep();
            }
        });
    }

    public void showRewindStep(Sheet sheet, boolean guess, boolean first){
        table.reset();

        if(guess){
            who.setText(sheet.getGuesser());
            table.add(who).top().padTop(getScreenHeight()*0.07f);
            table.row();

            if(first){
                guessWord.setText(sheet.getObjectiveWord());
                guessLabel.setText("Got");
            } else {
                guessWord.setText(sheet.getAnswer());
                guessLabel.setText("Guessed");
            }

            table.add(guessLabel);
            table.row();
            table.add(guessWord);
        } else{
            who.setText(sheet.getDrawer());
            table.add(who).top().padTop(getScreenHeight()*0.07f);
            table.row();

            image.setText("todo:render drawing");
            table.add(imageLabel);
            table.row();
            table.add(image);
        }

        table.row();
        table.add(next).bottom().padBottom(getScreenHeight()*0.03f);
    }

    public void setLobbyMaster() {
        isLobbyMaster = true;
        next.setVisible(true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(250.0f / 256, 171.0f / 256, 71.0f / 256, 1);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            clientController.rewindShowNext();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
