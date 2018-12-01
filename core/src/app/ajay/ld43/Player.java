package app.ajay.ld43;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;

public class Player {
	float x, y;
	
	//these are modified first, then cause x and y to get modified
	float xSpeed, ySpeed;
	
	//the image of this player
	Texture image;
	
	//the speed the player will accelerate by when moved
	float speed = 1000f;
	float maxSpeed = 500f;
	
	public Player(float x, float y) {
		this.x = x;
		this.y = y;
		
		image = new Texture("player.png");
	}
	
	public void update(Game game) {
		
		//handle input
		if(Gdx.input.isKeyPressed(Keys.W)) {
			ySpeed += speed * game.deltaTime;
		} else if(Gdx.input.isKeyPressed(Keys.S)) {
			ySpeed -= speed * game.deltaTime;
		}
		if(Gdx.input.isKeyPressed(Keys.D)) {
			xSpeed += speed * game.deltaTime;
		} else if(Gdx.input.isKeyPressed(Keys.A)) {
			xSpeed -= speed * game.deltaTime;
		}
		
		//handle max speeds
		if (xSpeed >= maxSpeed) {
			xSpeed = maxSpeed;
		} else if (xSpeed <= -maxSpeed) {
			xSpeed = -maxSpeed;
		}
		if (ySpeed >= maxSpeed) {
			ySpeed = maxSpeed;
		} else if (ySpeed <= -maxSpeed) {
			ySpeed = -maxSpeed;
		}
		
		//update position
		x += xSpeed * game.deltaTime;
		y += ySpeed * game.deltaTime;
	}
	
	public void render(Main main) {
		main.batch.begin();
		
		main.batch.draw(image, x, y);
		
		main.batch.end();
	}
	
	public void dispose() {
		image.dispose();
	}
}
