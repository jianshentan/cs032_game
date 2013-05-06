package game.gameplayStates;

import game.Person;
import game.StateManager;
import game.StaticObject;
import game.gameplayStates.GamePlayState.simpleMap;
import game.interactables.Door;
import game.interactables.Interactable;
import game.interactables.PortalObject;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class HospitalEntrance extends GamePlayState {

	public HospitalEntrance(int stateID) {
		m_stateID = stateID;
	}
	
	@Override
	public void additionalInit(GameContainer container, StateBasedGame stateManager)
		throws SlickException {
		
		m_playerX = SIZE*6;
		m_playerY = SIZE*8; 
		
		this.m_tiledMap = new TiledMap("assets/maps/hospitalEntrance.tmx");
		this.m_map = new simpleMap();
		this.setBlockedTiles();
		
		if (!this.isLoaded()) {
			PortalObject door = new Door("hospitalEntranceDoor", 8*SIZE, 4*SIZE, StateManager.HOSPITAL_MAZE_STATE, -1, -1);
			addObject(door, true);
			
			Person person = new Person("hospitalMazePerson", 9*SIZE, 6*SIZE,"assets/characters/woman_1.png", null);
			person.setDialogue(new String[] {"\"Help! I'm a nurse working in the hospital... \"", 
					"\"The power just went out on us. Oh god, I've really messed up.\"",
					"\"I was transporting some really awful drugs, 8 of them. The doctor told me to get rid of them. But I've dropped them all in the darkness!\"",
					"\"Please help me find them. Its not safe for these drugs to just be lying around...\"",
					"Helping some nurse seems cool... but perhaps you can use these drugs to shut the hospital down instead."
			});
			addObject(person, true);
			m_blocked[9][6] = true;
			
			StaticObject counter = new StaticObject("counter", 5*SIZE, 5*SIZE, "assets/gameObjects/hospitalReceptionTable.png");
			this.addObject(counter, false);
		}
	}

	@Override
	public void setupObjects(int city, int dream) throws SlickException {

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
