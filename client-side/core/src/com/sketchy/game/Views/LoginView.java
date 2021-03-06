package com.sketchy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.sketchy.game.Controllers.ClientController;

public class LoginView extends View {

    private final ClientController controller;

    private Image imageLogo;
    private MyTextField nameField;
    private Label warning, nameLabel;
    private TextButton create, join;

    public LoginView(final ClientController controller) {
        this.controller = controller;

        // Header
        imageLogo = new Image();
        imageLogo.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("sketchy_logo.png")))));
        imageLogo.setScaling(Scaling.fit);

        // Buttons
        create = new TextButton("Create Game", uiSkin);
        join = new TextButton("Join Game", uiSkin);

        // TextFields
        nameField = new MyTextField(uiSkin, "Your name");

        // Labels
        nameLabel = new Label("Name:", uiSkin);

        // Add elements to table
        table.add(imageLogo).expandY().top().padTop(getScreenHeight()*0.2f);
        table.row();
        table.add(nameLabel).top();
        table.row();
        table.add(nameField).width(getScreenWidth()/2).expandY().top();
        table.row();
        table.add(create).center().padBottom(25);
        table.row();
        table.add(join).center();

        // Listeners
        join.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onJoin();
            }
        });

        create.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onCreate();
            }
        });
    }

    private void onJoin() {
        System.out.println("Join Game");

        if(nameField.isValid()){
            controller.setPlayer(nameField.getText());
            controller.showJoin();
        } else{
            showToast("invalid name");
        }
    }

    private void onCreate() {
        System.out.println("Create Game");

        if(nameField.isValid()){
            controller.setPlayer(nameField.getText());
            controller.createLobby(controller.getPlayerName());
        } else{
            showToast("invalid name");
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(68.0f / 256, 117.0f / 256, 180.0f / 256, 1);
        Gdx.input.setCatchBackKey(true);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            Gdx.app.exit();
        }
    }

    @Override
    public void reset() {
        nameField.clear();
    }
}