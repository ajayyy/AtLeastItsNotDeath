package app.ajay.ld43;

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
			icon = new Texture("player.png");
			break;
		case 1:
			positive = true;
			description = "Increase player speed";
			icon = new Texture("player.png");
			break;
		case 2:
			positive = true;
			description = "Increase player jump height";
			icon = new Texture("player.png");
			break;
		case 3:
			positive = false;
			description = "Upside down screen";
			icon = new Texture("player.png");
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
			game.main.cam.rotate(180);
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
			game.main.cam.rotate(-180);
			break;
		}
	}
}
