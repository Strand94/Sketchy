package com.sketchy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.sketchy.game.SketchyGame;

import java.util.Stack;

public class GuessView extends View {
    final SketchyGame game;
    Stack<DrawView.Dots> drawing;

    // Renderer
    private ShapeRenderer shapeRenderer;

    public GuessView(SketchyGame game, Stack<DrawView.Dots> drawing) {
        this.game = game;
        this.drawing = drawing;

        shapeRenderer = new ShapeRenderer();

        // Header
        header = new Label("Wat is dis?", uiSkin);
        header.setColor(Color.RED);

        // TextFields
        guessField = new TextField("Enter guess here", uiSkin);
        guessField.setColor(Color.CORAL);

        // Buttons
        send = new TextButton("Send", uiSkin);

        // Add elements to table
        table.add(header).padBottom(45);
        table.row();
        table.add(guessField).width(300).padBottom(25);
        table.row();
        table.add(send).width(250).padBottom(25);

        // Listeners
        send.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onSend();
            }
        });
    }

    @Override
    public void show(){}

    @Override
    public void render(float delta){
        super.render(delta);
        Gdx.gl.glClearColor(49.0f/256, 176.0f/256, 213.0f/256, 1);
        Gdx.input.setCatchBackKey(true);

        for (DrawView.Dots dot : drawing) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(dot.color);
            shapeRenderer.circle(dot.position.x, screenHeight - dot.position.y, dot.radius);
            shapeRenderer.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose(){
        super.dispose();
    }

    void getGuess(){

    }

    void onSend(){
        System.out.println("You have pressed send. Unfortunately it doesn't do anything. :shrug:");
    }
}