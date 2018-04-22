package com.sketchy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.sketchy.game.Controllers.ClientController;
import com.sketchy.game.Models.Drawing;
import com.sketchy.game.Models.Sheet;

import java.io.IOException;

public class RewindView extends View {
    private final ClientController clientController;

    private Label who, guessWord, guessLabel, imageLabel, image;
    private TextButton next;

    private boolean isLobbyMaster = false;
    private ShapeRenderer shapeRenderer;
    private Drawing drawing;

    RewindView(final ClientController clientController) {
        this.clientController = clientController;
        shapeRenderer = new ShapeRenderer();

        // Labels
        who = new Label("if you see this something is wrong", blueLabel);
        guessLabel = new Label("Guessed", blueLabel);
        imageLabel = new Label("Drew", blueLabel);
        guessWord = new Label("Chimpanzee", blueLabel);

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

            drawing = null;

            if(first){
                who.setText(sheet.getDrawer());
                guessWord.setText(sheet.getObjectiveWord());
                guessLabel.setText("Got");
            } else {
                who.setText(sheet.getGuesser());
                guessWord.setText(sheet.getAnswer());
                guessLabel.setText("Guessed");
            }

            table.add(who).padTop(getScreenHeight()*0.07f);
            table.row();
            table.add(guessLabel);
            table.row();
            table.add(guessWord);
        } else{
            try {
                drawing = Drawing.fromBase64(sheet.getBase64Drawing());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            who.setText(sheet.getDrawer());
            table.add(who).top().padTop(getScreenHeight()*0.07f).expandY().top();
            table.add(imageLabel).top().padTop(getScreenHeight()*0.07f).expandY().top();
        }

        table.row();
        table.add(next).bottom().padBottom(getScreenHeight()*0.03f).colspan(2);
    }

    public void setLobbyMaster() {
        isLobbyMaster = true;
        next.setVisible(true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (drawing != null) drawing.render(shapeRenderer, getScreenHeight(), 0);

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
