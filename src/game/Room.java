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
	
	private TiledMap m_horseMap;
	private Player m_player;
	private Animation m_sprite, m_up, m_down, m_left, m_right;	
	private float m_x = 256f, m_y = 256f;
	
	// The collision map indicating which tiles block movement - generated 
	private boolean[][] m_blocked;
    private static final int SIZE = 64;
	
	public Room() {
		super ("Room");
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		m_horseMap.render(0, 0);
		m_sprite.draw((int)m_player.getX(), (int)m_player.getY());
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		// setup player
		m_player = new Player(m_x, m_y);
		
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
    // The lower the delta the slowest the sprite will animate.
		Input input = container.getInput();
        if (input.isKeyDown(Input.KEY_UP)) {
            m_sprite = m_up;
            if (!isBlocked(m_x, m_y - delta * 0.1f)) {
                m_sprite.update(delta);
                m_y -= delta * 0.1f;
            }
        }
        else if (input.isKeyDown(Input.KEY_DOWN)) {
            m_sprite = m_down;
            if (!isBlocked(m_x, m_y + SIZE + delta * 0.1f)) {
                m_sprite.update(delta);
                m_y += delta * 0.1f;
            }
        }
        else if (input.isKeyDown(Input.KEY_LEFT)) {
            m_sprite = m_left;
            if (!isBlocked(m_x - delta * 0.1f, m_y)) {
                m_sprite.update(delta);
                m_x -= delta * 0.1f;
            }
        }
        else if (input.isKeyDown(Input.KEY_RIGHT)) {
            m_sprite = m_right;
            if (!isBlocked(m_x + SIZE + delta * 0.1f, m_y)) {
                m_sprite.update(delta);
                m_x += delta * 0.1f;
            }
        }	
		
	}
	
    private boolean isBlocked(float x, float y) {
        int xBlock = (int)x / SIZE;
        int yBlock = (int)y / SIZE;
        return m_blocked[xBlock][yBlock];
    }
	
	
}
