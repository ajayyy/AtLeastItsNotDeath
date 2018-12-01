package app.ajay.ld43;

import java.awt.Font;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;

public class RevivalScreen {
	float x, y;
	
	GlyphLayout layout;
	
	public RevivalScreen() {
		layout = new GlyphLayout();
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
		Vector3 shapePosition = main.cam.unproject(new Vector3(100, 200, 0));
		main.shapeRenderer.setColor(Color.GRAY);
		main.shapeRenderer.rect(shapePosition.x, shapePosition.y, 400, -400);
		
		//option 0, bad 0
		shapePosition = main.cam.unproject(new Vector3(100 + 20, 620, 0));
		main.shapeRenderer.setColor(Color.GRAY);
		main.shapeRenderer.rect(shapePosition.x, shapePosition.y, 170, -170);
		
		//option 0, bad 1
		shapePosition = main.cam.unproject(new Vector3(100 + 170 + 20*2, 620, 0));
		main.shapeRenderer.setColor(Color.GRAY);
		main.shapeRenderer.rect(shapePosition.x, shapePosition.y, 170, -170);
		
		
		//option 1, good
		shapePosition = main.cam.unproject(new Vector3(Gdx.graphics.getWidth() - 100, 200, 0));
		main.shapeRenderer.setColor(Color.GRAY);
		main.shapeRenderer.rect(shapePosition.x, shapePosition.y, -400, -400);
		
		//option 0, bad 0
		shapePosition = main.cam.unproject(new Vector3(Gdx.graphics.getWidth() - (100 + 20), 620, 0));
		main.shapeRenderer.setColor(Color.GRAY);
		main.shapeRenderer.rect(shapePosition.x, shapePosition.y, -170, -170);
		
		//option 0, bad 1
		shapePosition = main.cam.unproject(new Vector3(Gdx.graphics.getWidth() - (100 + 170 + 20*2), 620, 0));
		main.shapeRenderer.setColor(Color.GRAY);
		main.shapeRenderer.rect(shapePosition.x, shapePosition.y, -170, -170);
		
		main.shapeRenderer.end();
	}
}
