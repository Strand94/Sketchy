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
import com.sketchy.game.Models.Player;

public class LoginView extends View {
    final ClientController controller;
    TextField nameField;

    public LoginView(final ClientController controller) {
        this.controller = controller;

        // Header
        Image imageLogo = new Image();
        imageLogo.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("sketchy_logo.png")))));
        imageLogo.setScaling(Scaling.fit);

        // Buttons
        TextButton create = new TextButton("Create Game", uiSkin);
        TextButton join = new TextButton("Join Game", uiSkin);

        // TextFields
        nameField = new TextField("Your name", uiSkin);

        // Labels
        final Label nameLabel = new Label("Name:", uiSkin);

        // Add elements to table
        table.pad(10);
        table.add(imageLogo).padBottom(70);
        table.row();
        table.add(nameLabel);
        table.row();
        table.add(nameField).width(250).padBottom(25);
        table.row();
        table.add(create).center().colspan(2).padBottom(25);
        table.row();
        table.add(join).center().colspan(2);

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

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(68.0f / 256, 117.0f / 256, 180.0f / 256, 1);
        Gdx.input.setCatchBackKey(true);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            Gdx.app.exit();
        }
    }

    private void onJoin() {
        System.out.println("Join Game");

        controller.setPlayer(new Player(nameField.getText()));
        controller.showJoin();
    }

    private void onCreate() {
        System.out.println("Create Game");

        controller.setPlayer(new Player(nameField.getText()));
        controller.createLobby(controller.getPlayer().getName());
    }

}