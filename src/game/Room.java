package game;
import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Room extends BasicGameState{
	
	int m_stateID = 0;
	private int inputDelta = 0;
	public enum Direction {UP, DOWN, LEFT, RIGHT}
	private TiledMap m_horseMap;
	private Player m_player;
	private Chest m_chest;
	private ArrayList<Interactable> m_objects; 
	private boolean[][] m_blocked; // 2D array indicating spaces that are blocked
    private static final int SIZE = 64; // block size
    private boolean m_isPaused = false;
    private PauseMenu m_pauseMenu;
	
	public Room(int stateID) {
		m_stateID = stateID;
	}

	public void init(GameContainer container, StateBasedGame stateManager) throws SlickException {
		try {
			m_horseMap = new TiledMap("assets/10X10.tmx");
		} catch (SlickException e) {
			System.out.println("ERROR: Could not 10X10.tmx");
		}
        m_blocked = new boolean[m_horseMap.getWidth()][m_horseMap.getHeight()];
        System.out.println("width: " + m_horseMap.getWidth());

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
		// setup menu
		m_pauseMenu = new PauseMenu();
	}

	@Override
	public void update(GameContainer container, StateBasedGame stateManager, int delta) throws SlickException {
		if (!m_isPaused)
			m_player.update(container, delta, SIZE);
		
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
	
	@Override
	public void render(GameContainer container, StateBasedGame stateManager, Graphics g) throws SlickException {
		m_horseMap.render(0, 0);
		m_chest.getImage().draw(m_chest.getX(), m_chest.getY());
		m_player.getAnimation().draw((int)m_player.getX(), (int)m_player.getY());
		
		if (m_isPaused) {
			Rectangle rectangle = new Rectangle(m_pauseMenu.getX(), 
												m_pauseMenu.getY(), 
												m_pauseMenu.getWidth(), 
												m_pauseMenu.getHeight());
			ShapeFill fill = new GradientFill(m_pauseMenu.getX(), 
											  m_pauseMenu.getY(), 
											  Color.black, 
											  m_pauseMenu.getX() + m_pauseMenu.getWidth(), 
											  m_pauseMenu.getY() + m_pauseMenu.getHeight(), 
											  Color.black);
			
			g.fill(rectangle, fill);
		}
	}
	
	public void interact(int[] interactSquare){
		for(Interactable i: m_objects){
			int[] loc = i.getSquare();
			System.out.println(interactSquare[0] + " loc x " + loc[0] + " square y " + interactSquare[1] + " location x " + loc[1]);
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
