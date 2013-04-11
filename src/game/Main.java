package game;


import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {

	public static void main(String[] args) {
		Room room = new Room();
		try {
			AppGameContainer app = new AppGameContainer(room);
			app.setDisplayMode(600, 500, false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
