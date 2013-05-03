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
			
			Person person_1 = new Person(1127, 11*SIZE, 27*SIZE, "assets/characters/human_2.png");
			m_interactables.put(1127, person_1);
			m_objects.put(1127, person_1); 
			m_blocked[11][27] = true;
			
			Person person_2 = new Person(1625, 16*SIZE, 25*SIZE, "assets/characters/human_3.png");
			m_interactables.put(1625, person_2);
			m_objects.put(1625, person_2);
			m_blocked[16][25] = true;
		}
		m_dialogue = new HashMap<Integer, Dialogue>();
		Dialogue person_1_dialogue = new Dialogue(this, container, new String[] 
				{"can you help me find my cats? there are 5 of them."}, new String[]
						{"", "yes", "no"});

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
		int[] dialoguePos;
		m_dialogue.clear();
		if (city == 3 && dream == 3) {
			Dialogue doorDialogue = new Dialogue(this, container, new String[] 
					{"Its late out... Perhaps you should just hit the sack"}, null);
			dialoguePos = new int[] {2, 2};
			m_dialogue.put(positionToKey(dialoguePos), doorDialogue);
			
			Dialogue computerDialogue = new Dialogue(this, container, new String[]
					{"1. This your macbook, a safe place to visit your collection of non-moving horses.",
					"You can also visit find plenty of friends right here on the internet.. special friends."},
					new String[] {"do you like cats or dogs", "cats", "dogs", "mouse"});
			dialoguePos = new int[] {1, 4};
			m_dialogue.put(positionToKey(dialoguePos), computerDialogue);
		}
		else if (city == 3 && dream == 2) {
			Dialogue computerDialogue = new Dialogue(this, container, new String[]
					{"2. Woah... that horse is indeed better than a boy.", "maybe i'll buy one"}, null);
			dialoguePos = new int[] {1, 4};
			m_dialogue.put(positionToKey(dialoguePos), computerDialogue);
		}
	}

}
