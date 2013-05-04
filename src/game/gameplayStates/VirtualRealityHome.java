package game.gameplayStates;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import game.StateManager;
import game.StaticObject;
import game.interactables.Interactable;
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
			
			StaticObject posters = 
				new StaticObject("posters", 3*SIZE, 1*SIZE, "assets/gameObjects/bieberPoster.png");
			this.addObject(posters, false);
			
			StaticObject carpet = 
				new StaticObject("carpet", 3*SIZE, 3*SIZE, "assets/gameObjects/carpet.png");
			this.addObject(carpet, false);
			
			StaticObject bedTable = 
				new StaticObject("bedTable", 4*SIZE, 4*SIZE, "assets/gameObjects/bedTable.png");
			bedTable.setDialogue(new String[] {"\"Wow I left my phone in the exact same place\"," +
					"you see some text on your phone... ",
					"\"JB is your luck, your curse... and your exit\""});
			bedTable.setRenderPriority(true);
			this.addObject(bedTable, true);
			m_blocked[4][4] = true;
			
			// you interact with the table to hack the system
			TableToHack table;
			try {
				table = new TableToHack("table", SIZE, 4*SIZE);			
				m_blocked[1][4] = true;
				m_blocked[1][5] = true;
				this.addObject(table, true);
			} catch (FileNotFoundException e) { e.printStackTrace();
			} catch (UnsupportedEncodingException e) { e.printStackTrace();}

			StaticObject door = new StaticObject("door", 2*SIZE, 2*SIZE, "assets/gameObjects/door.png");
			door.setDialogue(new String[] {"The door seems to be locked."});
			this.addObject(door, true);
			
			StaticObject bed = new StaticObject("bed", 3*SIZE, 5*SIZE, "assets/gameObjects/bed.png");
			m_blocked[3][5] = true;
			m_blocked[4][5] = true;
			this.addObject(bed, false);
			
			InvisiblePortal invisiblePortal = 
					new InvisiblePortal("invisiblePortal", 4*SIZE, 2*SIZE, StateManager.VIRTUAL_REALITY_ROOM_STATE, 6, 2);
			this.addObject(invisiblePortal, true);
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
