package app.ajay.ld43;

import com.badlogic.gdx.graphics.Texture;

public class Platform {
	float x, y;
	float width, height;
	
	public Platform(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void update(Game game) {
		
	}
	
	public void render(Main main) {
		main.batch.begin();
		
		main.batch.draw(main.game.platformImage, x, y, width, height);
		
		main.batch.end();
	}
}
