package app.ajay.ld43;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.XmlReader.Element;

public class RevivalScreen {
	float x, y;
	
	GlyphLayout layout;
	
	ArrayList<Power> powerUps = new ArrayList<Power>();
	ArrayList<Power> powerDowns = new ArrayList<Power>();
	
	Power[] powerUpOptions = new Power[2];
	Power[][] powerDownOptions = new Power[2][2];
	
	//this will be set with any currently active powerups, null if none
	Power currentPowerUp;
	Power[] currentPowerDowns = new Power[2];
	
	Random random = new Random();
	
	//for handling rotating upside down
	boolean upsideDown;
	//for handling random rotation power down
	boolean randomRotation;
	float targetRotation = -1;
	float currentRotation = 0;
	
	boolean zoomedIn;
	boolean zoomedOut;
	
	boolean randomZoom;
	float targetZoom = -1;
	float currentZoom = 1;
	
	Sound select;
	Sound dead;
	
	//is it the first time this player died?
	boolean firstTime = true;
	String[] possibleMessages = {
			"It looks like you need a bit of help...",
			"Seriously, again....",
			"Why don't you get better",
			"Welcome back, pick your poison...",
			"Here are some things to help you out",
			"You did an awesome job that last one!",
			"Good luck with your next try!",
			"You just keep getting worse, come on...",
			"You are terrible",
			"Hello again",
			"Never going to give you up...",
			"I see you want to try again...",
			"Hint, it is pretty difficult",
			"K.",
			"lol ur back",
			"..."
	};
	String currentMessage;
	
	public RevivalScreen() {
		layout = new GlyphLayout();
		
		powerUps.add(new Power(0));
		powerUps.add(new Power(1));
		powerUps.add(new Power(2));
		powerUps.add(new Power(10));
		powerUps.add(new Power(11));
		powerUps.add(new Power(12));
		powerUps.add(new Power(14));

		powerDowns.add(new Power(-1));
		powerDowns.add(new Power(-1));
		powerDowns.add(new Power(3));
		powerDowns.add(new Power(4));
		powerDowns.add(new Power(5));
		powerDowns.add(new Power(6));
		powerDowns.add(new Power(7));
		powerDowns.add(new Power(8));
		powerDowns.add(new Power(9));
		powerDowns.add(new Power(13));
		powerDowns.add(new Power(15));
		powerDowns.add(new Power(16));
		powerDowns.add(new Power(17));
		
		select = Gdx.audio.newSound(Gdx.files.internal("select.wav"));
		dead = Gdx.audio.newSound(Gdx.files.internal("dead.wav"));
	}
	
	public void chooseOptions() {
		for (int i = 0 ; i < powerUpOptions.length; i++) {
			powerUpOptions[i] =  powerUps.get(random.nextInt(powerUps.size()));
		}
		for (int i = 0 ; i < powerDownOptions.length; i++) {
			for (int s = 0 ; s < powerDownOptions[i].length; s++) {
				Power powerDown = powerDowns.get(random.nextInt(powerDowns.size()));
				
				//if it does not work with others, keep picking new ones
				while (powerUpOptions[i].doesNotWorkWith.contains(powerDown.type) || 
						(s == 1 && (powerDownOptions[i][0].doesNotWorkWith.contains(powerDown.type) || powerDownOptions[i][0].type == powerDown.type))) {
					powerDown = powerDowns.get(random.nextInt(powerDowns.size()));
				}
				
				powerDownOptions[i][s] = powerDown;
			}
		}
		
		if (!firstTime) {
			currentMessage = possibleMessages[random.nextInt(possibleMessages.length)];
		} else {
			currentMessage = possibleMessages[0];
			firstTime = false;
		}
	}
	
	public void update(Game game) {
		if (Gdx.input.isKeyJustPressed(Keys.A)) {
			revive(game, 0);
		} else if (Gdx.input.isKeyJustPressed(Keys.D)) {
			revive(game, 1);
		}
	}
	
