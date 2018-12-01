package app.ajay.ld43.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import app.ajay.ld43.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = 1400;
		config.height = 900;
		
		config.title = "Ludum Dare 43";
		
		//antialiasing
		config.samples=3;
		
		new LwjglApplication(new Main(), config);
	}
}
