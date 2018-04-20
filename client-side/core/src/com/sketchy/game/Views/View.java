package com.sketchy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class View implements Screen {

    Stage stage;
    Table table;
    Skin uiSkin;

    public View() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // UI skin
        uiSkin = new Skin(Gdx.files.internal("uiSkin.json"));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("uiSkin.atlas"));
        uiSkin.addRegions(atlas);

        // Table for keeping track of position of UI elements
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
    }

    float getScreenHeight(){
        return Gdx.graphics.getHeight();
    }

    float getScreenWidth(){
        return Gdx.graphics.getWidth();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getCamera().viewportWidth = getScreenWidth();
        stage.getCamera().viewportHeight = getScreenHeight();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        System.out.println("*Dispose: " + this);
        stage.dispose();
    }

}
