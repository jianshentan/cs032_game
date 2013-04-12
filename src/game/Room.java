package game;
import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;


public class Room extends BasicGameState{
	
	int m_stateID = 0;
	private TiledMap m_horseMap;
	private Player m_player;
	private Chest m_chest;
	private ArrayList<Interactable> m_objects;
	private Rectangle m_viewport;
	// The collision map indicating which tiles block movement - generated 
	private boolean[][] m_blocked;
	// block size
    private static final int SIZE = 64;
	
	public Room(int stateID) {
		m_stateID = stateID;
	}

	@Override
	public void render(GameContainer container, StateBasedGame stateManager, Graphics g) throws SlickException {
		int halfWidth = container.getWidth()/2;
		int halfHeight = container.getHeight()/2;
		int offsetX = (int)m_player.getX()-halfWidth;
		int offsetY = (int)m_player.getY()-halfHeight;
		m_horseMap.render(-offsetX, -offsetY);
		m_chest.getImage().draw(m_chest.getX()-offsetX, m_chest.getY()-offsetY);
		m_player.getAnimation().draw(halfWidth, halfHeight);
	}

	@Override
	public void init(GameContainer container, StateBasedGame stateManager) throws SlickException {
		// setup player
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
		//setup objects
		m_objects= new ArrayList<Interactable>();
		m_chest = new Chest(2*SIZE, 3*SIZE);
		m_objects.add(m_chest);
		m_blocked[2][3] = true;      

	}

	@Override
	public void update(GameContainer container, StateBasedGame stateManager, int delta) throws SlickException {
		m_player.update(container, delta, SIZE);
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
	
	
}
