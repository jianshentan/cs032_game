package game;
import java.util.ArrayList;

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
	private Chest m_chest;
	private ArrayList<Interactable> m_objects; 
	private boolean[][] m_blocked; // 2D array indicating spaces that are blocked
    private static final int SIZE = 64; // block size
	private Rectangle m_viewport;
	private Dialogue m_dialogue1;
	
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
		m_chest.getImage().draw(m_chest.getX()-offsetX, m_chest.getY()-offsetY);
		m_enemy.getAnimation().draw(m_enemy.getX()-offsetX, m_enemy.getY()-offsetY);
		m_player.getAnimation().draw(halfWidth, halfHeight);

		if (m_isPaused)
			m_pauseMenu.render(g);
		if (m_inDialogue)
			m_dialogue1.render(g);
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
		m_player = new Player(this, 256f, 256f);

		// setup objects
		m_objects= new ArrayList<Interactable>();
		m_chest = new Chest(2*SIZE, 3*SIZE);
		m_objects.add(m_chest);
		m_blocked[2][3] = true;      

		m_enemy = new Enemy(this, 1*SIZE, 1*SIZE);
		
		// setup menu
		m_pauseMenu = new PauseMenu(this, container);
		
		// setup dialogue
		m_dialogue1 = new Dialogue(this, container, new String[] 
				{"Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
						"Mauris ultrices dolor non massa eleifend elementum. " +
						"Suspendisse vel magna augue, in tincidunt urna. ",
				 "Fusce in ligula libero, eget lacinia tellus. Donec bibendum " +
						"ultrices eros sit amet lacinia. Praesent nec mauris ac " +
						"justo tempus dapibus a vel diam."});
	}

	@Override
	public void update(GameContainer container, StateBasedGame stateManager, int delta) throws SlickException {
		if (m_isPaused)
			m_pauseMenu.update(container, stateManager, delta);
		
		if (m_inDialogue)
			m_dialogue1.update(container, stateManager, delta);
		
		if (!m_isPaused && !m_inDialogue)
			m_player.update(container, delta);
		
		Input input = container.getInput();
		inputDelta-=delta;

		// creates menu screen
		if (inputDelta<0 && input.isKeyDown(Input.KEY_ESCAPE)) {
			if (!m_isPaused)
				m_isPaused = true;
			else
				m_isPaused = false;
        	inputDelta = 500;
        }
		
		// Testing dialogue -- testing purposes only
		if (inputDelta<0 && input.isKeyDown(Input.KEY_Z)) {
			if (!m_inDialogue)
				m_inDialogue = true;
			inputDelta = 500;
		}
	}

	public void interact(int[] interactSquare){
		for(Interactable i: m_objects){
			int[] loc = i.getSquare();
			if(loc[0]==interactSquare[0]&&loc[1]==interactSquare[1]){
				i.fireAction();
			}
		}
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
		for(Interactable i : m_objects) {
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
