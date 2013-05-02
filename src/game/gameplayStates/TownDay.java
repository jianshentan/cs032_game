package game.gameplayStates;

import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import game.Dialogue;
import game.GameObject;
import game.Person;
import game.StateManager;
import game.interactables.Door;
import game.interactables.InvisiblePortal;
import game.interactables.Interactable;
import game.interactables.PortalObject;

public class TownDay extends GamePlayState {

	public TownDay(int stateID) {
		m_stateID = stateID;
	}
	
	@Override
	public void additionalInit(GameContainer container, StateBasedGame stateManager) throws SlickException {
		m_playerX = SIZE*11;
		m_playerY = SIZE*28;
		
		
		if(m_mapPath != null) {
			m_tiledMap = new TiledMap(m_mapPath);
		}
		else
			try {
				m_tiledMap = new TiledMap("assets/maps/townDay.tmx");
			} catch (SlickException e) {
				System.out.println("ERROR: Could not townDay.tmx");
			}
		m_map = new simpleMap();
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
		
		if (!this.isLoaded()) {
			// setup objects
			m_interactables = new HashMap<Integer, Interactable>();
			m_objects = new HashMap<Integer, GameObject>();	
			
			PortalObject doorMat = new InvisiblePortal(1129, 11*SIZE, 29*SIZE, StateManager.HOME_STATE, 2*SIZE, 3*SIZE);
			m_interactables.put(1129, doorMat);
			m_objects.put(1129, doorMat);
			
			Person person_1 = new Person(1127, 11*SIZE, 27*SIZE, "assets/characters/Sprite3_all.png");
			m_interactables.put(1127, person_1);
			m_objects.put(1127, person_1); 
			m_blocked[11][27] = true;
			
		}
		m_dialogue = new HashMap<Integer, Dialogue>();
		Dialogue person_1_dialogue = new Dialogue(this, container, new String[] 
				{"can you help me find my cats? there are 5 of them."});

		m_dialogue.put(1127, person_1_dialogue);
	}

	@Override
	public void dialogueListener(Interactable i) {
		// person_1: key = 1127
		if (m_interactables.containsKey(1127) && m_dialogue.containsKey(1127)) 
			if (i.getSquare()[0] == m_interactables.get(1127).getSquare()[0] && 
				i.getSquare()[1] == m_interactables.get(1127).getSquare()[1]) { 
				m_dialogueNum = 1127;
				m_inDialogue = true;
			}
	}

	@Override
	public void setupObjects(int city, int dream) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setupDialogue(GameContainer container, int city, int dream) {
		// TODO Auto-generated method stub
		
	}

}
