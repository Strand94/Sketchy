package com.sketchy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.sketchy.game.Controllers.ClientController;
import com.sketchy.game.Models.Dot;
import com.sketchy.game.Models.Sheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DrawView extends SheetView {
    private static final Color INITIAL_COLOR = new Color(224.0f / 256, 224.0f / 256, 224.0f / 256, 1);
    private static final float INITIAL_RADIUS = 5.0f;

    // Rendering
    private ShapeRenderer shapeRenderer;

    // Drawing
    private Stack<Dot> drawing;
    private float currentRadius = INITIAL_RADIUS;
    private Color currentColor = INITIAL_COLOR;

    private Float lastX = null;
    private Float lastY = null;

    // UI
    private Label drawWordLabel;
    private TextButton submit;
    private List<Color> colors;

    DrawView(ClientController controller) {
        super(controller);

        // Labels
        drawWordLabel = new Label("", uiSkin);

        // Buttons
        submit = new TextButton("Submit", uiSkin);

        colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        colors.add(Color.ORANGE);
        colors.add(Color.WHITE);

        // Add to table
        table.row().colspan(5);
        table.add(drawWordLabel).top().expand().pad(20);
        table.row();

        for (Color color : colors){
            ColorButton button = new ColorButton(uiSkin, color);
            table.add(button).size(button.getWidth()*2);
        }

        table.row();
        table.add(submit).bottom().expandX().padBottom(0.03f * getScreenHeight()).colspan(5);

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

    private class ColorButton extends Button{

        public ColorButton(Skin skin, final Color color) {
            super(skin);
            this.setColor(color);

            // Listeners
            this.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    currentColor = color;
                }
            });
        }
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

    @Override
    public void reset() {
        super.reset();
        drawing.clear();
        drawWordLabel.clear();
        currentColor = INITIAL_COLOR;
        currentRadius = INITIAL_RADIUS;
        lastX = null;
        lastY = null;
    }

    @Override
    public void setSheet(Sheet sheet) throws Exception {
        super.setSheet(sheet);
        drawWordLabel.setText(sheet.getObjectiveWord());
    }
}
