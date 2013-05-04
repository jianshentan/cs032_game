package game.gameplayStates;

import java.util.HashMap;

import game.Dialogue;
import game.GameObject;
import game.StateManager;
import game.StaticObject;
import game.gameplayStates.GamePlayState.simpleMap;
import game.interactables.Interactable;
import game.interactables.InvisiblePortal;
import game.interactables.VisiblePortal;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class VirtualRealityRoom extends GamePlayState {

	public VirtualRealityRoom(int stateID) {
		m_stateID = stateID;
	}
	
	@Override
	public void additionalInit(GameContainer container, StateBasedGame stateManager)
		throws SlickException {
		
		m_playerX = SIZE*3;
		m_playerY = SIZE*4; 
		
		this.m_tiledMap = new TiledMap("assets/maps/virtualRealityRoom.tmx");
		this.m_map = new simpleMap();
		this.setBlockedTiles();
		
		if (!this.isLoaded()) {
			m_interactables = new HashMap<Integer, Interactable>();
			m_objects = new HashMap<Integer, GameObject>();
			m_dialogue = new HashMap<Integer, Dialogue>(); // think about whether this needs to be a hashmap instead
				
			InvisiblePortal portal = new InvisiblePortal(35, 3*SIZE, 5*SIZE, StateManager.TOWN_DAY_STATE, 22, 18);
			m_objects.put(35, portal);
			m_interactables.put(35, portal);
			StaticObject doorMat = new StaticObject(3*SIZE, 4*SIZE, "assets/gameObjects/doormat.png");
			m_objects.put(34, doorMat);
			
			// load up some chairs!
			StaticObject VRCperson1 = new StaticObject(2*SIZE, SIZE-20, "assets/gameObjects/VRCperson01.png");
			m_objects.put(21, VRCperson1);
			StaticObject VRCperson2 = new StaticObject(4*SIZE, SIZE-20, "assets/gameObjects/VRCperson02.png");
			m_objects.put(41, VRCperson2);
			StaticObject VRCperson3 = new StaticObject(8*SIZE, SIZE-20, "assets/gameObjects/VRCperson03.png");
			m_objects.put(81, VRCperson3);
			
			VisiblePortal VRC = new VisiblePortal(61, 6*SIZE, SIZE, 
					StateManager.HOME_STATE, -1, -1, "assets/gameObjects/virtualRealityChair.png");
			m_objects.put(61, VRC);
			m_interactables.put(61, VRC);
			m_blocked[6][1] = true;
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
