package com.sketchy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.sketchy.game.SketchyGame;

public class DrawView implements Screen {

    final SketchyGame game;

    private OrthographicCamera camera;

    public DrawView(SketchyGame game){
        this.game = game;

        this.loadAssets();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
    }

    @Override
    public void show(){

    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0.2f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Draw View", 20, 550);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height){
        // TODO Auto-generated method stub

    }

    @Override
    public void pause(){
        // TODO Auto-generated method stub

    }

    @Override
    public void resume(){
        // TODO Auto-generated method stub

    }

    @Override
    public void hide(){
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose(){

    }

    private void loadAssets(){

    }

    private void initializeEntities(){

    }

    public void getDrawing() {

    }
}
