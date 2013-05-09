package game.gameplayStates;

import game.Person;
import game.StateManager;
import game.StaticObject;
import game.gameplayStates.GamePlayState.simpleMap;
import game.interactables.Door;
import game.interactables.Interactable;
import game.interactables.InvisiblePortal;
import game.interactables.PortalObject;
import game.interactables.TraderCounter;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Shop extends GamePlayState{

	public Shop(int stateID) {
		m_stateID = stateID;
	}
	
	@Override
	public void additionalEnter(GameContainer container, StateBasedGame stateManager) throws SlickException {
	}
	
	@Override
	public void additionalInit(GameContainer container, StateBasedGame stateManager)
		throws SlickException {
		this.setMusic("assets/sounds/BetterBuildingInt.wav");
		m_playerX = SIZE*4;
		m_playerY = SIZE*4; 
		
		this.m_tiledMap = new TiledMap("assets/maps/shopMap.tmx");
		this.m_map = new simpleMap();
		this.setBlockedTiles();
		
		if (!this.isLoaded()) {
			InvisiblePortal portal = new InvisiblePortal("shopDoorOut", 4*SIZE, 5*SIZE, StateManager.TOWN_DAY_STATE, 19, 15);
			addObject(portal, true);
			
			StaticObject doorMat = new StaticObject("shopDoorMat", 4*SIZE, 4*SIZE, "assets/gameObjects/doormat.png");
			this.addObject(doorMat, false);
			
			StaticObject counter = new StaticObject("shopCounter", SIZE, 2*SIZE - 14, "assets/gameObjects/shopCounter.png");
			this.addObject(counter, false);
			m_blocked[1][2] = true;
			m_blocked[2][2] = true;
			m_blocked[2][3] = true;
			m_blocked[2][4] = true;
			
			StaticObject trader = new StaticObject("shopPerson", SIZE, 3*SIZE, "assets/characters/dummy_human1.png");
			trader.setRenderPriority(5);
			this.addObject(trader, false);
			
			TraderCounter traderCounter = new TraderCounter("traderCounter", 2*SIZE, 3*SIZE);
			this.addObject(traderCounter, true);
		}
	}
	
	@Override
	public void setupObjects(int city, int dream) throws SlickException {
	}

	@Override
	public void setupDialogue(GameContainer container, int city, int dream)
			throws SlickException {

	}

	@Override
	@Deprecated
	public void dialogueListener(Interactable i) {
		// TODO Auto-generated method stub
		
	}
}
