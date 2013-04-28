package game.gameplayStates;

import game.Dialogue;
import game.Enemy;
import game.GameObject;
import game.Loadable;
import game.PauseMenu;
import game.StateManager;
import game.GameObject.Types;
import game.interactables.Interactable;
import game.interactables.Interactables;
import game.player.Player;

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
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * All game states that are in gameplay-mode need to inherit from this class
 * -- pausemenu
 * -- text/dialouge 
 */
public abstract class GamePlayState extends BasicGameState implements Loadable<GamePlayState> {
	
	protected boolean m_isPaused = false;
	private boolean m_inDialogue = false;
	protected PauseMenu m_pauseMenu = null;
	
	int m_stateID = 0;
	protected int inputDelta = 0;
	public enum Direction {UP, DOWN, LEFT, RIGHT}
	protected TiledMap m_tiledMap;
	protected String m_mapPath; //path to the tiled map file
	protected Player m_player;
	protected int m_playerX, m_playerY;
	protected Enemy m_enemy;



	protected boolean[][] m_blocked; // 2D array indicating spaces that are blocked
	protected static final int SIZE = 64; // block size
	//protected Rectangle m_viewport;

	protected simpleMap m_map;
	
	protected ArrayList<Dialogue> m_dialogue;
	protected int m_dialogueNum; // represents which dialogue to use
	
	private boolean m_loaded; //true if the state has already been loaded from file.
	public boolean isLoaded() { return m_loaded; }
	
	private boolean m_isActive; //true if the state is the active state
	public boolean isActive() { return m_isActive; }
	
	/**
	 *  key is represented by 'xPos' + 'yPos'
	 *  example: if object has position (2,3), key = 23.
	 */
	protected HashMap<Integer, GameObject> m_objects;
	protected HashMap<Integer, Interactable> m_interactables; 
	
	public void setPauseState(boolean state) { m_isPaused = state; }
	public boolean getPauseState() { return m_isPaused; }
	
	public void setDialogueState(boolean state) { set_inDialogue(state); }
	public boolean getDialogueState() { return is_inDialogue(); }
	
	/**
	 * This is used by subclasses to do level-specific initialization.
	 * @param container
	 * @param stateManager
	 * @throws SlickException 
	 */
	public void additionalInit(GameContainer container, StateBasedGame stateManager) throws SlickException {
		
	}
	
	/**
	 * This is used by subclasses to add updating functionality.
	 * @param container
	 * @param stateManager
	 * @param delta
	 */
	public void additionalUpdate(GameContainer container, StateBasedGame stateManager, int delta) {
		
	}
	
	/**
	 * This is used by subclasses to add rendering functionality.
	 * @param container
	 * @param stateManager
	 * @param g
	 */
	public void additionalRender(GameContainer container, StateBasedGame stateManager, Graphics g) {
		
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame stateManager) throws SlickException {
		// setup menu
		m_pauseMenu = new PauseMenu(this, container);
		m_interactables = new HashMap<Integer, Interactable>();
		m_objects = new HashMap<Integer, GameObject>();
		m_dialogue = new ArrayList<Dialogue>();
		this.additionalInit(container, stateManager);
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame stateManager, int delta) throws SlickException {

		if (m_isPaused && m_pauseMenu!=null)
			m_pauseMenu.update(container, stateManager, delta);

		if (is_inDialogue())
			m_dialogue.get(m_dialogueNum).update(container, stateManager, delta);

		
		if (!m_isPaused && !is_inDialogue()){
			m_player.update(container, delta);
			if (m_enemy != null)
				m_enemy.update(delta);
		}
		

		Input input = container.getInput();
		inputDelta-=delta;

		// creates menu screen
		if (inputDelta<0 && input.isKeyDown(Input.KEY_ESCAPE)) {
			if (!is_inDialogue()) { // must not be in dialogue
				if (!m_isPaused)
					m_isPaused = true;
				else
					m_isPaused = false;
			}
			inputDelta = 500;
		}
		
		this.additionalUpdate(container, stateManager, delta);


	}
	
