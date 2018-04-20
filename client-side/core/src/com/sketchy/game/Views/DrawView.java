package com.sketchy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.sketchy.game.Controllers.ClientController;
import com.sketchy.game.Models.Dot;

import java.util.List;
import java.util.Stack;

public class DrawView extends View {

    private final ClientController controller;

    // Rendering
    private ShapeRenderer shapeRenderer;

    // Drawing
    private Stack<Dot> drawing;
    private float currentRadius = 5.0f;
    private Color currentColor = new Color(224.0f / 256, 224.0f / 256, 224.0f / 256, 1);

    private Float lastX = null;
    private Float lastY = null;


    public DrawView(ClientController controller) {
        this.controller = controller;

        // Labels
        Label guessWord = new Label("Elephant", uiSkin);

        // Buttons
        TextButton submit = new TextButton("Submit", uiSkin);

        // Add to table
        table.add(guessWord).top().expand().padTop(20);
        table.add(submit).bottom().expandX();

        // Listeners
        submit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onSubmit();
            }
        });

        // Drawing initialization
        drawing = new Stack<>();
        shapeRenderer = new ShapeRenderer();
    }

    private void onSubmit() {
        controller.showGuess(drawing);
    }

    /**
     * Add colored Circle (Dot) at position of user input
     * Drawing consists of lots of Dot.
     */
    private void draw() {
        if (Gdx.input.isTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.input.getY();
            if (lastX != null && lastY != null && (x != lastX || y != lastY)) {
                int nDots = (int) Math.sqrt(Math.pow(y - lastY, 2) + Math.pow(x - lastX, 2)) - 1;
                float dX = (x - lastX) / nDots;
                float dY = (y - lastY) / nDots;
                for (int i = 0; i < nDots; i += 3) {
                    lastX += 3 * dX;
                    lastY += 3 * dY;
                    drawing.add(new Dot(currentRadius, new Vector2(lastX, lastY), currentColor));
                }
                drawing.add(new Dot(currentRadius, new Vector2(x, y), currentColor));
            }
            lastX = x;
            lastY = y;
        } else {
            lastX = null;
            lastY = null;
        }
    }

    /**
     * Render ALL the Dot!
     */
    private void renderDrawing() {
        for (Dot dot : drawing) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(dot.getColor());
            shapeRenderer.circle(dot.getPosX(), getScreenHeight() - dot.getPosY(), dot.getRadius());
            shapeRenderer.end();
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(41.0f / 256, 45.0f / 256, 50.0f / 256, 1);

        draw();

        renderDrawing();
    }

    public List<Dot> getDrawing() {
        return drawing;
    }
}
