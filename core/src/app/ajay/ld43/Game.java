package app.ajay.ld43;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

public class Game {
	
	Main main;
	
	//time in between frames
	float deltaTime;
	
	Player player;
	
	ArrayList<Platform> platforms = new ArrayList<Platform>();
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	Texture platformImage;
	
	BitmapFont font;
	
	RevivalScreen revivalScreen;
	
	public Game(Main main) {
		this.main = main;
		
		//load font
		font = new BitmapFont(Gdx.files.internal("theshapeofthings.fnt"));
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		//setup images
		platformImage = new Texture("player.png");
		
		player = new Player(0, 64);
		
		platforms.add(new Platform(0, 0, 800, 64));
		platforms.add(new Platform(400, 150, 500, 64));
		platforms.add(new Platform(1000, 300, 500, 64));
		platforms.add(new Platform(1600, 450, 500, 64));
		platforms.add(new Platform(2000, 0, 2000, 64));

		enemies.add(new Enemy(400, 150 + 64, 400, 900 - 64, 200f));
		
		revivalScreen = new RevivalScreen();
	}
	
	public void update() {
		//update deltaTime
		deltaTime = Gdx.graphics.getDeltaTime();
		//if it is going under 15 frames per second, it is going too slowly. Cap it there.
		if (deltaTime > 1/15f) {
			deltaTime = 1/15f;
		}
		
		player.update(this);
		
		for (Enemy enemy : enemies) {
			enemy.update(this);
		}
		
		if (player.dead) {
			revivalScreen.update(this);
		}
		//always call this
		revivalScreen.constantUpdate(this);
	}
	
	public void render() {
		player.render(main);
		
		for (Platform platform : platforms) {
			platform.render(main);
		}
		
		for (Enemy enemy : enemies) {
			enemy.render(main);
		}
		
		if (player.dead) {
			revivalScreen.render(main);
		}
	}
	
	//called when the player dies
	public void died() {
		revivalScreen.chooseOptions();
		revivalScreen.disposeOld(this);
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
	
	public Vector2 getDistanceFromPlatform(float x, float y, float width, float height, Platform platform) {
		Vector2 distance = new Vector2();
		
		//check if it is on the left or right
		//left
		if (x + width/2 < platform.x + platform.width / 2) {
			distance.x = x + width - platform.x;
		} else {
			//right
			distance.x = - (platform.x + platform.width - x);
		}
		
		//check if it is on the top or bottom
		//bottom
		if (y + height/2 < platform.y + platform.height / 2) {
			distance.y = - (y + height - platform.y);
		} else {
			//top
			distance.y = platform.y + platform.height - y;
		}
		
		return distance;
	}
}
