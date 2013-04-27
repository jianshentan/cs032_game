package game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * A Room represents a single level.
 *
 */
public class Room extends GamePlayState {

	int m_stateID = 0;
	private int inputDelta = 0;
	public enum Direction {UP, DOWN, LEFT, RIGHT}
	private TiledMap m_horseMap;
	private String m_mapPath; //path to the tiled map file
	private Player m_player;
	private Enemy m_enemy;

	/**
	 *  key is represented by 'xPos' + 'yPos'
	 *  example: if object has position (2,3), key = 23.
	 */
	private HashMap<Integer, GameObject> m_objects;
	private HashMap<Integer, Interactable> m_interactables; 

	private boolean[][] m_blocked; // 2D array indicating spaces that are blocked
	private static final int SIZE = 64; // block size
	private Rectangle m_viewport;

	private simpleMap m_map;
	
	private ArrayList<Dialogue> m_dialogue;
	private int m_dialogueNum; // represents which dialogue to use

	public Room(int stateID) {
		m_stateID = stateID;
	}

	public Room(int stateID, String mapPath) {
		m_stateID = stateID;
		m_mapPath = mapPath;
	}

	@Override
	public void render(GameContainer container, StateBasedGame stateManager, Graphics g) 
			throws SlickException {
		int halfWidth = container.getWidth()/2;
		int halfHeight = container.getHeight()/2;
		int offsetX = (int)m_player.getX()-halfWidth;
		int offsetY = (int)m_player.getY()-halfHeight;
		m_horseMap.render(-offsetX, -offsetY);
		for (Entry<Integer, GameObject> e : m_objects.entrySet()) {
			GameObject o = e.getValue();
			o.getImage().draw(o.getX()-offsetX, o.getY()-offsetY);
		}
		m_enemy.getAnimation().draw(m_enemy.getX()-offsetX, m_enemy.getY()-offsetY);
		m_player.getAnimation().draw(halfWidth, halfHeight);
		m_player.getHealth().render();

		if (m_inDialogue)
			m_dialogue.get(m_dialogueNum).render(g);
		if (m_isPaused)
			m_pauseMenu.render(g);
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
		m_player = new Player(this, container, 256f, 256f);

		// setup objects
		m_interactables = new HashMap<Integer, Interactable>();
		m_objects = new HashMap<Integer, GameObject>();

		Chest chest = new Chest(2*SIZE, 3*SIZE);
		m_interactables.put(23, chest);
		m_blocked[2][3] = true;      

		int[][] patrolPoints = {{1,1},{1,8},{8,8},{8,1}};
		m_enemy = new Enemy(this, m_player, 1*SIZE, 1*SIZE, patrolPoints);

		m_objects.put(23, chest);

		ChickenWing chickenWing = new ChickenWing(6*SIZE, 3*SIZE);
		m_interactables.put(63, chickenWing);
		m_blocked[6][3] = true;      

		m_objects.put(63, chickenWing);


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

	@Override
	public void update(GameContainer container, StateBasedGame stateManager, int delta) throws SlickException {

		if (m_isPaused)
			m_pauseMenu.update(container, stateManager, delta);

		if (m_inDialogue)
			m_dialogue.get(m_dialogueNum).update(container, stateManager, delta);

		
		if (!m_isPaused && !m_inDialogue){
			m_player.update(container, delta);
			m_enemy.update(delta);
		}
		

		Input input = container.getInput();
		inputDelta-=delta;

		// creates menu screen
		if (inputDelta<0 && input.isKeyDown(Input.KEY_ESCAPE)) {
			if (!m_inDialogue) { // must not be in dialogue
				if (!m_isPaused)
					m_isPaused = true;
				else
					m_isPaused = false;
			}
			inputDelta = 500;
		}

		// Testing dialogue -- testing purposes only
		if (inputDelta<0 && input.isKeyDown(Input.KEY_Z)) {
			m_dialogueNum = 0;
			if (!m_isPaused)  // must not be paused
				if (!m_inDialogue)
					m_inDialogue = true;
			inputDelta = 500;
		}
	}

	/**
	 * interact is called by the player to interact with some game object
	 * in the map.
	 * @param interactSquare - location of the target interactable
	 * @return
	 */
	public Interactable interact(int[] interactSquare){
		for(Entry<Integer, Interactable> e: m_interactables.entrySet()){
			Interactable i = e.getValue();
			int[] loc = i.getSquare();
			if(loc[0]==interactSquare[0]&&loc[1]==interactSquare[1]){
				int key = positionToKey(loc);
				// chest
				//key = 23;
				if (i.getType() == GameObject.Types.CHEST) {
					if (loc[0] == m_objects.get(key).getX()/SIZE && 
							loc[1] == m_objects.get(key).getY()/SIZE) {
						m_dialogueNum = 1;
						m_inDialogue = true;
					}
				}

				//chickenWing
				//key = 63;
				if (i.getType() == GameObject.Types.CHICKEN_WING) {
					if (loc[0] == m_objects.get(key).getX()/SIZE &&
							loc[1] == m_objects.get(key).getY()/SIZE) {
						m_interactables.remove(key);
						m_blocked[loc[0]][loc[1]] = false;
						m_objects.remove(key);
					}
				}
				return i.fireAction();
			}
		}
		return null;
	}


    public boolean blocked(int x, int y) {
    	return m_blocked[x][y];
    }
    public simpleMap getMap(){
    	return m_map;
    }


	@Override
	public int getID() {
		return m_stateID;
	}

	

	class simpleMap implements TileBasedMap{
		public static final int HEIGHT = 10;
		public static final int WIDTH = 10;
		
		public float getCost(PathFindingContext ctx, int x, int y){
			return 1.0f;
		}
		public boolean blocked(PathFindingContext ctx, int x, int y){
			return m_blocked[x][y];
		}
		public int getHeightInTiles(){
			return HEIGHT;
		}
		public int getWidthInTiles(){
			return WIDTH;
		}
		public void pathFinderVisited(int x, int y){};
	}


	public Player getPlayer() {
		return this.m_player;
	}

	public void setPlayer(Player p) {
		this.m_player = p;
	}

	public String getMapPath() {
		return this.m_mapPath;
	}

	public ArrayList<Interactable> getInteractables() {
		ArrayList<Interactable> ret = new ArrayList<Interactable>();
		for (Entry<Integer, Interactable> e : m_interactables.entrySet()) {
			Interactable i = e.getValue();
			ret.add(i);
		}
		return ret;
	}

	/**
	 * Given a position as an array of two integers, returns
	 * an integer consisting of the two integers concatenated.
	 * @param position
	 * @return integer
	 */
	public static int positionToKey(int[] position) {
		String s1 = String.valueOf(position[0]);
		String s2 = String.valueOf(position[1]);
		return Integer.parseInt(s1+s2);
	}

	/**
	 * Writes the data contained in the room to XML, not including
	 * the player data.
	 * @param writer
	 * @throws XMLStreamException
	 */
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		writer.writeStartElement("Room");
		if(m_mapPath!=null)
			writer.writeAttribute("m_mapPath", m_mapPath);
		writer.writeAttribute("id", String.valueOf(this.m_stateID));

		writer.writeStartElement("Interactables");
		for (Entry<Integer, Interactable> e : m_interactables.entrySet()) {
			Interactable i = e.getValue();
			i.writeToXML(writer);
		}
		//TODO: add enemy
		writer.writeEndElement();

		writer.writeEndElement();
	}

	/**
	 * Loads a new room from an XML node
	 * @param n
	 * @return
	 * @throws SlickException 
	 */
	public static Room loadFromNode(Node n) throws SlickException {
		int id = Integer.parseInt(n.getAttributes().getNamedItem("id").getNodeValue());
		Room room = new Room(id);
		NodeList children = n.getChildNodes();
		for(int i = 0; i<children.getLength(); i++) {
			Node child = children.item(i);
			if(child.getNodeName().equals("Interactables")) {
				Node c2 = child;
				NodeList interactables = c2.getChildNodes();
				for(int j = 0; j< interactables.getLength(); j++) {
					Node c3 = interactables.item(j);
					if(c3.getNodeName().equals("Interactable")) {
						//TODO: add interactables
						Interactable o = Interactables.loadFromNode(child);
						int[] square = o.getSquare();
						room.m_interactables.put(positionToKey(square), o);
					}
				}
			}
		}
		return room;
	}


}
