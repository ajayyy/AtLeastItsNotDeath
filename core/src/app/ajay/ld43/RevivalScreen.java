package app.ajay.ld43;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;

public class RevivalScreen {
	float x, y;
	
	GlyphLayout layout;
	
	ArrayList<Power> powerUps = new ArrayList<Power>();
	ArrayList<Power> powerDowns = new ArrayList<Power>();
	
	Power[] powerUpOptions = new Power[2];
	Power[][] powerDownOptions = new Power[2][2];
	
	Random random = new Random();
	
	public RevivalScreen() {
		layout = new GlyphLayout();
		
		powerUps.add(new Power(true, "5 times more speed", new Texture("player.png")));
		
		powerDowns.add(new Power(false, "Increased gravity", new Texture("player.png")));
	}
	
	public void chooseOptions() {
		for (int i = 0 ; i < powerUpOptions.length; i++) {
			powerUpOptions[i] =  powerUps.get(random.nextInt(powerUps.size()));
		}
		for (int i = 0 ; i < powerDownOptions.length; i++) {
			for (int s = 0 ; s < powerDownOptions[i].length; s++) {
				powerDownOptions[i][s] =  powerDowns.get(random.nextInt(powerDowns.size()));
			}
		}
	}
	
	public void update(Game game) {
		
	}
	
	public void render(Main main) {
		main.batch.begin();
		
		String message = "You Lost...";
		main.game.font.getData().setScale(1f);
		layout.setText(main.game.font, message);
		Vector3 textPosition = main.cam.unproject(new Vector3(Gdx.graphics.getWidth()/2 - layout.width/2, 10, 0));
		main.game.font.draw(main.batch, message, textPosition.x, textPosition.y);
		
		message = "It looks like you need a bit of help...";
		main.game.font.getData().setScale(0.6f);
		layout.setText(main.game.font, message);
		textPosition = main.cam.unproject(new Vector3(Gdx.graphics.getWidth()/2 - layout.width/2, 100, 0));
		main.game.font.draw(main.batch, message, textPosition.x, textPosition.y);
		
		main.batch.end();
		
		main.shapeRenderer.begin(ShapeType.Filled);
		
		//draw the rectangles that will hold the icons
		//option 0, good
		Vector3 option0Position = main.cam.unproject(new Vector3(100, 200, 0));
		main.shapeRenderer.setColor(Color.GRAY);
		main.shapeRenderer.rect(option0Position.x, option0Position.y, 400, -400);
		
		//option 0, bad 0
		Vector3 option0Bad0Position = main.cam.unproject(new Vector3(100 + 20, 620, 0));
		main.shapeRenderer.setColor(Color.GRAY);
		main.shapeRenderer.rect(option0Bad0Position.x, option0Bad0Position.y, 170, -170);
		
		//option 0, bad 1
		Vector3 option0Bad1Position = main.cam.unproject(new Vector3(100 + 170 + 20*2, 620, 0));
		main.shapeRenderer.setColor(Color.GRAY);
		main.shapeRenderer.rect(option0Bad1Position.x, option0Bad1Position.y, 170, -170);
		
		
		//option 1, good
		Vector3 option1Position = main.cam.unproject(new Vector3(Gdx.graphics.getWidth() - 100 - 400, 200, 0));
		main.shapeRenderer.setColor(Color.GRAY);
		main.shapeRenderer.rect(option1Position.x, option1Position.y, 400, -400);
		
		//option 0, bad 0
		Vector3 option1Bad0Position = main.cam.unproject(new Vector3(Gdx.graphics.getWidth() - (100 + 20) - 170, 620, 0));
		main.shapeRenderer.setColor(Color.GRAY);
		main.shapeRenderer.rect(option1Bad0Position.x, option1Bad0Position.y, 170, -170);
		
		//option 0, bad 1
		Vector3 option1Bad1Position = main.cam.unproject(new Vector3(Gdx.graphics.getWidth() - (100 + 170 + 20*2) - 170, 620, 0));
		main.shapeRenderer.setColor(Color.GRAY);
		main.shapeRenderer.rect(option1Bad1Position.x, option1Bad1Position.y, 170, -170);
		
		main.shapeRenderer.end();
		
		//draw options
		main.batch.begin();
		
		//left side
		main.batch.draw(powerUpOptions[0].icon, option0Position.x + 75, option0Position.y - 275, 250, 250);
		main.game.font.getData().setScale(0.4f);
		textPosition = new Vector3(option0Position.x, option0Position.y - 300, 0);
		main.game.font.draw(main.batch, powerUpOptions[0].description, textPosition.x, textPosition.y, 400, 1, true);
		
		main.batch.draw(powerDownOptions[0][0].icon, option0Bad0Position.x + 32, option0Bad0Position.y - 106 - 32/3, 106, 106);
		main.game.font.getData().setScale(0.2f);
		textPosition = new Vector3(option0Bad0Position.x, option0Bad0Position.y - 120, 0);
		main.game.font.draw(main.batch, powerDownOptions[0][0].description, textPosition.x, textPosition.y, 170, 1, true);
		
		main.batch.draw(powerDownOptions[0][1].icon, option0Bad1Position.x + 32, option0Bad1Position.y - 106 - 32/3, 106, 106);
		main.game.font.getData().setScale(0.2f);
		textPosition = new Vector3(option0Bad1Position.x, option0Bad1Position.y - 120, 0);
		main.game.font.draw(main.batch, powerDownOptions[0][1].description, textPosition.x, textPosition.y, 170, 1, true);
		
		//right side
		main.batch.draw(powerUpOptions[1].icon, option1Position.x + 75, option1Position.y - 275, 250, 250);
		main.game.font.getData().setScale(0.4f);
		textPosition = new Vector3(option1Position.x, option1Position.y - 300, 0);
		main.game.font.draw(main.batch, powerUpOptions[0].description, textPosition.x, textPosition.y, 400, 1, true);
		
		main.batch.draw(powerDownOptions[1][0].icon, option1Bad0Position.x + 32, option1Bad0Position.y - 106 - 32/3, 106, 106);
		main.game.font.getData().setScale(0.2f);
		textPosition = new Vector3(option1Bad0Position.x, option1Bad0Position.y - 120, 0);
		main.game.font.draw(main.batch, powerDownOptions[1][0].description, textPosition.x, textPosition.y, 170, 1, true);
		
		main.batch.draw(powerDownOptions[1][1].icon, option1Bad1Position.x + 32, option1Bad1Position.y - 106 - 32/3, 106, 106);
		main.game.font.getData().setScale(0.2f);
		textPosition = new Vector3(option1Bad1Position.x, option1Bad1Position.y - 120, 0);
		main.game.font.draw(main.batch, powerDownOptions[1][1].description, textPosition.x, textPosition.y, 170, 1, true);
		
		main.batch.end();
	}
}
