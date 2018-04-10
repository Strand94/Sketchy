package com.sketchy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sketchy.game.SketchyGame;

public class LoginView extends View{
    final SketchyGame game;

    Table table;
    Stage stage;

    public LoginView(SketchyGame game){
        this.game = game;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Table for keeping track of position of UI elements
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // UI skin
        Skin uiSkin = new Skin(Gdx.files.internal("uiSkin.json"));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("uiSkin.atlas"));
        uiSkin.addRegions(atlas);

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
            }
        });


    }

    @Override
    public void show(){

    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(68.0f/256, 117.0f/256, 180.0f/256, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

        //resize cam viewport
        stage.getCamera().viewportWidth = Gdx.graphics.getWidth();
        stage.getCamera().viewportHeight = Gdx.graphics.getHeight();

    }

    @Override
    public void dispose(){
        stage.dispose();
    }

}