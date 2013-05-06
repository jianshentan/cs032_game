package game.gameplayStates;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import game.StateManager;
import game.interactables.Door;
import game.interactables.Interactable;
import game.interactables.PortalObject;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class HospitalMaze extends GamePlayState {

	public HospitalMaze(int stateID) {
		m_stateID = stateID;
	}

	
	@Override
	public void additionalEnter(GameContainer container, StateBasedGame stateManager) {
		AppGameContainer gc = (AppGameContainer) container;
		try {
			gc.setDisplayMode(192, 192, false);
			m_camera.refreshCamera(gc, m_player);
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
			PortalObject door = new Door("hospitalMazeExit", 10*SIZE, 0*SIZE, StateManager.TOWN_DAY_STATE, -1, -1);
			addObject(door, true);
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
	@Deprecated
	public void dialogueListener(Interactable i) {
		// TODO Auto-generated method stub

	}

}
