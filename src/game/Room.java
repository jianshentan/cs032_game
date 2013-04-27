package game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import game.Interactables.Types;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

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

	int m_stateID = 0;
	private int inputDelta = 0;
	public enum Direction {UP, DOWN, LEFT, RIGHT}
	private TiledMap m_horseMap;
	private String m_mapPath; //path to the tiled map file
	private Player m_player;
	private Enemy m_enemy;
	
	private ArrayList<GameObject> m_objects;
	private HashMap<Types, Interactable> m_interactables; 
	private boolean[][] m_blocked; // 2D array indicating spaces that are blocked
    private static final int SIZE = 64; // block size
	private Rectangle m_viewport;
	
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
//		m_chest.getImage().draw(m_chest.getX()-offsetX, m_chest.getY()-offsetY);
//		m_chickenWing.getImage().draw(m_chickenWing.getX()-offsetX, m_chickenWing.getY()-offsetY);
		for (GameObject o : m_objects)
			o.getImage().draw(o.getX()-offsetX, o.getY()-offsetY);
		m_enemy.getAnimation().draw(m_enemy.getX()-offsetX, m_enemy.getY()-offsetY);
		m_player.getAnimation().draw(halfWidth, halfHeight);

		if (m_inDialogue)
			m_dialogue.get(m_dialogueNum).render(g);
		if (m_isPaused)
			m_pauseMenu.render(g);
	}

	@Override
	public void init(GameContainer container, StateBasedGame stateManager) throws SlickException {
		// setup player
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
		m_interactables = new HashMap<Types, Interactable>();
		m_objects = new ArrayList<GameObject>();
		
		Chest chest = new Chest(2*SIZE, 3*SIZE);
		m_interactables.put(Types.CHEST, chest);
		m_blocked[2][3] = true;      
		m_objects.add(chest);
		
		ChickenWing chickenWing = new ChickenWing(6*SIZE, 3*SIZE);
		m_interactables.put(Types.CHICKEN_WING, chickenWing);
		m_blocked[6][3] = true;      
		m_objects.add(chickenWing);

		m_enemy = new Enemy(this, 1*SIZE, 1*SIZE);
		
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
		
		if (!m_isPaused && !m_inDialogue)
			m_player.update(container, delta);
		
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

	public Interactable interact(int[] interactSquare){
		for(Entry<Types, Interactable> e: m_interactables.entrySet()){
			Interactable i = e.getValue();
			int[] loc = i.getSquare();
			if(loc[0]==interactSquare[0]&&loc[1]==interactSquare[1]){
				// handles interaction with room
				if (loc[0] == m_objects.get(0).getX()/SIZE && 
					loc[1] == m_objects.get(0).getY()/SIZE) {
					m_dialogueNum = 1;
					m_inDialogue = true;
				}
				if (loc[0] == m_objects.get(1).getX()/SIZE &&
					loc[1] == m_objects.get(1).getY()/SIZE) {
					m_interactables.remove(Types.CHICKEN_WING);
					m_blocked[loc[0]][loc[1]] = false;
//					m_objects.remove(1);
				}
				return i.fireAction();
			}
		}
		return null;
	}

	public boolean getBlocked(int x, int y) {
		return m_blocked[x][y];
	}

	@Override
	public int getID() {
		return m_stateID;
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
		for (Entry<Types, Interactable> e : m_interactables.entrySet()) {
			Interactable i = e.getValue();
			ret.add(i);
		}
		return ret;
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
		for (Entry<Types, Interactable> e : m_interactables.entrySet()) {
			Interactable i = e.getValue();
			i.writeToXML(writer);
		}
		writer.writeEndElement();
		
		writer.writeEndElement();
	}

	/**
	 * Loads a new room from an XML node
	 * @param n
	 * @return
	 */
	public static Room loadFromNode(Node n) {
		int id = Integer.parseInt(n.getAttributes().getNamedItem("id").getNodeValue());
		Room room = new Room(id);
		NodeList children = n.getChildNodes();
		for(int i = 0; i<children.getLength(); i++) {
			Node child = children.item(i);
			if(child.getNodeName().equals("Interactable")) {
				
			}
		}
		return room;
	}

}
