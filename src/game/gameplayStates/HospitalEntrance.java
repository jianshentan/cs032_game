package game.gameplayStates;

import game.Person;
import game.StateManager;
import game.StaticObject;
import game.interactables.Door;
import game.interactables.Interactable;
import game.interactables.InvisiblePortal;
import game.interactables.PortalObject;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class HospitalEntrance extends GamePlayState {
	
	private boolean m_healthLow = false;

	public HospitalEntrance(int stateID) {
		m_stateID = stateID;
	}
	
	@Override
	public void additionalEnter(GameContainer container, StateBasedGame stateManager) throws SlickException {
		if(m_player.getHealth().getHealthPercentage() <= 0.75) {
			m_healthLow = true;
		}
		if(m_healthLow && StateManager.m_cityState==2) {
			setupQuest();
		}
	}
	
	@Override
	public void additionalInit(GameContainer container, StateBasedGame stateManager)
		throws SlickException {
		this.setMusic("assets/sounds/BetterBuildingInt.wav");
		m_playerX = SIZE*6;
		m_playerY = SIZE*8; 
		
		this.m_tiledMap = new TiledMap("assets/maps/hospitalEntrance.tmx");
		this.m_map = new simpleMap();
		this.setBlockedTiles();
		
		if (!this.isLoaded()) {
			StaticObject counter = new StaticObject("counter", 5*SIZE, 5*SIZE, "assets/gameObjects/hospitalReceptionTable.png");
			counter.setRenderPriority(2);
			this.addObject(counter, false);
			m_blocked[5][6] = true;
			m_blocked[6][6] = true;
			m_blocked[7][6] = true;
			m_blocked[7][5] = true;
			
			InvisiblePortal portal = new InvisiblePortal("hospitalEntranceDoorOut", 6*SIZE, 9*SIZE, StateManager.TOWN_DAY_STATE, 5, 22);
			addObject(portal, true);
			
			StaticObject doorMat = new StaticObject("hospitalEntranceDoorMat", 6*SIZE, 8*SIZE, "assets/gameObjects/doormat.png");
			this.addObject(doorMat, false);
		}
	}
	
	private void setupQuest() throws SlickException {
		this.removeObject("hospitalMazePerson");
		this.removeObject("hospitalCounterPerson");
		this.removeObject("hospitalEntranceDoor");
		this.removeObject("hospitalEntranceSign");
		
		Person nurse = new Person("hospitalMazePerson", 9*SIZE, 6*SIZE,"assets/characters/woman_1.png", null);
		nurse.setDialogue(new String[] {"\"Help! I'm a nurse working in the hospital... \"", 
				"\"The power just went out on us. Oh god, I've really messed up.\"",
				"\"I was transporting some really awful drugs, 8 of them. The doctor told me to get rid of them. But I've dropped them all in the darkness!\"",
				"\"Please help me find them. Its not safe for these drugs to just be lying around...\"",
				"Helping some nurse seems cool... but perhaps you can use these drugs to shut the hospital down instead."
		});
		addObject(nurse, true);
		m_blocked[9][6] = true;
		
		Person counterPerson = new Person("hospitalCounterPerson", 6*SIZE, 5*SIZE, "assets/characters/woman_1.png", null);
		addObject(counterPerson, true);
		StaticObject counterDialogue = new StaticObject("counterDialogue", 6*SIZE, 6*SIZE, "assets/colors/clear.png");
		counterDialogue.setDialogue(new String[] {"\"Welcome to the our hospital... Our power just went out so we won't be able to help you tonight.\""});
		addObject(counterDialogue, true);
		
		PortalObject door = new Door("hospitalEntranceDoor", 8*SIZE, 4*SIZE, StateManager.HOSPITAL_MAZE_STATE, -1, -1);
		addObject(door, true);
		
		StaticObject doorSign = new StaticObject("hospitalEntranceSign", 9*SIZE, 4*SIZE, "assets/gameObjects/sign.png");
		doorSign.setDialogue(new String[] {"\"Warning: Enter at your own risk. There is no turning back\"",
				"\"* DISCLAIMER: The following level is not for the weak mind or the easily frustrated. Enter with care. *\""});
		this.addObject(doorSign, true);
	}

	@Override
	public void setupObjects(int city, int dream) throws SlickException {
		if (city == 2 && m_healthLow) {
			setupQuest();
		}
		else {
			Person nurse = new Person("hospitalMazePerson", 9*SIZE, 6*SIZE,"assets/characters/woman_1.png", null);
			nurse.setDialogue(new String[] {"She looks busy saving lives and shit... Probably should not disturb her"});
			addObject(nurse, true);
			m_blocked[9][6] = true;
			
			Person counterPerson = new Person("hospitalCounterPerson", 6*SIZE, 5*SIZE, "assets/characters/woman_1.png", null);
			addObject(counterPerson, true);
			StaticObject counterDialogue = new StaticObject("counterDialogue", 6*SIZE, 6*SIZE, "assets/colors/clear.png");
			counterDialogue.setDialogue(new String[] {"\"You're not sick.. Other than that mustache. Go away.\""});
			addObject(counterDialogue, true);
			
			StaticObject door = new StaticObject("hospitalEntranceDoor", 8*SIZE, 4*SIZE, "assets/gameObjects/door.png");
			door.setDialogue(new String[] {"Door appears to be locked. Perhaps only them fancy docters are allowed past this point"});
			addObject(door, true);
			
			StaticObject doorSign = new StaticObject("hospitalEntranceSign", 9*SIZE, 4*SIZE, "assets/gameObjects/sign.png");
			doorSign.setDialogue(new String[] {"Door appears to be locked. Perhaps only them fancy doctors are allowed past this point"});
			this.addObject(doorSign, true);	
		}
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
