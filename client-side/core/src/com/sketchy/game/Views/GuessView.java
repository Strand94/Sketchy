package com.sketchy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.sketchy.game.Controllers.ClientController;

import java.util.Stack;

public class GuessView extends View {
    private final ClientController controller;
    private Stack<DrawView.Dots> drawing;

    // UI elements
    private TextField guessField;
    private Label header;
    private TextButton submit;

    // Renderer
    private ShapeRenderer shapeRenderer;

    public GuessView(ClientController controller, Stack<DrawView.Dots> drawing) {
        this.controller = controller;
        this.drawing = drawing;

        shapeRenderer = new ShapeRenderer();

        // Header
        header = new Label("Wat is dis?", uiSkin);
        header.setColor(Color.RED);

        // TextFields
        guessField = new TextField("Enter guess here", uiSkin);
        guessField.setColor(Color.CORAL);

        // Buttons
        submit = new TextButton("Submit", uiSkin);

        // Add elements to table
        table.add(header).padBottom(45);
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

        for (DrawView.Dots dot : drawing) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(dot.color);
            shapeRenderer.circle(dot.position.x, screenHeight - dot.position.y, dot.radius);
            shapeRenderer.end();
        }
    }

    String getGuess() {
        return guessField.getText();
    }
}