package app.ajay.ld43;

import com.badlogic.gdx.graphics.Texture;

/**
 * For powerups and powerdowns
 */
public class Power {
	
	//is it good
	boolean positive;
	
	Texture icon;
	
	public Power(boolean positive, Texture icon) {
		this.positive = positive;
		
		this.icon = icon;
	}
}
