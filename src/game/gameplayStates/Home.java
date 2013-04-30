package game.gameplayStates;

import game.Dialogue;
import game.Enemy;
import game.GameObject;
import game.PauseMenu;
import game.StateManager;
import game.StaticObject;
import game.interactables.Bed;
import game.interactables.Chest;
import game.interactables.ChickenWing;
import game.interactables.Cigarette;
import game.interactables.Door;
import game.interactables.Interactable;
import game.interactables.PortalObject;
import game.player.Player;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Home extends GamePlayState {

	public Home(int stateID) {
		m_stateID = stateID;
	}
	
	
	@Override
	public void additionalInit(GameContainer container, StateBasedGame stateManager)
			throws SlickException {
		// set player initial location
		m_playerX = SIZE*2;
		m_playerY = SIZE*4;
		
		// set up map
		m_map = new simpleMap();
		if(m_mapPath != null) {
			m_tiledMap = new TiledMap(m_mapPath);
		}
		else
			try {
				m_tiledMap = new TiledMap("assets/maps/home.tmx");
			} catch (SlickException e) {
				System.out.println("ERROR: Could not load home.tmx");
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

		// set up objects that will not change regardless of the game state
		if(!this.isLoaded()) {
			m_interactables = new HashMap<Integer, Interactable>();
			m_objects = new HashMap<Integer, GameObject>();
			m_dialogue = new ArrayList<Dialogue>(); // think about whether this needs to be a hashmap instead
				
			StaticObject posters = 
				new StaticObject(3*SIZE, 1*SIZE, "assets/gameObjects/posters.png");
			m_objects.put(31, posters);
			
			StaticObject carpet = 
				new StaticObject(3*SIZE, 3*SIZE, "assets/gameObjects/carpet.png");
			m_objects.put(23, carpet);
			
			Bed bed = new Bed(35, 3*SIZE, 5*SIZE, StateManager.TOWN_DAY_STATE, 0, 0);
			m_interactables.put(35, bed);
			m_blocked[3][5] = true;
			m_blocked[4][5] = true;
			m_objects.put(35, bed);
			
			StaticObject bedTable = 
				new StaticObject(4*SIZE, 4*SIZE, "assets/gameObjects/bedTable.png");
			m_blocked[4][4] = true;
			m_objects.put(44, bedTable);
			
			StaticObject table =
				new StaticObject(SIZE, 4*SIZE, "assets/gameObjects/table.png");
			m_interactables.put(14, table);
			m_blocked[1][4] = true;
			m_blocked[1][5] = true;
			m_objects.put(14, table);
		
		}
		

	}
	
	@Override
	public void setupObjects(int city, int dream) throws SlickException {
		if (city == 3 && dream == 3) {
			super.removeObject(22);
			StaticObject door = new StaticObject(2*SIZE, 2*SIZE, "assets/gameObjects/door.png");
			m_objects.put(22, door);
		}
		else if (city == 3 && dream == 2) {
			super.removeObject(22);
			PortalObject door = new Door(22, 2*SIZE, 2*SIZE, StateManager.TOWN_DAY_STATE, -1, -1);
			m_interactables.put(22, door);
			m_objects.put(22, door);
		}
	}
	
	@Override
	public void setupDialogue(GameContainer container, int city, int dream) {
		if (city == 3 && dream == 3) {
			Dialogue computerDialogue = new Dialogue(this, container, new String[]
					{"1. This your macbook, a safe place to visit your collection of non-moving horses.",
					"You can also visit find plenty of friends right here on the internet.. special friends."});
			m_dialogue.add(computerDialogue);
		}
		else if (city == 3 && dream == 2) {
			Dialogue computerDialogue = new Dialogue(this, container, new String[]
					{"2. Woah... that horse is indeed better than a boy.", "maybe i'll buy one"});
			m_dialogue.add(computerDialogue);
		}
	}



	@Override
	public void dialogueListener(Interactable i) {
		// computer: key = 14
		if (i.getSquare()[0] == m_interactables.get(14).getSquare()[0] && 
			i.getSquare()[1] == m_interactables.get(14).getSquare()[1]) { 
			m_dialogueNum = 0;
			m_inDialogue = true;
		}
	}

}
