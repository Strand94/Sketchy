package com.sketchy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.sketchy.game.Controllers.ClientController;
import com.sketchy.game.Models.Dot;

import java.util.Stack;

public class GuessView extends SheetView {
    private Stack<Dot> drawing;

    // UI elements
    private TextField guessField;
    private Label header;
    private TextButton submit;

    // Renderer
    private ShapeRenderer shapeRenderer;

    GuessView(ClientController controller) {
        super(controller);
        shapeRenderer = new ShapeRenderer();

        // Header
        header = new Label("Wat is dis?", redLabel);

        // TextFields
        guessField = new TextField("", redTextField);
        guessField.setMessageText("Your guess");

        // Buttons
        submit = new TextButton("Submit", uiSkin);

        // Add elements to table
        table.add(header).expandY().top().padTop(getScreenHeight()*0.07f);
        table.row();
        table.add(guessField).width(300).padBottom(25);
        table.row();
        table.add(submit).width(250).padBottom(25);

        // Listeners
        submit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onSend();
            }
        });
    }

    void onSend() {
        System.out.println(String.format("You have guessed %s. Nothing more will happen", getGuess()));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(49.0f / 256, 176.0f / 256, 213.0f / 256, 1);
        Gdx.input.setCatchBackKey(true);

        for (Dot dot : drawing) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(dot.getColor());
            shapeRenderer.circle(dot.getPosX(), getScreenHeight() - dot.getPosY(), dot.getRadius());
            shapeRenderer.end();
        }
    }

    @Override
    public void reset() {
        super.reset();
        drawing.clear();
        guessField.clear();
    }

    String getGuess() {
        return guessField.getText();
    }
}