	//called even when the revival screen is not open
	public void constantUpdate(Game game) {
		if (upsideDown) {
			if (currentRotation < 180) {
				float lerp = 4f;
				float rotation = ((180 - currentRotation) * lerp * game.deltaTime);
				
				game.main.cam.rotate(rotation);
				currentRotation += rotation;
			}
		} else if(randomRotation) {
			//no target had been chosen yet
			if (targetRotation == -1) {
				//choose a target rotation
				targetRotation = random.nextFloat() * 360;
			}
			
			//go towards that target rotation
			float lerp = 1.1f;
			float rotation = ((targetRotation - currentRotation) * lerp * game.deltaTime);
			
			game.main.cam.rotate(rotation);
			currentRotation += rotation;
			
			if (Math.abs(currentRotation - targetRotation) < 10) {
				//choose a new target next frame, close enough to target
				targetRotation = -1f;
			}
		} else {
			if (currentRotation > 0) {
				float lerp = 4f;
				float rotation = ((-currentRotation) * lerp * game.deltaTime);
				
				game.main.cam.rotate(rotation);
				currentRotation += rotation;
				targetRotation = -1;
			}
		}
		
		if (randomZoom) {
			//no target had been chosen yet
			if (targetZoom == -1) {
				//choose a target rotation
				targetZoom = random.nextFloat() * 2.5f + 0.2f;
			}
			
			//go towards that target rotation
			float lerp = 1.1f;
			float zoom = ((targetZoom - currentZoom) * lerp * game.deltaTime);
			
			currentZoom += zoom;
			
			game.main.cam.viewportWidth = Gdx.graphics.getWidth() * currentZoom;
			game.main.cam.viewportHeight = Gdx.graphics.getHeight() * currentZoom;
			game.main.cam.update();
			
			if (Math.abs(currentZoom - targetZoom) < 0.05f) {
				//choose a new target next frame, close enough to target
				targetZoom = -1f;
			}
		}
	}
	
	public void disposeOld(Game game) {
		if (currentPowerUp != null) {
			currentPowerUp.dispose(game);
		}
		if (currentPowerDowns[0] != null) {
			currentPowerDowns[0].dispose(game);
		}
		if (currentPowerDowns[1] != null) {
			currentPowerDowns[1].dispose(game);
		}
		
		dead.play();
	}
	
	public void revive(Game game, int side) {
		currentPowerUp = powerUpOptions[side];
		currentPowerUp.use(game);
		currentPowerDowns[0] = powerDownOptions[side][0];
		currentPowerDowns[0].use(game);
		currentPowerDowns[1] = powerDownOptions[side][1];
		currentPowerDowns[1].use(game);
		
		game.player.revive();
		
		select.play();
	}
	
