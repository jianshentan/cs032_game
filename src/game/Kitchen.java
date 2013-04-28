package game;

import game.GamePlayState.simpleMap;

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
	public void init(GameContainer container, StateBasedGame stateManager)
			throws SlickException {
		// setup player
		m_player = new Player(this, container, SIZE, 0);
		m_map = new simpleMap();
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
			m_interactables = new HashMap<Integer, Interactable>();
			m_objects = new HashMap<Integer, GameObject>();
		}
	}

}
