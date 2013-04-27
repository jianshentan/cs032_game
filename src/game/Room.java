package game;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

public class Room extends GamePlayState {
	
	int m_stateID = 0;
	private int inputDelta = 0;
	public enum Direction {UP, DOWN, LEFT, RIGHT}
	private TiledMap m_horseMap;
	private Player m_player;
	private Enemy m_enemy;
	private Chest m_chest;
	private ArrayList<Interactable> m_objects; 
	private boolean[][] m_blocked; // 2D array indicating spaces that are blocked
    private static final int SIZE = 64; // block size
    private PauseMenu m_pauseMenu;
	private Rectangle m_viewport;
	private simpleMap m_map;
	public Room(int stateID) {
		m_stateID = stateID;
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
	}

	@Override
	public void init(GameContainer container, StateBasedGame stateManager) throws SlickException {
		// setup player
		m_map = new simpleMap();
		m_viewport = new Rectangle(0,0, container.getWidth(), container.getHeight());
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
		int[][] patrolPoints = {{1,6},{9,9}};
		m_enemy = new Enemy(this, 1*SIZE, 1*SIZE, patrolPoints);
		
		// setup menu
		m_pauseMenu = new PauseMenu(this, container.getWidth(), container.getHeight());
	}

	@Override
	public void update(GameContainer container, StateBasedGame stateManager, int delta) throws SlickException {
		if (!m_isPaused){
			m_player.update(container, delta);
			m_enemy.update(delta);
		}
		else{
			m_pauseMenu.update(container, stateManager, delta);
		}
		
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
		
	}
	
	public void interact(int[] interactSquare){
		for(Interactable i: m_objects){
			int[] loc = i.getSquare();
			if(loc[0]==interactSquare[0]&&loc[1]==interactSquare[1]){
				i.fireAction();
			}
		}
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
}
