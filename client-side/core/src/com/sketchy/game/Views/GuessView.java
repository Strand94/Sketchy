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
import com.sketchy.game.Models.Drawing;
import com.sketchy.game.Models.Sheet;

import java.io.IOException;

public class GuessView extends SheetView {
    private Drawing drawing;
    private int drawIndex;

    // UI elements
    private TextField guessField;
    private Label header;
    private TextButton submit;

    // Renderer
    private ShapeRenderer shapeRenderer;

    GuessView(ClientController controller) {
        super(controller);
        clearGl = false;

        drawing = new Drawing();
        drawIndex = 0;
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
                onSubmit();
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(49.0f / 256, 176.0f / 256, 213.0f / 256, 1);
        Gdx.input.setCatchBackKey(true);

        while (drawIndex < drawing.size()) {
            Dot dot = drawing.get(drawIndex++);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(dot.getColor());
            shapeRenderer.circle(dot.getX(), getScreenHeight() - dot.getY(), dot.getRadius());
            shapeRenderer.end();
        }
        super.render(delta);
    }

    @Override
    public void reset() {
        super.reset();
        clearGlOnce();
        drawing.clear();
        drawIndex = 0;
        guessField.setText("");
    }

    @Override
    public void setSheet(Sheet sheet) throws Exception {
        super.setSheet(sheet);
        try {
            drawing = Drawing.fromBase64(sheet.getBase64Drawing());
            drawIndex = 0;
        } catch(IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onSubmit() {
        getSheet().setAnswer(guessField.getText());
        getSheet().setGuesser(controller.getPlayerName());
        super.onSubmit();
    }
}