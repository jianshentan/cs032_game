package game.gameplayStates;

import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

import game.GameObject;
import game.Spectre;
import game.StateManager;
import game.interactables.Interactable;
import game.interactables.InvisiblePortal;
import game.interactables.PortalObject;

public class TownNight extends GamePlayState {

	public TownNight(int stateID) {
		m_stateID = stateID;
	}
	
	@Override
	public void additionalInit(GameContainer container, StateBasedGame stateManager) throws SlickException {
		m_playerX = SIZE*11;
		m_playerY = SIZE*28;
		//add ghost
		
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
			
			//PortalObject doorMat = new InvisiblePortal(1129, 11*SIZE, 29*SIZE, StateManager.HOME_STATE, 2*SIZE, 3*SIZE);
			//m_interactables.put(1129, doorMat);
			//m_objects.put(1129, doorMat);
			
		}
	}

	@Override
	public void dialogueListener(Interactable i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setupObjects(int city, int dream) throws SlickException {
		//setup enemies
		if(dream==3) {
			int[][] leadPoints = {{8,14},{8,14},{8,14},{8,14}};
			Spectre spec = new Spectre(this, m_player, SIZE*10, SIZE*26, leadPoints);
			m_enemies.add(spec);
		}
		
	}

	@Override
	public void setupDialogue(GameContainer container, int city, int dream) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void stateEnd(int endCode) {
		StateManager.m_dreamState -= 1;
		StateManager.getInstance().enterState(StateManager.HOME_STATE, 
				new FadeOutTransition(Color.black, 1000), 
				new FadeInTransition(Color.black, 1000));
	}

}
