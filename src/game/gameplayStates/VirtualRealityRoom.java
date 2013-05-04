package game.gameplayStates;

import java.util.HashMap;

import game.Dialogue;
import game.GameObject;
import game.Person;
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
	
	private String[] GUIDE_TEXT = new String[] {"Welcome to the virtual reality play house!",
			"We provide a great virtual reality service that finds you your personal dream place",
			"A guaranteed awesome experience.",
			"Lucky you! Looks like we have one more spot left. Hop on in and let the chair bring you to your happy place"};

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
			//m_interactables = new HashMap<Integer, Interactable>();
			//m_objects = new HashMap<Integer, GameObject>();
			m_dialogue = new HashMap<Integer, Dialogue>(); // think about whether this needs to be a hashmap instead
				
			InvisiblePortal portal = new InvisiblePortal("portal", 3*SIZE, 5*SIZE, StateManager.TOWN_DAY_STATE, 22, 18);
			addObject(portal, true);
			StaticObject doorMat = new StaticObject("doorMat", 3*SIZE, 4*SIZE, "assets/gameObjects/doormat.png");
			addObject(doorMat, false);
			
			// load up some chairs!
			StaticObject VRCperson1 = new StaticObject("VRCperson1", 2*SIZE, SIZE-20, "assets/gameObjects/VRCperson01.png");
			VRCperson1.setDialogue(new String[] {"This person is clearly out of it."});
			this.addObject(VRCperson1, true);
			m_blocked[2][1] = true;

			StaticObject VRCperson2 = new StaticObject("VRCperson2", 4*SIZE, SIZE-20, "assets/gameObjects/VRCperson02.png");
			VRCperson2.setDialogue(new String[] {"This person is clearly out of it."});
			this.addObject(VRCperson2, true);
			m_blocked[4][1] = true;

			StaticObject VRCperson3 = new StaticObject("VRCperson3", 8*SIZE, SIZE-20, "assets/gameObjects/VRCperson03.png");
			VRCperson3.setDialogue(new String[] {"This person is clearly out of it."});
			this.addObject(VRCperson3, true);
			m_blocked[8][1] = true;
			
			VisiblePortal VRC = new VisiblePortal("VRC", 6*SIZE, SIZE, 
					StateManager.VIRTUAL_REALITY_HOME_STATE, -1, -1, "assets/gameObjects/virtualRealityChair.png");
			addObject(VRC, true);
			m_blocked[6][1] = true;
			
			//
			Person guide = new Person("guide", 1*SIZE, 2*SIZE, "assets/characters/human_4.png", null);
			guide.setDialogue(GUIDE_TEXT);
			this.addObject(guide, true);
			m_blocked[1][2] = true;
		}
	}
	
	@Override
	public void additionalEnter(GameContainer container, StateBasedGame stateManager) {
		if(isEntered() == false) { //TODO: conditions also involve global city/dream state
			this.displayDialogue(new String[] {"\"We've been waiting for you\", you hear a booming voice say.",
					"\"Your chair is the third from the left.\""});
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
