package game;

import game.GamePlayState.simpleMap;

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
	
	public Home(int stateID, String mapPath) {
		m_stateID = stateID;
		m_mapPath = mapPath;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame stateManager)
			throws SlickException {
		// setup player
		m_map = new simpleMap();
		//m_viewport = new Rectangle(0,0, container.getWidth(), container.getHeight());
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

		// setup player
		if(m_player==null)
			m_player = new Player(this, container, 256f, 256f);
		if(!this.isLoaded()) {
			// setup objects
			m_interactables = new HashMap<Integer, Interactable>();
			m_objects = new HashMap<Integer, GameObject>();

			Chest chest = new Chest(23, 2*SIZE, 3*SIZE);
			m_interactables.put(23, chest);
			m_blocked[2][3] = true;		
			m_objects.put(23, chest);

			ChickenWing chickenWing = new ChickenWing(6*SIZE, 3*SIZE);
			m_interactables.put(63, chickenWing);
			m_blocked[6][3] = true;      
			m_objects.put(63, chickenWing);

			Cigarette cigarette = new Cigarette(8*SIZE, 4*SIZE);
			m_interactables.put(84, cigarette);
			m_blocked[8][4] = true;
			m_objects.put(84, cigarette);
		}

		int[][] patrolPoints = {{1,1},{1,8},{8,8},{8,1}};
		m_enemy = new Enemy(this, m_player, 1*SIZE, 1*SIZE, patrolPoints);
		Enemy[] e = new Enemy[1];
		e[0] = m_enemy;
		m_player.setEnemies(e);
		
		


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

}
