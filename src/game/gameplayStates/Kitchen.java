package game.gameplayStates;

import game.GameObject;
import game.Spectre;
import game.StateManager;
import game.gameplayStates.GamePlayState.simpleMap;
import game.interactables.Door;
import game.interactables.Interactable;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Kitchen extends GamePlayState {

	public Kitchen(int stateID) {
		m_stateID = stateID;
	}
	
	@Override
	public void additionalInit(GameContainer container, StateBasedGame stateManager)
			throws SlickException {
		m_playerX = SIZE*2;
		m_playerY = SIZE*1;		
		
		//m_viewport = new Rectangle(0,0, container.getWidth(), container.getHeight());
		if(m_mapPath != null) {
			m_tiledMap = new TiledMap(m_mapPath);
		}
		else
			try {
				m_tiledMap = new TiledMap("assets/maps/kitchen.tmx");
			} catch (SlickException e) {
				System.out.println("ERROR: Could not load kitchen.tmx");
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
		
		if(!this.isLoaded()) {
			// setup objects
			//m_interactables = new HashMap<String, Interactable>();
			//m_objects = new HashMap<String, GameObject>();
			
			Door door = new Door("door", 2*SIZE, 0*SIZE, StateManager.ROOM_STATE, 6, 8);
			this.addObject(door, true);
		}
	}

	@Override
	public void dialogueListener(Interactable i) {
		// no dialogue here
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
