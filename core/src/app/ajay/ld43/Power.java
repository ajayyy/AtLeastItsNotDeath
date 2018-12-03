package app.ajay.ld43;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * For powerups and powerdowns
 */
public class Power {
	
	//is it good
	boolean positive;
	
	Texture icon;
	
	String description;
	
	int type;
	
	public Power(int type) {
		this.type = type;
		
		switch (type) {
		case 0:
			positive = true;
			description = "Increase max player speed";
			icon = new Texture("playerSpeed.png");
			break;
		case 1:
			positive = true;
			description = "Increase player speed";
			icon = new Texture("playerSpeed.png");
			break;
		case 2:
			positive = true;
			description = "Increase player jump height";
			icon = new Texture("jumpSpeed.png");
			break;
		case 3:
			positive = false;
			description = "Upside down screen";
			icon = new Texture("upsideDown.png");
			break;
		case 4:
			positive = false;
			description = "Screen Shake";
			icon = new Texture("screenShake.png");
			break;
		case 5:
			positive = false;
			description = "Invisible Player";
			icon = new Texture("invisible.png");
			break;
		case 6:
			positive = false;
			description = "Random Rotation";
			icon = new Texture("upsideDown.png");
			break;
		case 7:
			positive = false;
			description = "Fading Player";
			icon = new Texture("invisible.png");
			break;
		case 8:
			positive = false;
			description = "Zoomed In";
			icon = new Texture("zoom.png");
			break;
		case 9:
			positive = false;
			description = "Random Zooming";
			icon = new Texture("zoom.png");
			break;
		case 10:
			positive = true;
			description = "More visibilty";
			icon = new Texture("zoom.png");
			break;
		case 11:
			positive = true;
			description = "Moon gravity";
			icon = new Texture("jumpSpeed.png");
			break;
		case 12:
			positive = true;
			description = "Slower Enemies";
			icon = new Texture("playerSpeed.png");
			break;
		case 13:
			positive = false;
			description = "Faster Enemies";
			icon = new Texture("playerSpeed.png");
			break;
		case 14:
			positive = true;
			description = "Make player smaller";
			icon = new Texture("screenShake.png");
			break;
		case 15:
			positive = false;
			description = "Constant Color Changing";
			icon = new Texture("screenShake.png");
			break;
		case 16:
			positive = false;
			description = "Low lighting";
			icon = new Texture("screenShake.png");
			break;
		}
	}
	
	//called when the item get's used
	public void use(Game game) { 
		switch (type) {
		case 0:
			game.player.maxSpeed = game.player.defaultMaxSpeed * 1.5f;
			break;
		case 1:
			game.player.speed = game.player.defaultSpeed * 2f;
			break;
		case 2:
			game.player.jumpSpeed = game.player.defaultJumpSpeed * 1.5f;
			break;
		case 3:
			game.revivalScreen.upsideDown = true;
			break;
		case 4:
			game.player.screenshake = true;
			break;
		case 5:
			game.player.translucent = true;
			break;
		case 6:
			game.revivalScreen.randomRotation = true;
			break;
		case 7:
			game.player.fading = true;
			break;
		case 8:
			game.main.cam.viewportWidth = Gdx.graphics.getWidth() / 2f;
			game.main.cam.viewportHeight = Gdx.graphics.getHeight() / 2f;
			game.main.cam.update();
			game.revivalScreen.zoomedIn = true;
			break;
		case 9:
			game.revivalScreen.randomZoom = true;
			break;
		case 10:
			game.main.cam.viewportWidth = Gdx.graphics.getWidth() * 2f;
			game.main.cam.viewportHeight = Gdx.graphics.getHeight() * 2f;
			game.main.cam.update();
			game.revivalScreen.zoomedOut = true;
			break;
		case 11:
			game.player.gravity = game.player.defaultGravity * 0.5f;
			break;
		case 12:
			game.enemySpeed = game.defaultEnemySpeed * 0.25f;
			break;
		case 13:
			game.enemySpeed = game.defaultEnemySpeed * 2.5f;
			break;
		case 14:
			game.player.width = game.player.defaultWidth / 2f;
			game.player.height = game.player.defaultHeight / 2f;
			break;
		case 15:
			game.main.hues = true;
			break;
		case 16:
			game.main.lighting = true;
			break;
		}
	}
	
	//called when the item is not being used anymore, undo
	public void dispose(Game game) { 
		switch (type) {
		case 0:
			game.player.maxSpeed = game.player.defaultMaxSpeed;
			break;
		case 1:
			game.player.speed = game.player.defaultSpeed;
			break;
		case 2:
			game.player.jumpSpeed = game.player.defaultJumpSpeed;
			break;
		case 3:
			game.revivalScreen.upsideDown = false;
			break;
		case 4:
			game.player.screenshake = false;
			break;
		case 5:
			game.player.translucent = false;
			break;
		case 6:
			game.revivalScreen.randomRotation = false;
			break;
		case 7:
			game.player.fading = false;
			break;
		case 8:
			game.main.cam.viewportWidth = Gdx.graphics.getWidth();
			game.main.cam.viewportHeight = Gdx.graphics.getHeight();
			game.main.cam.update();
			game.revivalScreen.zoomedIn = false;
			break;
		case 9:
			game.main.cam.viewportWidth = Gdx.graphics.getWidth();
			game.main.cam.viewportHeight = Gdx.graphics.getHeight();
			game.main.cam.update();
			game.revivalScreen.randomZoom = false;
			break;
		case 10:
			game.main.cam.viewportWidth = Gdx.graphics.getWidth();
			game.main.cam.viewportHeight = Gdx.graphics.getHeight();
			game.main.cam.update();
			game.revivalScreen.zoomedOut = false;
			break;
		case 11:
			game.player.gravity = game.player.defaultGravity;
			break;
		case 12:
			game.enemySpeed = game.defaultEnemySpeed;
			break;
		case 13:
			game.enemySpeed = game.defaultEnemySpeed;
			break;
		case 14:
			game.player.width = game.player.defaultWidth;
			game.player.height = game.player.defaultHeight;
			break;
		case 15:
			game.main.hues = false;
			break;
		case 16:
			game.main.lighting = false;
			break;
		}
	}
}
