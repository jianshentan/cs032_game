package game;

import game.GamePlayState.simpleMap;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Kitchen extends GamePlayState {

	private int m_playerX = SIZE*2;
	private int m_playerY = SIZE*1;
	
	public Kitchen(int stateID) {
		m_stateID = stateID;
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame stateManager) throws SlickException {
		m_player.setX(m_playerX);
		m_player.setY(m_playerY);
		m_player.setGame(this);
	}
	
	@Override
	public void additionalInit(GameContainer container, StateBasedGame stateManager)
			throws SlickException {
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
			
			Door door = new Door(20, 2*SIZE, 0*SIZE, StateManager.ROOM_STATE);
			m_interactables.put(20, door);
			m_objects.put(20, door);
		}
	}

}
