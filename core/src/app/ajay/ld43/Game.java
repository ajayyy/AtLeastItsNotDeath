package app.ajay.ld43;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

public class Game {
	
	Main main;
	
	//time in between frames
	float deltaTime;
	
	Player player;
	
	ArrayList<Platform> platforms = new ArrayList<Platform>();
	
	public Game(Main main) {
		this.main = main;
		
		player = new Player(0, 64);
		
		platforms.add(new Platform(0, 0, 2000, 64));
		platforms.add(new Platform(400, 150, 500, 64));
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
		
		for (Platform platform : platforms) {
			platform.render(main);
		}
	}
	
	public void dispose() {
		player.dispose();
	}
	
	//checks if you are in a platform
	public Platform getPlatform(float x, float y, float width, float height) {
		for (Platform platform : platforms) {
			if (x + width >= platform.x && x <= platform.x + platform.width &&
					y + height >= platform.y && y <= platform.y + platform.height) {
				return platform;
			}
		}
		
		return null;
	}
}
