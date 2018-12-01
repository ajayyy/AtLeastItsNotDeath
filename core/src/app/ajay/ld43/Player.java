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
	
	float gravity = 1000f;
	float jumpSpeed = 600f;
	float friction = 500f;
	
	float width = 64;
	float height = 64;
	
	public Player(float x, float y) {
		this.x = x;
		this.y = y;
		
		image = new Texture("player.png");
	}
	
	public void update(Game game) {
		
		//check if on a platform
		Platform currentPlatform = game.getPlatform(x, y, width, height);
		
		//handle input
		if(Gdx.input.isKeyPressed(Keys.D)) {
			xSpeed += speed * game.deltaTime;
		} else if(Gdx.input.isKeyPressed(Keys.A)) {
			xSpeed -= speed * game.deltaTime;
		}
		//jumping
		if(Gdx.input.isKeyPressed(Keys.W) && currentPlatform != null) {
			ySpeed = jumpSpeed;
		}
		
		//handle max speeds
		if (xSpeed >= maxSpeed) {
			xSpeed = maxSpeed;
		} else if (xSpeed <= -maxSpeed) {
			xSpeed = -maxSpeed;
		}
		
		//update position
		x += xSpeed * game.deltaTime;
		y += ySpeed * game.deltaTime;
		
		//update friction for next frame
		if (xSpeed > 0) {
			xSpeed -= friction * game.deltaTime;
			if (xSpeed < 0) { 
				xSpeed = 0;
			}
		} else if (xSpeed < 0) {
			xSpeed += friction * game.deltaTime;
			if (xSpeed > 0) { 
				xSpeed = 0;
			}
		}
		
		//update gravity for next frame
		ySpeed -= gravity * game.deltaTime;
		if (currentPlatform!= null && y <= currentPlatform.y + currentPlatform.height && ySpeed < 0) {
			ySpeed = 0;
			y = currentPlatform.y + currentPlatform.height;
		}
		
		//move camera
		float lerp = 2.5f;
		float xMovement = ((x - game.main.cam.position.x) * lerp * game.deltaTime);
		float targetY = y;
		//only move to it if the y is over half the screen, otherwise the camera would look below the ground
		if (y < Gdx.graphics.getHeight() / 2f) {
			targetY = Gdx.graphics.getHeight() / 2f;
		}
		float yMovement = ((targetY - game.main.cam.position.y) * lerp * game.deltaTime);
		
		game.main.cam.position.x += xMovement;
		game.main.cam.position.y += yMovement;
		
	}
	
	public void render(Main main) {
		main.batch.begin();
		
		main.batch.draw(image, x, y, width, height);
		
		main.batch.end();
	}
	
	public void dispose() {
		image.dispose();
	}
}
