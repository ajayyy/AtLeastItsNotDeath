package app.ajay.ld43;

import com.badlogic.gdx.graphics.Texture;

public class Enemy {
	float x, y;
	
	float width = 64;
	float height = 64;
	
	float xSpeed, ySpeed;
	
	//for the path movement of the enemy
	float startX, targetX;
	
	//multiplier for the enemy speed in Game
	float movementSpeed;
	
	Texture image;
	
	//true when going toward start position, false when going towards target
	boolean flip = false;
	
	public Enemy(float x, float y, float startX, float targetX, float movementSpeed) {
		//the separate x and y is in case the enemy should be placed in the middle of the trail
		this.x = x;
		this.y = y;
		
		this.startX = startX;
		this.targetX = targetX;

		this.movementSpeed = movementSpeed;
		
		image = new Texture("enemy.png");
	}
	
	public void update(Game game) {
		if (!flip) {
			x += movementSpeed * game.enemySpeed * game.deltaTime;
			
			if (x >= targetX) {
				x = targetX;
				flip = true;
			}
		} else {
			x -= movementSpeed * game.enemySpeed * game.deltaTime;
			
			if (x <= startX) {
				x = startX;
				flip = false;
			}
		}
		
		//check for collision with player
		if (x < game.player.x + game.player.width && x + width > game.player.x &&
				y < game.player.y + game.player.height && y + height > game.player.y) {
			game.player.dead = true;
			game.player.y = -100;
			game.died();
		}
	}
	
	public void render(Main main) {
		main.batch.begin();
		
		main.batch.draw(image, x, y, width, height);
		
		main.batch.end();
	}
}
