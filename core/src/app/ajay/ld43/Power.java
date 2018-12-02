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
	
	public Power(boolean positive, String description, Texture icon) {
		this.positive = positive;
		this.description = description;
		
		this.icon = icon;
	}
}
