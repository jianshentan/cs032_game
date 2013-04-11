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
	private Animation m_sprite, m_up, m_down, m_left, m_right;	
	private float m_x = 128f, m_y = 128f;
	
	// The collision map indicating which tiles block movement - generated 
	private boolean[][] m_blocked;
    private static final int SIZE = 32;
	
	public Room() {
		super ("Room");
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		m_horseMap.render(0, 0);
		m_sprite.draw((int)m_x, (int)m_y);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		Image [] movementUp = {new Image("assets/Sprite1Back.png"), new Image("assets/Sprite1Back.png")};
        Image [] movementDown = {new Image("assets/Sprite1Front.png"), new Image("assets/Sprite1Front.png")};
        Image [] movementLeft = {new Image("assets/Sprite1Left.png"), new Image("assets/Sprite1Left.png")};
        Image [] movementRight = {new Image("assets/Sprite1Right.png"), new Image("assets/Sprite1Right.png")};
        int [] duration = {300, 300}; 
         
		try {
			m_horseMap = new TiledMap("assets/testmap.tmx");
		} catch (SlickException e) {
			System.out.println("ERROR: Could not open testmap.tmx");
		}
		
        /*
         * false variable means do not auto update the animation.
         * By setting it to false animation will update only when
         * the user presses a key.
         */
        m_up = new Animation(movementUp, duration, false);
        m_down = new Animation(movementDown, duration, false);
        m_left = new Animation(movementLeft, duration, false);
        m_right = new Animation(movementRight, duration, false);
        
        // Original orientation of the sprite. It will look right.
        m_sprite = m_right;
        
        // build a collision map based on tile properties in the TileD map
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
      
		
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		Input input = container.getInput();
        if (input.isKeyDown(Input.KEY_UP)) {
            m_sprite = m_up;
            if (!isBlocked(m_x, m_y - delta * 0.1f)) {
                m_sprite.update(delta);
                // The lower the delta the slowest the sprite will animate.
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
