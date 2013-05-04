package game.gameplayStates;

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

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class DolphinEntrance extends GamePlayState {

	private boolean m_isHorsesShown = false;
	private ArrayList<Runnable> m_threads;
	private int m_i;
	
	public DolphinEntrance(int id) {
		m_stateID = id;
	}
	
	public void additionalEnter(GameContainer container, StateBasedGame stateManager) {
		if (!m_isHorsesShown) {
			m_isHorsesShown = true;
			// pop up!
			for (m_i=0; m_i<100; m_i++) {
				Runnable thread = new Runnable() {
					public void run() {
						try {
							int randX = (int)(Math.random()*2260);
							int randY = (int)(Math.random()*1300);
							int randHorse = (int)(Math.random()*5)+1;
							MainFrame frame = new MainFrame(randX, randY, 256, 256, 
									"assets/popupHorses/popup_horse0"+randHorse+".png");
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
