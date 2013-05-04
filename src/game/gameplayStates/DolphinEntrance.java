package game.gameplayStates;

import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import game.Dialogue;
import game.GameObject;
import game.StateManager;
import game.StaticObject;
import game.gameplayStates.GamePlayState.simpleMap;
import game.interactables.Interactable;
import game.interactables.InvisiblePortal;
import game.popup.MainFrame;

import org.lwjgl.util.Dimension;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class DolphinEntrance extends GamePlayState {

	private boolean m_isHorsesShown = false, m_freed = false;
	private ArrayList<Runnable> m_threads;
	private int m_i;
	private ArrayList<MainFrame> m_frames;
	public DolphinEntrance(int id) {
		m_frames = new ArrayList<MainFrame>();
		m_stateID = id;
	}
	
	public void additionalEnter(GameContainer container, StateBasedGame stateManager) {
		if (m_freed){
			m_freed = false;
			TownDay td = (TownDay) stateManager.getState(StateManager.TOWN_DAY_STATE);
			td.setFree();
			Runnable freeHorsesThread = new Runnable() {
				public void run() {
					freeHorses();
				}
			};
			freeHorsesThread.run();
			String[] di2 = {"I guess blowing up that dolphin freed the horses from it's control", "Or something"};
			this.displayDialogue(di2);
		}
		if (!m_isHorsesShown) {
			this.displayDialogue(new String[] {"You realize that you are in the zoo.",
					"Or rather, what used to be the zoo before it was converted " +
					"into a horse repository.",
					"Maybe if you free the horses, this place will be closed down for good..."});
			m_isHorsesShown = true;
			// pop up!
			final java.awt.Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
			for (m_i=0; m_i<20; m_i++) {
				Runnable thread = new Runnable() {
					public void run() {
						try {
							int randX = (int)(Math.random()*(ScreenSize.getWidth()-300));
							int randY = (int)(Math.random()*(ScreenSize.getHeight()-300));
							int randHorse = (int)(Math.random()*5)+1;
							MainFrame frame = new MainFrame(randX, randY, 256, 256, 
									"assets/popupHorses/popup_horse0"+randHorse+".png");
							m_frames.add(frame);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				};
				m_threads.add(thread);
				thread.run();
			}

		}
	}
	
	@Override
	public void additionalInit(GameContainer container, StateBasedGame stateManager) throws SlickException {
		this.m_tiledMap = new TiledMap("assets/maps/dolphinEntrance.tmx");
		this.m_map = new simpleMap();
		m_playerX = SIZE*2;
		m_playerY = SIZE*8;
		m_blocked = new boolean[m_tiledMap.getWidth()][m_tiledMap.getHeight()];
		for (int xAxis=0; xAxis<m_tiledMap.getWidth(); xAxis++) {
			for (int yAxis=0; yAxis<m_tiledMap.getHeight(); yAxis++) {
				int tileID = m_tiledMap.getTileId(xAxis, yAxis, 0);
				String value = m_tiledMap.getTileProperty(tileID, "blocked", "false");
				if ("true".equals(value)) {
					m_blocked[xAxis][yAxis] = true;
				}
			}
		}
		
		m_threads = new ArrayList<Runnable>();
		
		if(!this.isLoaded()) {
			m_interactables = new HashMap<Integer, Interactable>();
			m_objects = new HashMap<Integer, GameObject>();
			m_dialogue = new HashMap<Integer, Dialogue>(); // think about whether this needs to be a hashmap instead
			
			StaticObject entrance = new StaticObject(SIZE, 0, "assets/dolphinProtectors.png");
			entrance.setRenderPriority(true);
			m_objects.put(10, entrance);
			
			
			InvisiblePortal portalA = new InvisiblePortal(21, 2*SIZE, SIZE, StateManager.DOLPHIN_STATE, -1,-1);
			InvisiblePortal portalB = new InvisiblePortal(31, 3*SIZE, SIZE, StateManager.DOLPHIN_STATE, -1,-1);
			m_objects.put(21, portalA);
			m_objects.put(31, portalB);
			m_interactables.put(21,  portalA);
			m_interactables.put(31,  portalB);
			
			StaticObject doormat = new StaticObject(2*SIZE, 8*SIZE, "assets/gameObjects/doormat.png");
			m_objects.put(28, doormat);
			InvisiblePortal portalC = new InvisiblePortal(28, 2*SIZE, 9*SIZE, StateManager.TOWN_DAY_STATE, 8, 14);
			m_objects.put(29, portalC);
			m_interactables.put(29, portalC);
		}
	}
	public void setFree(){
		m_freed = true;
	}
	public void freeHorses(){
		long time = System.currentTimeMillis();
		for(MainFrame frame: m_frames){
			frame.toFront();
		}
		for(MainFrame frame: m_frames){
			while(System.currentTimeMillis()-time<500){
				
			}
			time = System.currentTimeMillis();
			frame.setVisible(false);
		}
		
	}
	@Override
	public void setupObjects(int city, int dream) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setupDialogue(GameContainer container, int city, int dream)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void dialogueListener(Interactable i) {
		// TODO Auto-generated method stub

	}

}
