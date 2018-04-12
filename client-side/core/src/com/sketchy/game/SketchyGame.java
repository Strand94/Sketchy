package com.sketchy.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.sketchy.game.Views.LoginView;
<<<<<<< HEAD

=======
>>>>>>> 311eb47caad85253fa38dd9771c3e224e03110bc
public class SketchyGame extends Game {

   public SpriteBatch batch;
   public BitmapFont font;

    @Override
    public void create () {
        font = new BitmapFont(); // Use default Arial font
        batch = new SpriteBatch();
        this.setScreen(new LoginView(this));
    }

    @Override
    public void render () {
        super.render();
    }

    @Override
    public void dispose () {
        batch.dispose();
        font.dispose();
    }
}
