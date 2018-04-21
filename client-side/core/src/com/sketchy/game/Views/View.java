package com.sketchy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class View implements Screen {

    Stage stage;
    Table table;
    Skin uiSkin;

    // Styles
    protected Label.LabelStyle blueLabel, redLabel, greenLabel;
    protected TextField.TextFieldStyle redTextField;

    protected View() {
        stage = new Stage(new ScreenViewport());

        loadAssets();

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
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        System.out.println("*Dispose: " + this);
        stage.dispose();
    }

    private void loadAssets(){

        float density = Gdx.graphics.getDensity();
        String path, jsonPath, atlasPath;

        if (density <= 1.5){
            path = "mdpi";
        } else if (density > 1.5f && density <= 2.0f){
            path = "hdpi";
        } else {
            path = "xhdpi";
        }

        path += "/";
        jsonPath = path + "uiSkin.json";
        atlasPath = path + "uiSkin.atlas";

        System.out.println("Loading assets from " + jsonPath + ", density determined to be " + density);

        // Skin
        uiSkin = new Skin(Gdx.files.internal(jsonPath));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(atlasPath));
        uiSkin.addRegions(atlas);

        // Styles
        blueLabel = uiSkin.get("blue", Label.LabelStyle.class);
        redLabel = uiSkin.get("red", Label.LabelStyle.class);
        greenLabel = uiSkin.get("green", Label.LabelStyle.class);

        redTextField = uiSkin.get("red", TextField.TextFieldStyle.class);
    }

    public void reset() {
    }
}
