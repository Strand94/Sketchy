package com.sketchy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.sketchy.game.Controllers.ClientController;
import com.sketchy.game.Models.Notepad;
import com.sketchy.game.Models.Sheet;

import java.util.ArrayList;
import java.util.List;

public class RewindView extends View {
    private final ClientController clientController;

    private Label who, guessWord, guessLabel, imageLabel, image;
    private TextButton next;

    private boolean isLobbyMaster;
    private int advances = 0;

    private Notepad notepad;
    private List<Sheet> sheets;

    public RewindView(final ClientController clientController, boolean lobbyMaster) {
        this.clientController = clientController;
        this.isLobbyMaster = lobbyMaster;


        // Labels
        who = new Label("if you see this something is wrong", blueLabel);
        guessLabel = new Label("Guessed", blueLabel);
        imageLabel = new Label("Drew", blueLabel);
        guessWord = new Label("Chimpanzee", blueLabel);
        image = new Label("", blueLabel);

        // Buttons
        next = new TextButton("Next", uiSkin);

        // Listeners
        next.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clientController.requestRewind();
            }
        });

        List<String> route = new ArrayList<>();
        notepad = new Notepad("Elephant", route);

        List<Sheet> sheets_2 = new ArrayList<>();

        Sheet sheet = new Sheet();
        sheet.setObjectiveWord(notepad.getOriginalWord());
        sheet.setAnswer("Chimpanzee");
        sheet.setDrawing("drawing.png");
        sheet.setPlayerName("First Player");
        sheets_2.add(sheet);

        Sheet sheet_2 = new Sheet();
        sheet_2.setAnswer("Yoda");
        sheet_2.setDrawing("image.png");
        sheet_2.setPlayerName("Luke");
        sheets_2.add(sheet_2);

        notepad.setSheets(sheets_2);
        sheets = notepad.getSheets();
    }

    public void showNext(){

        if (sheets.size() == 0){
            System.out.println("No more sheets in notepad to show!");
            return;
        }

        // Show objective word
        if(advances == 0){
            createRewindStep(sheets.get(0), true, true);
        }
        // Show drawing
        else if(advances % 2 == 1){
            createRewindStep(sheets.get(0), false, false);
        }

        // Show guess
        else if (advances % 2 == 0){
            createRewindStep(sheets.get(0), true, false);

            // We are now done with 1 sheet and can proceed to the next
            sheets.remove(0);
        }

        advances++;
    }

    private void createRewindStep(Sheet sheet, boolean guess, boolean first){
        table.reset();

        who.setText(sheet.getPlayerName());

        // Add to table
        table.add(who).top().padTop(getScreenHeight()*0.07f);
        table.row();

        if(guess){
            if(first){
                guessWord.setText(sheet.getObjectiveWord());
            } else {
                guessWord.setText(sheet.getAnswer());
            }
            table.add(guessLabel);
            table.row();
            table.add(guessWord);
        } else{
            image.setText(sheet.getDrawing());
            table.add(imageLabel);
            table.row();
            table.add(image);
        }

        table.row();
        table.add(next).bottom().padBottom(getScreenHeight()*0.03f);

        if(!isLobbyMaster){
            next.setVisible(false);
        }

        table.debug();
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
