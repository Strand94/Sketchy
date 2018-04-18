package com.sketchy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.sketchy.game.SketchyGame;

import java.util.List;
import java.util.Stack;

public class DrawView extends View {

    final SketchyGame game;

    // Rendering
    private ShapeRenderer shapeRenderer;

    // Drawing
    private Stack<Dots> drawing;
    private float currentRadius = 5.0f;
    private Color currentColor = new Color(224.0f/256, 224.0f/256, 224.0f/256, 1);

    private Float lastX = null;
    private Float lastY = null;

    /**
     * A drawing consists of a stack of colored circles (Dots)
     */
    public class Dots {
        float radius;
        Vector2 position;
        Color color;

        /**
         * Dot constructor. Remember to add it to your Stack of Dots
         * @param radius Dot size
         * @param position Remember to render at (x, screenHeight - y))
         * @param color gdx.Graphics.Color
         */
        public Dots(float radius, Vector2 position, Color color) {
            this.radius = radius;
            this.position = position;
            this.color = color;
        }
    }

    /**
     *
     * @param game
     */
    public DrawView(SketchyGame game){
        this.game = game;

        this.loadAssets();

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
        drawing = new Stack<Dots>();
        shapeRenderer = new ShapeRenderer();
    }

    private void onSubmit() {
        game.getClientController().setView(new GuessView(game, drawing));
    }

    /**
     * Add colored Circle (Dot) at position of user input
     * Drawing consists of lots of Dots.
     */
    private void draw(){
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
                    drawing.add( new Dots(currentRadius, new Vector2(lastX, lastY), currentColor));
                }
                drawing.add(new Dots(currentRadius, new Vector2(x, y), currentColor));
            }
            lastX = x;
            lastY = y;
        } else {
            lastX = null;
            lastY = null;
        }
    }

    /**
     * Render ALL the Dots!
     */
    private void renderDrawing(){
        for (Dots dot : drawing) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(dot.color);
            shapeRenderer.circle(dot.position.x, screenHeight - dot.position.y, dot.radius);
            shapeRenderer.end();
        }
    }

    @Override
    public void show(){

    }

    @Override
    public void render(float delta){
        super.render(delta);
        Gdx.gl.glClearColor(41.0f/256, 45.0f/256, 50.0f/256, 1);

        draw();

        game.batch.begin();
        renderDrawing();
        game.batch.end();
    }

    @Override
    public void resize(int width, int height){}

    @Override
    public void pause(){}

    @Override
    public void resume(){}

    @Override
    public void hide(){}

    @Override
    public void dispose(){}

    private void loadAssets(){}

    public List<Dots> getDrawing() {
        return drawing;
    }
}
