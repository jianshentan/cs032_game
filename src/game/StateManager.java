package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class StateManager extends StateBasedGame {

	public static final int MAINMENU_STATE = 0;
	public static final int ROOM_STATE = 1; 
	
	public StateManager() {
		super("chicken salad and cucumber on croissant");
		
		try {
			AppGameContainer app = new AppGameContainer(this);
			setup();
			app.setDisplayMode(600, 600, false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void setup() {
		addState(new Room(ROOM_STATE));
		addState(new MainMenu(MAINMENU_STATE));
		enterState(MAINMENU_STATE);
	}
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		getState(ROOM_STATE).init(container, this);
		getState(MAINMENU_STATE).init(container, this);
	}

}
