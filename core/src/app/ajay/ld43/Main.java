package app.ajay.ld43;

import java.util.Set;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
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
	
	//powerdown that constanly changes hues
	boolean hues;
	float hue = 0;
	
	//power down that makes low visibilty
	boolean lighting;
	
	ShaderProgram huesShader;
	ShaderProgram lightingShader;
	
	boolean startMenu = true;
	Texture background;
	
	@Override
	public void create () {
		
		//Add bloom effect
		ShaderLoader.BasePath = "shaders/";
        postProcessor = new PostProcessor(false, false, Gdx.app.getType() == ApplicationType.Desktop);
        bloom = new Bloom((int) (Gdx.graphics.getWidth() * 0.25f), (int) (Gdx.graphics.getHeight() * 0.25f));
        postProcessor.addEffect( bloom );
        
        //hue shader
        huesShader = new ShaderProgram(Gdx.files.internal("shaders/hues.vsh"), Gdx.files.internal("shaders/hues.fsh"));
  		if (huesShader.getLog().length() != 0) {
			System.out.println("hues error: \n\n" + huesShader.getLog());
  		}
  		lightingShader = new ShaderProgram(Gdx.files.internal("shaders/lighting.vsh"), Gdx.files.internal("shaders/lighting.fsh"));
  		if (lightingShader.getLog().length() != 0) {
			System.out.println("lightingShader error: \n\n" + lightingShader.getLog());
  		}
  		
  		ShaderProgram.pedantic = false;
		
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		game = new Game(this);
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();
		
		background = new Texture("game.png");
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
		if (!startMenu) {
			//update all objects
			game.update();
		}
		
		//setup post processing
		postProcessor.capture();
		
		//update camera
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		shapeRenderer.setProjectionMatrix(cam.combined);
		
		//clear screen
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		boolean startedHues = false;
		if (hues) {
			huesShader.begin();
			huesShader.setUniformf("hue", hue);
			batch.setShader(huesShader);
			
			hue += 0.5f * game.deltaTime;
			if (hue > 1) {
				hue = 0;
			}
			
			startedHues = true;
		}
		
		boolean startedLighting = false;
		if (lighting) {
			lightingShader.begin();
			lightingShader.setUniform2fv("position", new float[]{game.player.x + game.player.width / 2, game.player.y + game.player.height / 2}, 0, 2);
			batch.setShader(lightingShader);
			startedLighting = true;
		}
		
		if (!startMenu) {
			//render all objects
			game.render();
		} else {
			batch.begin();
			
			batch.draw(background, 0, 0);
			
			String message = "At least it's not death...";
			game.font.getData().setScale(1f);
			Vector3 textPosition = cam.unproject(new Vector3(0, 10, 0));
			game.font.draw(batch, message, textPosition.x, textPosition.y, Gdx.graphics.getWidth(), 1, true);
			
			message = "This is a platformer, try to reach the end. If you die, you might be "
					+ "able to be ressurected and given ONE powerup. However, that is in exchange for two sacrifices... Good Luck!";
			game.font.getData().setScale(0.4f);
			textPosition = cam.unproject(new Vector3(0, 300, 0));
			game.font.draw(batch, message, textPosition.x, textPosition.y, Gdx.graphics.getWidth(), 1, true);
			
			message = "By Ajay Ramachandran for Ludum Dare 43     https://ajay.app      dev@ajay.app";
			game.font.getData().setScale(0.4f);
			textPosition = cam.unproject(new Vector3(0, 600, 0));
			game.font.draw(batch, message, textPosition.x, textPosition.y, Gdx.graphics.getWidth(), 1, true);
			
			message = "Press A to start";
			game.font.getData().setScale(1f);
			textPosition = cam.unproject(new Vector3(0, Gdx.graphics.getHeight() - 100, 0));
			game.font.draw(batch, message, textPosition.x, textPosition.y, Gdx.graphics.getWidth(), 1, true);
			
			if (Gdx.input.isKeyPressed(Keys.A)) {
				startMenu = false;
			}
			
			batch.end();
		}
		
		if (startedHues) {
			huesShader.end();
			batch.setShader(null);
		}
		
		if (startedLighting) {
			lightingShader.end();
			batch.setShader(null);
		}
		
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
