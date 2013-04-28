package game;

import game.io.LoadGame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class StateManager extends StateBasedGame {

	public static final int MAINMENU_STATE = 0;
	public static final int ROOM_STATE = 1;
	private AppGameContainer m_app;
	private LoadGame m_loader;
	
	public StateManager() {
		super("chicken salad and cucumber on croissant");
		
		try {
			m_app = new AppGameContainer(this);
			setup();
			m_app.setDisplayMode(600, 600, false);
			//app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public StateManager(LoadGame l) {
		super("chicken salad and cucumber on croissant");
		this.m_loader = l;
		try {
			m_app = new AppGameContainer(this);
			setup();
			m_app.setDisplayMode(600, 600, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public GameContainer getGameContainer() {
		return this.m_app;
	}
	
	public void setup() {
		addState(new Room(ROOM_STATE));
		addState(new MainMenu(MAINMENU_STATE));
		enterState(MAINMENU_STATE);
	}
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		getState(MAINMENU_STATE).init(container, this);
		getState(ROOM_STATE).init(container, this);
		if(this.m_loader==null) {
			
		} else {
			try {
				m_loader.load(this);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void run() {
		try {
			m_app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
