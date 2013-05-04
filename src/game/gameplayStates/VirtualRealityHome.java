package game.gameplayStates;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import game.Dialogue;
import game.GameObject;
import game.StateManager;
import game.StaticObject;
import game.interactables.Bed;
import game.interactables.Interactable;
import game.interactables.Interactables;
import game.interactables.InvisiblePortal;
import game.interactables.TableToHack;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class VirtualRealityHome extends GamePlayState {


	public VirtualRealityHome(int stateID) {
		m_stateID = stateID;
	}
	
	@Override
	public void additionalEnter(GameContainer container, StateBasedGame stateManager) {
		this.displayDialogue(new String[] {"Wait... this is my room.", 
				"Why was I brought here? Why is justin beiber on my poster?",
				"... This can't be my room"});
	}
	
	@Override
	public void additionalInit(GameContainer container, StateBasedGame stateManager)
		throws SlickException {
		
		
		// set player initial location
		m_playerX = SIZE*2;
		m_playerY = SIZE*4;
		
		// set up map
		this.m_tiledMap = new TiledMap("assets/maps/home.tmx");
		this.m_map = new simpleMap();
		this.setBlockedTiles();
		
		//set up objects that will not change
		if (!this.isLoaded()) {
			m_interactables = new HashMap<Integer, Interactable>();
			m_objects = new HashMap<Integer, GameObject>();
			m_dialogue = new HashMap<Integer, Dialogue>();
			
			StaticObject posters = 
				new StaticObject(3*SIZE, 1*SIZE, "assets/gameObjects/bieberPoster.png");
			m_objects.put(31, posters);
			
			StaticObject carpet = 
				new StaticObject(3*SIZE, 3*SIZE, "assets/gameObjects/carpet.png");
			m_objects.put(23, carpet);
			
			StaticObject bedTable = 
				new StaticObject(4*SIZE, 4*SIZE, "assets/gameObjects/bedTable.png");
			bedTable.setDialogue(new String[] {"\"wow i left my phone in the exact same place\"," +
					"you see some text on your phone... ",
					"\"JB is your luck, your curse... and your exit\""});
			m_interactables.put(44, bedTable);
			m_blocked[4][4] = true;
			m_objects.put(44, bedTable);
			
			// you interact with the table to hack the system
			TableToHack table;
			try {
				table = new TableToHack(14, SIZE, 4*SIZE);			
				m_interactables.put(14, table);
				m_blocked[1][4] = true;
				m_blocked[1][5] = true;
				m_objects.put(14, table);
			} catch (FileNotFoundException e) { e.printStackTrace();
			} catch (UnsupportedEncodingException e) { e.printStackTrace();}

			
			StaticObject door = new StaticObject(2*SIZE, 2*SIZE, "assets/gameObjects/door.png");
			door.setDialogue(new String[] {"The door appears to be fake...",
					"Looks like you're locked in this virtual reality state"});
			m_interactables.put(22, door);
			m_objects.put(22, door);
			
			StaticObject bed = new StaticObject(3*SIZE, 5*SIZE, "assets/gameObjects/bed.png");
			m_blocked[3][5] = true;
			m_blocked[4][5] = true;
			m_objects.put(35, bed);
			
			InvisiblePortal invisiblePortal = 
					new InvisiblePortal(42, 4*SIZE, 2*SIZE, StateManager.VIRTUAL_REALITY_ROOM_STATE, 6, 2);
			m_interactables.put(42, invisiblePortal);
			m_objects.put(42, invisiblePortal);
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
