package app.ajay.ld43;

import com.badlogic.gdx.Gdx;

public class Game {
	
	Main main;
	
	//time in between frames
	float deltaTime;
	
	Player player;
	
	public Game(Main main) {
		this.main = main;
		
		player = new Player(0, 0);
	}
	
	public void update() {
		//update deltaTime
		deltaTime = Gdx.graphics.getDeltaTime();
		//if it is going under 15 frames per second, it is going too slowly. Cap it there.
		if (deltaTime > 1/15f) {
			deltaTime = 1/15f;
		}
		
		player.update(this);
	}
	
	public void render() {
		player.render(main);
	}
	
	public void dispose() {
		player.dispose();
	}
}
