package game;

import java.io.IOException;

import game.gameplayStates.Home;
import game.gameplayStates.Kitchen;
import game.gameplayStates.Room;
import game.io.LoadGame;
import game.player.Player;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class StateManager extends StateBasedGame {
	
	private static final StateManager instance;
	  
	static {
	    instance = new StateManager();
	}
	 
	public static StateManager getInstance() {
		return instance;
	}
	  
	// black box test states
	public static boolean m_debugMode = false;
	public static final int KITCHEN_STATE = 1002;
	public static final int ROOM_STATE = 1001;
	
	public static final int HOME_STATE = 1;
	public static final int MAINMENU_STATE = 0;
	private AppGameContainer m_app;
	private LoadGame m_loader;
	private Player m_player;
	
	private StateManager() {
		super("chicken salad and cucumber on croissant");
		
		try {
			m_app = new AppGameContainer(this);
			m_app.setDisplayMode(600, 600, false);
			//app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	private StateManager(LoadGame l) {
		super("chicken salad and cucumber on croissant");
		this.m_loader = l;
		try {
			m_app = new AppGameContainer(this);
			m_app.setDisplayMode(600, 600, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setLoader(LoadGame l) {
		this.m_loader = l;
	}
	
	public GameContainer getGameContainer() {
		return this.m_app;
	}
	
	public void setup() throws SlickException {
		
		// Test states
		if (m_debugMode) {
			Room room = new Room(ROOM_STATE);
			addState(room);
			Kitchen kitchen = new Kitchen(KITCHEN_STATE);
			addState(kitchen);
			
			// give player to gameplaystates
			m_player = new Player(room, getGameContainer(), 0, 0);
			room.setPlayer(m_player);
			kitchen.setPlayer(m_player);
		}
		else {
			Home home = new Home(HOME_STATE);
			addState(home);
			m_player = new Player(home, getGameContainer(), 0, 0);
			home.setPlayer(m_player);
		}
		
		// start!
		addState(new MainMenu(MAINMENU_STATE));
		enterState(MAINMENU_STATE);
	}
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		setup();
		
		// setup states
		getState(MAINMENU_STATE).init(container, this);
		if (m_debugMode) {
			getState(KITCHEN_STATE).init(container, this);
			getState(ROOM_STATE).init(container, this);
		}
		else {
			getState(HOME_STATE).init(container, this);
		}
		
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
