package game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


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
	public void init(GameContainer container, StateBasedGame stateManager) throws SlickException {
		// setup player
		m_map = new simpleMap();
		m_viewport = new Rectangle(0,0, container.getWidth(), container.getHeight());
		if(m_mapPath != null) {
			m_horseMap = new TiledMap(m_mapPath);
		}
		else
			try {
				m_horseMap = new TiledMap("assets/10X10.tmx");
			} catch (SlickException e) {
				System.out.println("ERROR: Could not 10X10.tmx");
			}
		m_blocked = new boolean[m_horseMap.getWidth()][m_horseMap.getHeight()];
		for (int xAxis=0; xAxis<m_horseMap.getWidth(); xAxis++) {
			for (int yAxis=0; yAxis<m_horseMap.getHeight(); yAxis++) {
				int tileID = m_horseMap.getTileId(xAxis, yAxis, 0);
				String value = m_horseMap.getTileProperty(tileID, "blocked", "false");
				if ("true".equals(value)) {
					m_blocked[xAxis][yAxis] = true;
				}
			}
		}

		// setup player

		if(m_player==null)
			m_player = new Player(this, container, 256f, 256f);

		// setup objects
		m_interactables = new HashMap<Integer, Interactable>();
		m_objects = new HashMap<Integer, GameObject>();

		Chest chest = new Chest(23, 2*SIZE, 3*SIZE);
		m_interactables.put(23, chest);
		m_blocked[2][3] = true;		
		m_objects.put(23, chest);

		int[][] patrolPoints = {{1,1},{1,8},{8,8},{8,1}};
		m_enemy = new Enemy(this, m_player, 1*SIZE, 1*SIZE, patrolPoints);
		Enemy[] e = new Enemy[1];
		e[0] = m_enemy;
		m_player.setEnemies(e);
		m_objects.put(23, chest);


		ChickenWing chickenWing = new ChickenWing(6*SIZE, 3*SIZE);
		m_interactables.put(63, chickenWing);
		m_blocked[6][3] = true;      
		m_objects.put(63, chickenWing);
		
		Cigarette cigarette = new Cigarette(8*SIZE, 4*SIZE);
		m_interactables.put(84, cigarette);
		m_blocked[8][4] = true;
		m_objects.put(84, cigarette);
		


		// setup menu
		m_pauseMenu = new PauseMenu(this, container);

		// setup dialogue
		m_dialogue = new ArrayList<Dialogue>();
		Dialogue dialogue1 = new Dialogue(this, container, new String[] 
				{"Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
						"Mauris ultrices dolor non massa eleifend elementum. " +
						"Suspendisse vel magna augue, in tincidunt urna. ",
						"Fusce in ligula libero, eget lacinia tellus. Donec bibendum " +
								"ultrices eros sit amet lacinia. Praesent nec mauris ac " +
				"justo tempus dapibus a vel diam."});

		Dialogue dialogue2 = new Dialogue(this, container, new String[] 
				{"let is rain over me!!! girl my body dont stop, out of my mind " +
						"let it rain over meeeeeeeeeeeee aii yai yaiii ai yaii ya " +
						"let it rain over meeeeeeeeee. yeah! ",
						"I'm rising so high, i'm out o fmy mind let it rain overr meeeeee" +
								"billions a new milion, voili's the new vodka" +
				"forty's the new thirty, baby you're a rockstar!"});

		m_dialogue.add(dialogue1);
		m_dialogue.add(dialogue2);
	}
	
	/**
	 * For the testing room, the "additional" update is 
	 */
	@Override
	public void additionalUpdate(GameContainer container, StateBasedGame stateManager, int delta) {
		Input input = container.getInput();
		// Testing dialogue -- testing purposes only
		if (inputDelta<0 && input.isKeyDown(Input.KEY_Z)) {
			m_dialogueNum = 0;
			if (!m_isPaused)  // must not be paused
				if (!m_inDialogue)
					m_inDialogue = true;
			inputDelta = 500;
		}
	}
    


}
