package game;
import org.newdawn.slick.Animation;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Room extends BasicGame{
	public enum Direction {UP, DOWN, LEFT, RIGHT}
	private TiledMap m_horseMap;
	private Player m_player;
	
	// The collision map indicating which tiles block movement - generated 
	private boolean[][] m_blocked;
	// block size
    private static final int SIZE = 64;
	
	public Room() {
		super ("Room");
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		m_horseMap.render(0, 0);
		m_player.getAnimation().draw((int)m_player.getX(), (int)m_player.getY());
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		// setup player
		m_player = new Player(this, 256f, 256f);
		
		try {
			m_horseMap = new TiledMap("assets/10X10.tmx");
		} catch (SlickException e) {
			System.out.println("ERROR: Could not 10X10.tmx");
		}
        
        // build a collision map based on tile properties in the TileD map
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
		
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		m_player.update(container, delta, SIZE);
	}
	

    public boolean getBlocked(int x, int y) {
    	return m_blocked[x][y];

    }
	
	
}
