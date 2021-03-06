package game.gameplayStates;
import game.AIState;
import game.Dialogue;
import game.Enemy;
import game.GameObject;
import game.PauseMenu;
import game.Scene;
import game.Spectre;
import game.StateManager;
import game.interactables.Chest;
import game.interactables.ChickenWing;
import game.interactables.Cigarette;
import game.interactables.Door;
import game.interactables.Interactable;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;


/**
 * A Room represents a single level.
 *
 */
public class Room extends GamePlayState {
	
	public Room(int stateID) {
		m_stateID = stateID;
	}

	public Room(int stateID, String mapPath) {
		m_stateID = stateID;
		m_mapPath = mapPath;
	}

	@Override
	public void additionalInit(GameContainer container, StateBasedGame stateManager) throws SlickException {
		m_playerX = SIZE*4;
		m_playerY = SIZE*3;
		// setup player
		
		//m_viewport = new Rectangle(0,0, container.getWidth(), container.getHeight());
		if(m_mapPath != null) {
			m_tiledMap = new TiledMap(m_mapPath);
		}
		else
			try {
				m_tiledMap = new TiledMap("assets/10X10.tmx");
			} catch (SlickException e) {
				System.out.println("ERROR: Could not 10X10.tmx");
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

		if(!this.isLoaded()) {
			// setup objects
			//m_interactables = new HashMap<Integer, Interactable>();
			//m_objects = new HashMap<Integer, GameObject>();

			Chest chest = new Chest("chest", 2*SIZE, 3*SIZE);
			this.addObject(chest, true);
			m_blocked[2][3] = true;		

			ChickenWing chickenWing = new ChickenWing("chickenWing", 6*SIZE, 3*SIZE);
			this.addObject(chickenWing, true);
			m_blocked[6][3] = true;      

			Cigarette cigarette = new Cigarette("cigarette", 8*SIZE, 4*SIZE);
			this.addObject(cigarette, true);
			m_blocked[8][4] = true;
			
			Door door = new Door("door", 6*SIZE, 7*SIZE, StateManager.KITCHEN_STATE, 2, 1);
			this.addObject(door, true);
		}

		int[][] patrolPoints = {{1,1},{1,8},{8,8},{8,1}};
		int[][] leadPoints ={{8,8},{8,8},{8,8},{8,8}};
		//Enemy enemy = new Spectre(this, m_player, 1*SIZE, 1*SIZE, leadPoints);
		Enemy enemy = new Enemy(this, m_player, 1*SIZE, 1*SIZE);
		enemy.setAI(AIState.ROAM);
		enemy.setPatrolPoints(patrolPoints);
		
		enemy.setLeadTo(leadPoints[0][0], leadPoints[0][1]);
		Enemy[] e = new Enemy[1];
		e[0] = enemy;
		m_player.setEnemies(e);
		m_enemies.add(enemy);
		this.addObject(e[0], false);
		// setup menu
		m_pauseMenu = new PauseMenu(this, container);

		// setup dialogue
		m_dialogue = new HashMap<Integer, Dialogue>();
		Dialogue dialogue1 = new Dialogue(this, container, new String[] 
				{"Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
						"Mauris ultrices dolor non massa eleifend elementum. " +
						"Suspendisse vel magna augue, in tincidunt urna. ",
						"Fusce in ligula libero, eget lacinia tellus. Donec bibendum " +
								"ultrices eros sit amet lacinia. Praesent nec mauris ac " +
				"justo tempus dapibus a vel diam."}, null);

		Dialogue dialogue2 = new Dialogue(this, container, new String[] 
				{"let is rain over me!!! girl my body dont stop, out of my mind " +
						"let it rain over meeeeeeeeeeeee aii yai yaiii ai yaii ya " +
						"let it rain over meeeeeeeeee. yeah! ",
						"I'm rising so high, i'm out o fmy mind let it rain overr meeeeee" +
								"billions a new milion, voili's the new vodka" +
				"forty's the new thirty, baby you're a rockstar!"}, null);

//		m_dialogue.add(dialogue1);
		m_dialogue.put(23, dialogue2);
		
		
	}
	
	public void additionalEnter(GameContainer container, StateBasedGame stateManager) {
		if(this.isEntered()==false) {
			float[][] path = {{4,8}, {1,5}};
			Scene s = new Scene(this, this.getPlayer(), path);
			//s.playScene();
		}
	}
	
	public void dialogueListener(Interactable i) {
		if (i.getType() == GameObject.Types.CHEST) {
			//m_dialogueNum = 1;
			//m_inDialogue = true;
		}
	}
	
	/**
	 * For the testing room, the "additional" update is just having "z" launch a dialog.
	 */
	@Override
	public void additionalUpdate(GameContainer container, StateBasedGame stateManager, int delta) {
		Input input = container.getInput();
		// Testing dialogue -- testing purposes only
//		if (inputDelta<0 && input.isKeyDown(Input.KEY_Z)) {
//			m_dialogueNum = 0;
//			if (!m_isPaused)  // must not be paused
//				if (!is_inDialogue())
//					set_inDialogue(true);
//			inputDelta = 500;
//		}
	}

	@Override
	public void setupObjects(int city, int dream) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setupDialogue(GameContainer container, int city, int dream) {
		// TODO Auto-generated method stub
		
	}
    


}
