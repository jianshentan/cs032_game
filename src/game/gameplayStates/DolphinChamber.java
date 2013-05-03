package game.gameplayStates;

import game.Scene;
import game.gameplayStates.GamePlayState.simpleMap;
import game.interactables.Interactable;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class DolphinChamber extends GamePlayState {
	
	private boolean m_waterDown;
	/**
	 * Turns down the water.
	 */
	public void waterDown(boolean b) {
		this.m_waterDown = b;
	}
	
	public DolphinChamber(int id) {
		m_stateID = id;
	}
	
	@Override
	public void additionalInit(GameContainer container, StateBasedGame stateManager) throws SlickException {
		this.m_tiledMap = new TiledMap("assets/maps/dolphinMap2.tmx");
		this.m_map = new simpleMap();
		m_playerX = SIZE*3;
		m_playerY = SIZE*13;
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
	
	public void additionalEnter(GameContainer container, StateBasedGame stateManager) {
		if(this.isEntered()==false) {
			Scene s = new Scene(this, m_player, new int[][] {{3,13},{3,4},{3,13}});
			s.setPlayerInvisible(true);
			s.playScene();
			
			//this.displayDialogue(new String[] {"You see a dolphin in the distance."});
		}
		if(m_waterDown == true) {
			try {
				this.m_tiledMap = new TiledMap("assets/maps/dolphinMap1.tmx");
			} catch (SlickException e) {
				e.printStackTrace();
			}
			this.m_map = new simpleMap();
			m_playerX = SIZE*3;
			m_playerY = SIZE*13;
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
		}
		
	}

}
