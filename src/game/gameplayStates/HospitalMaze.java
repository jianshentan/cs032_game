package game.gameplayStates;

import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;

import game.Person;
import game.StateManager;
import game.collectables.Pill;
import game.interactables.Door;
import game.interactables.Interactable;
import game.interactables.PortalObject;
import game.popup.MainFrame;
import game.quests.Quest;
import game.quests.QuestGoal;
import game.quests.QuestReward;
import game.quests.QuestStage;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class HospitalMaze extends GamePlayState {
	
	private ArrayList<MainFrame> m_frames;
	private java.awt.Dimension m_screenSize;

	public HospitalMaze(int stateID) {
		m_stateID = stateID;
	}

	public void makePillFrame(String name) {
		String path = "";
		if(name.compareTo("alprazolam") == 0)
			path = "assets/pillOnHorse/h01.png";
		else if (name.compareTo("citalopram") == 0)
			path = "assets/pillOnHorse/h02.png";
		else if (name.compareTo("sertraline") == 0)
			path = "assets/pillOnHorse/h03.png";
		else if (name.compareTo("lorazepam") == 0)
			path = "assets/pillOnHorse/h04.png";
		else if (name.compareTo("fluoxetine HCL") == 0)
			path = "assets/pillOnHorse/h05.png";
		else if (name.compareTo("escitalopram") == 0)
			path = "assets/pillOnHorse/h06.png";
		else if (name.compareTo("trazodone HCL") == 0)
			path = "assets/pillOnHorse/h07.png";
		else if (name.compareTo("duloxetine") == 0)
			path = "assets/pillOnHorse/h08.png";
		else 
			System.out.println("ERROR: could not get pill");
		makeWindow(path);
	}
	
	class WindowThread implements Runnable {
		private String m_path;
		public void setPath(String path) {
			m_path = path;
		}
		public void run() {
			try {
				int randX = (int)(Math.random()*(m_screenSize.getWidth()-192));
				int randY = (int)(Math.random()*(m_screenSize.getHeight()-192));
				int randHorse = (int)(Math.random()*5)+1;
				MainFrame frame = new MainFrame(randX, randY, 192, 192, m_path);
				m_frames.add(frame);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void makeWindow(String path) {
		WindowThread thread = new WindowThread();
		thread.setPath(path);
		thread.run();
	}
	
	
	@Override
	public void additionalEnter(GameContainer container, StateBasedGame stateManager) {
		m_frames = new ArrayList<MainFrame>();
		m_screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		AppGameContainer gc = (AppGameContainer) container;
		try {
			gc.setDisplayMode(192, 192, false);
			m_camera.refreshCamera(gc, m_player);
			m_player.getInventory().setMini(true);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void additionalInit(GameContainer container, StateBasedGame stageManager) 
		throws SlickException {
		
		m_playerX = 9*SIZE;
		m_playerY = 18*SIZE;
		
		this.m_tiledMap = new TiledMap("assets/maps/hospitalMaze.tmx");
		this.m_map = new simpleMap();
		this.setBlockedTiles();
		
		if (!this.isLoaded()) {
			PortalObject door = new Door("hospitalMazeExit", 10*SIZE, 0*SIZE, StateManager.HOSPITAL_BASE_STATE, -1, -1);
			addObject(door, true);
		}
	}
	
	@Override
	public void setupObjects(int city, int dream) throws SlickException {
		Pill alprazolam = new Pill("alprazolam", "assets/pills/p01.png", 18*SIZE, 17*SIZE);
		addObject(alprazolam, true);
		
		Pill citalopram = new Pill("citalopram", "assets/pills/p02.png", 15*SIZE, 9*SIZE);
		addObject(citalopram, true);
		
		Pill sertraline = new Pill("sertraline", "assets/pills/p03.png", 18*SIZE, 5*SIZE);
		addObject(sertraline, true);
		
		Pill lorazepam = new Pill("lorazepam", "assets/pills/p04.png", 16*SIZE, 11*SIZE);
		addObject(lorazepam, true);
		
		Pill fluoxetine_HCL = new Pill("fluoxetine HCL", "assets/pills/p05.png", 9*SIZE, 12*SIZE);
		addObject(fluoxetine_HCL, true);
		
		Pill escitalopram = new Pill("escitalopram", "assets/pills/p06.png", 2*SIZE, 18*SIZE);
		addObject(escitalopram, true);
		
		Pill trazodone_HCL = new Pill("trazodone HCL", "assets/pills/p07.png", 1*SIZE, 12*SIZE);
		addObject(trazodone_HCL, true);
		
		Pill duloxetine = new Pill("duloxetine", "assets/pills/p08.png", 4*SIZE, 5*SIZE);
		addObject(duloxetine, true);
		
		//setting up quest
		Quest quest3 = new Quest("quest3");
		QuestStage stage1 = new QuestStage();
		stage1.addGoal(new QuestGoal.Quest3Goal());
		stage1.setReward(new QuestReward.Quest3Reward());
		quest3.addStage(stage1);
		m_player.addQuest(quest3);
		quest3.startQuest(this);
	}
	
	/**
	 * On leaving, deactivate mini inventory
	 */
	@Override
	public void additionalLeave(GameContainer container, StateBasedGame stateManager) {
		m_player.getInventory().setMini(false);
	}

	@Override
	public void setupDialogue(GameContainer container, int city, int dream)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	@Deprecated
	public void dialogueListener(Interactable i) {
		// TODO Auto-generated method stub

	}

}
