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
		}
	}
	
	//called when the item get's used
	public void use(Game game) { 
		switch (type) {
		case 0:
			game.player.maxSpeed = game.player.defaultMaxSpeed * 1.5f;
			break;
		}
	}
	
	//called when the item is not being used anymore, undo
	public void dispose(Game game) { 
		switch (type) {
		case 0:
			game.player.maxSpeed = game.player.defaultMaxSpeed;
			break;
		}
	}
}
