package com.sketchy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.sketchy.game.SketchyGame;

public class LoginView extends View{
    final SketchyGame game;

    public LoginView(final SketchyGame game){
        this.game = game;

        // Header
        Label header = new Label("Sketchy", uiSkin);

        // Buttons
        TextButton create = new TextButton("Create Game", uiSkin);
        TextButton join = new TextButton("Join Game", uiSkin);

        // TextFields
        final TextField nameField = new TextField("Your Name", uiSkin);

        // Labels
        Label nameLabel = new Label("Name:", uiSkin);

        // Add elements to table
        table.pad(10);
        table.add(header).width(100).top().center().colspan(2);
        table.row();
        table.add(nameLabel).width(100);
        table.add(nameField);
        table.row();
        table.add(create).center().colspan(2);
        table.row();
        table.add(join).center().colspan(2);

        // Listeners
        join.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Join Game");
                System.out.println(nameField.getText());
            }
        });

        create.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Create Game");
                game.clientController.setView(new LobbyView(game));
            }
        });
    }

    @Override
    public void show(){

    }

    @Override
    public void render(float delta){
        super.render(delta);
        Gdx.gl.glClearColor(68.0f/256, 117.0f/256, 180.0f/256, 1);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose(){
        super.dispose();
    }

}