package com.sketchy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.sketchy.game.SketchyGame;

public class LoginView extends View{
    final SketchyGame game;
    private OrthographicCamera camera;

    // Text
    private String title = "Sketchy", main_menu = "Main Menu", click_to_play = "Click To Play";
    private GlyphLayout title_layout, main_menu_layout, click_to_play_layout;


    public LoginView(SketchyGame game){
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);

        //Can be used to get font width (or height)
        title_layout = new GlyphLayout(game.font, title);
        main_menu_layout = new GlyphLayout(game.font, main_menu);
        click_to_play_layout = new GlyphLayout(game.font, click_to_play);

    }

    @Override
    public void show(){

    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        game.font.draw(game.batch, main_menu, screenWidth * 0.1f, screenHeight * (1-0.2f));
        game.font.draw(game.batch, title , (screenWidth-title_layout.width)*0.5f, screenHeight * (1-0.1f));
        game.font.draw(game.batch, click_to_play, screenWidth * 0.1f, screenHeight * (1-0.23f));

        game.batch.end();

        if(Gdx.input.isTouched()){
            game.setScreen(new DrawView(game));
            dispose();
        }
    }

    @Override
    public void dispose(){
        // TODO Auto-generated method stub

    }

}