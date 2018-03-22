package com.sketchy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.sketchy.game.SketchyGame;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import java.util.Stack;

public class DrawView extends View {

    final SketchyGame game;
    private OrthographicCamera camera;

    // Rendering
    private ShapeRenderer shapeRenderer;

    //Text
    private String draw = "Draw View", guessWord;
    private GlyphLayout draw_layout;
    private GlyphLayout guessWord_layout;

    private Stack<Dots> drawing;
    private float current_radius = 5.0f;
    private Color current_color = Color.CYAN;

    /**
     * A drawing consists of a stack of colored circles (Dots)
     */
    public class Dots {
        private float radius;
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

        // Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);

        // Text initialization
        draw_layout = new GlyphLayout(game.font, draw);

        // Drawing initialization
        drawing = new Stack<Dots>();
        shapeRenderer = new ShapeRenderer();
    }

    /**
     * Add colored Circle (Dot) at position of user input
     * Drawing consists of lots of Dots.
     */
    private void Draw(){
        if(Gdx.input.isTouched()){
            float x = Gdx.input.getX();
            float y = Gdx.input.getY();
            drawing.add(new Dots(current_radius, new Vector2(x, y), current_color));
        }
    }

    /**
     * Render ALL the Dots!
     */
    private void RenderDrawing(){
        for (Dots dot : drawing) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(dot.color);
            shapeRenderer.circle(dot.position.x, screenHeight - dot.position.y, dot.radius);
            shapeRenderer.end();
        }
    }

    /**
     * Renders ALL the text!
     */
    private void RenderText(){
        game.font.draw(game.batch, draw, (screenWidth-draw_layout.width)*0.5f, screenHeight * (1-0.1f));
    }

    @Override
    public void show(){

    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0.2f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        Draw();

        game.batch.begin();
        RenderDrawing();
        RenderText();
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

    public void getDrawing() {} // TODO
}