	public void render(Main main) {
		main.batch.begin();
		
		String message = "You Lost...";
		main.game.font.getData().setScale(1f);
		layout.setText(main.game.font, message);
		Vector3 textPosition = main.cam.unproject(new Vector3(Gdx.graphics.getWidth()/2 - layout.width/2, 10, 0));
		main.game.font.draw(main.batch, message, textPosition.x, textPosition.y);
		
		message = currentMessage;
		main.game.font.getData().setScale(0.6f);
		layout.setText(main.game.font, message);
		textPosition = main.cam.unproject(new Vector3(Gdx.graphics.getWidth()/2 - layout.width/2, 100, 0));
		main.game.font.draw(main.batch, message, textPosition.x, textPosition.y);
		
		message = "This will replace any current effects";
		main.game.font.getData().setScale(0.3f);
		layout.setText(main.game.font, message);
		textPosition = main.cam.unproject(new Vector3(Gdx.graphics.getWidth()/2 - layout.width/2, 150, 0));
		main.game.font.draw(main.batch, message, textPosition.x, textPosition.y);
		
		message = "Press A or D to select an option";
		main.game.font.getData().setScale(0.4f);
		layout.setText(main.game.font, message);
		textPosition = main.cam.unproject(new Vector3(Gdx.graphics.getWidth()/2 - layout.width/2, Gdx.graphics.getHeight() - 100, 0));
		main.game.font.draw(main.batch, message, textPosition.x, textPosition.y);
		
		message = "OR";
		main.game.font.getData().setScale(1f);
		layout.setText(main.game.font, message);
		textPosition = main.cam.unproject(new Vector3(Gdx.graphics.getWidth()/2 - layout.width/2, 300, 0));
		main.game.font.draw(main.batch, message, textPosition.x, textPosition.y);
		
		main.batch.end();
		
		main.shapeRenderer.begin(ShapeType.Filled);
		
		//draw the rectangles that will hold the icons
		//option 0, good
		Vector3 option0Position = main.cam.unproject(new Vector3(100, 200, 0));
		main.shapeRenderer.setColor(Color.GRAY);
		main.shapeRenderer.rect(option0Position.x, option0Position.y, 400, -400);
		
		//option 0, bad 0
		Vector3 option0Bad0Position = main.cam.unproject(new Vector3(100 + 20, 620, 0));
		main.shapeRenderer.setColor(Color.GRAY);
		main.shapeRenderer.rect(option0Bad0Position.x, option0Bad0Position.y, 170, -170);
		
		//option 0, bad 1
		Vector3 option0Bad1Position = main.cam.unproject(new Vector3(100 + 170 + 20*2, 620, 0));
		main.shapeRenderer.setColor(Color.GRAY);
		main.shapeRenderer.rect(option0Bad1Position.x, option0Bad1Position.y, 170, -170);
		
		
		//option 1, good
		Vector3 option1Position = main.cam.unproject(new Vector3(Gdx.graphics.getWidth() - 100 - 400, 200, 0));
		main.shapeRenderer.setColor(Color.GRAY);
		main.shapeRenderer.rect(option1Position.x, option1Position.y, 400, -400);
		
		//option 0, bad 0
		Vector3 option1Bad0Position = main.cam.unproject(new Vector3(Gdx.graphics.getWidth() - (100 + 20) - 170, 620, 0));
		main.shapeRenderer.setColor(Color.GRAY);
		main.shapeRenderer.rect(option1Bad0Position.x, option1Bad0Position.y, 170, -170);
		
		//option 0, bad 1
		Vector3 option1Bad1Position = main.cam.unproject(new Vector3(Gdx.graphics.getWidth() - (100 + 170 + 20*2) - 170, 620, 0));
		main.shapeRenderer.setColor(Color.GRAY);
		main.shapeRenderer.rect(option1Bad1Position.x, option1Bad1Position.y, 170, -170);
		
		main.shapeRenderer.end();
		
		//draw options
		main.batch.begin();
		
		//left side
		main.batch.draw(powerUpOptions[0].icon, option0Position.x + 75, option0Position.y - 275, 250, 250);
		main.game.font.getData().setScale(0.4f);
		textPosition = new Vector3(option0Position.x, option0Position.y - 300, 0);
		main.game.font.draw(main.batch, powerUpOptions[0].description, textPosition.x, textPosition.y, 400, 1, true);
		
		main.batch.draw(powerDownOptions[0][0].icon, option0Bad0Position.x + 32, option0Bad0Position.y - 106 - 32/3, 106, 106);
		main.game.font.getData().setScale(0.2f);
		textPosition = new Vector3(option0Bad0Position.x, option0Bad0Position.y - 120, 0);
		main.game.font.draw(main.batch, powerDownOptions[0][0].description, textPosition.x, textPosition.y, 170, 1, true);
		
		main.batch.draw(powerDownOptions[0][1].icon, option0Bad1Position.x + 32, option0Bad1Position.y - 106 - 32/3, 106, 106);
		main.game.font.getData().setScale(0.2f);
		textPosition = new Vector3(option0Bad1Position.x, option0Bad1Position.y - 120, 0);
		main.game.font.draw(main.batch, powerDownOptions[0][1].description, textPosition.x, textPosition.y, 170, 1, true);
		
		//right side
		main.batch.draw(powerUpOptions[1].icon, option1Position.x + 75, option1Position.y - 275, 250, 250);
		main.game.font.getData().setScale(0.4f);
		textPosition = new Vector3(option1Position.x, option1Position.y - 300, 0);
		main.game.font.draw(main.batch, powerUpOptions[1].description, textPosition.x, textPosition.y, 400, 1, true);
		
		main.batch.draw(powerDownOptions[1][0].icon, option1Bad0Position.x + 32, option1Bad0Position.y - 106 - 32/3, 106, 106);
		main.game.font.getData().setScale(0.2f);
		textPosition = new Vector3(option1Bad0Position.x, option1Bad0Position.y - 120, 0);
		main.game.font.draw(main.batch, powerDownOptions[1][0].description, textPosition.x, textPosition.y, 170, 1, true);
		
		main.batch.draw(powerDownOptions[1][1].icon, option1Bad1Position.x + 32, option1Bad1Position.y - 106 - 32/3, 106, 106);
		main.game.font.getData().setScale(0.2f);
		textPosition = new Vector3(option1Bad1Position.x, option1Bad1Position.y - 120, 0);
		main.game.font.draw(main.batch, powerDownOptions[1][1].description, textPosition.x, textPosition.y, 170, 1, true);
		
		main.batch.end();
	}
}
