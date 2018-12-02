package app.ajay.ld43;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.Bloom;
import com.bitfire.utils.ShaderLoader;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	
	Game game;
	
	OrthographicCamera cam;
	
	PostProcessor postProcessor;
	Bloom bloom;
	
	@Override
	public void create () {
		
		//Add bloom effect
		ShaderLoader.BasePath = "shaders/";
        postProcessor = new PostProcessor(false, false, Gdx.app.getType() == ApplicationType.Desktop);
        bloom = new Bloom((int) (Gdx.graphics.getWidth() * 0.25f), (int) (Gdx.graphics.getHeight() * 0.25f));
        postProcessor.addEffect( bloom );
		
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		game = new Game(this);
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();
	}
	
	@Override
	public void resize(int width, int height) {
		cam.viewportWidth = width;
		cam.viewportHeight = height;
		
		if (game.revivalScreen.zoomedIn) {
			cam.viewportWidth = width / 2f;
			cam.viewportHeight = height / 2f;
		}
		
		if (game.revivalScreen.zoomedOut) {
			cam.viewportWidth = width * 2f;
			cam.viewportHeight = height * 2f;
		}
		
		if (game.revivalScreen.randomZoom) {
			cam.viewportWidth = width / game.revivalScreen.currentZoom;
			cam.viewportHeight = height / game.revivalScreen.currentZoom;
		}
		
		cam.update();
	}

	@Override
	public void render () {
		//update all objects
		game.update();
		
		//setup post processing
		postProcessor.capture();
		
		//update camera
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		shapeRenderer.setProjectionMatrix(cam.combined);
		
		//clear screen
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//render all objects
		game.render();
		
		//render the result
        postProcessor.render();
	}
	
	@Override
    public void resume() {
        postProcessor.rebind();
    }
	
	@Override
	public void dispose () {
		batch.dispose();
		game.dispose();
		postProcessor.dispose();
	}
}