	@Override
	public void render(GameContainer container, StateBasedGame stateManager, Graphics g) 
			throws SlickException {
		int halfWidth = container.getWidth()/2;
		int halfHeight = container.getHeight()/2;
		int offsetX = (int)m_player.getX()-halfWidth;
		int offsetY = (int)m_player.getY()-halfHeight;
		m_tiledMap.render(-offsetX, -offsetY);
		for (Entry<Integer, GameObject> e : m_objects.entrySet()) {
			GameObject o = e.getValue();
			o.getImage().draw(o.getX()-offsetX, o.getY()-offsetY);
		}
		if (m_enemy != null)
			m_enemy.getAnimation().draw(m_enemy.getX()-offsetX, m_enemy.getY()-offsetY);
		m_player.getAnimation().draw(halfWidth, halfHeight);
		m_player.getHealth().render();
		if (m_player.m_inInventory) { m_player.getInventory().render(g); }
		

		if (is_inDialogue())
			m_dialogue.get(m_dialogueNum).render(g);
		if (m_isPaused && m_pauseMenu!=null)
			m_pauseMenu.render(g);
		
		this.additionalRender(container, stateManager, g);
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
	
	public void setPlayerLocation(int xLoc, int yLoc) {
		m_playerX = xLoc;
		m_playerY = yLoc;
	}

	public String getMapPath() {
		return this.m_mapPath;
	}
	
	/**
	 * Removes a given object, and unblocks the map space it occupied.
	 * @param key
	 * @param xLoc
	 * @param yLoc
	 */
	public void removeObject(int key, int xLoc, int yLoc) {
		m_interactables.remove(key);
		m_blocked[xLoc][yLoc] = false;
		m_objects.remove(key);
	}
	
	/**
	 * Returns true if the tile is blocked, and false otherwise.
	 * @param x
	 * @param y
	 * @return
	 */
    public boolean blocked(int x, int y) {
    	return m_blocked[x][y];
    }
    
    /**
     * Sets tile at x,y to blocked, and returns true.
     * @param x
     * @param y
     * @return
     */
    public boolean blockTile(int x, int y) {
    	m_blocked[x][y] = true;
    	return m_blocked[x][y];
    }
    
	/**
	 * Returns the Interactable of an interaction.
	 * @param interactSquare
	 * @return
	 */
	public Interactable interact(int[] interactSquare) {
		for(Entry<Integer, Interactable> e: m_interactables.entrySet()){
			Interactable i = e.getValue();
			int[] loc = i.getSquare();
			if(loc[0]==interactSquare[0]&&loc[1]==interactSquare[1]){
				//TODO: this shouldn't be necessary
				if (i.getType() == GameObject.Types.CHEST) {
						m_dialogueNum = 1;
						set_inDialogue(true);
				}
				return i.fireAction(this, m_player);
			}
		}
		return null;
	}
	
    public simpleMap getMap(){
    	return m_map;
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

	@Override
	public GamePlayState loadFromXML(Node n, GameContainer c, StateManager g) throws SlickException {
		this.m_interactables = new HashMap<Integer, Interactable>();
		this.m_objects = new HashMap<Integer, GameObject>();
		NodeList children = n.getChildNodes();
		for(int i = 0; i<children.getLength(); i++) {
			Node child = children.item(i);
			if(child.getNodeName().equals("Interactables")) {
				Node c2 = child;
				NodeList interactables = c2.getChildNodes();
				for(int j = 0; j< interactables.getLength(); j++) {
					Node c3 = interactables.item(j);
					if(c3.getNodeName().equals("Interactable")) {
						Interactable o = Interactables.loadFromNode(c3);
						if(o!=null) {
							int[] square = o.getSquare();
							this.m_interactables.put(positionToKey(square), o);
							this.m_objects.put(positionToKey(square), (GameObject) o);
						}
					}
				}
			}
		}
		this.m_loaded = true;
		return this;
	}
	
	public boolean is_inDialogue() {
		return m_inDialogue;
	}
	public void set_inDialogue(boolean m_inDialogue) {
		this.m_inDialogue = m_inDialogue;
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
}
