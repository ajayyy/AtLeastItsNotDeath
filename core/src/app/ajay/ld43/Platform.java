package app.ajay.ld43;

import com.badlogic.gdx.graphics.Texture;

public class Platform {
	float x, y;
	float width, height;
	
	Texture image;
	
	public Platform(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		image = new Texture("player.png");
	}
	
	public void update(Game game) {
		
	}
	
	public void render(Main main) {
		main.batch.begin();
		
		main.batch.draw(image, x, y, width, height);
		
		main.batch.end();
	}
}
