package app.ajay.ld43;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	
	Game game;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		game = new Game(this);
	}

	@Override
	public void render () {
		//update all objects
		game.update();
		
		//clear screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//render all objects
		game.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		game.dispose();
	}
}
