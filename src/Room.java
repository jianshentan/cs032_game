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
	private Animation sprite, up, down, left, right;	
	private float x = 128f, y = 128f;
	
	// The collision map indicating which tiles block movement - generated 
	private boolean[][] blocked;
    private static final int SIZE = 34;
	
	public Room() {
		super ("Room");
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		m_horseMap.render(-100, -100);
		sprite.draw((int)x, (int)y);
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
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
        up = new Animation(movementUp, duration, false);
        down = new Animation(movementDown, duration, false);
        left = new Animation(movementLeft, duration, false);
        right = new Animation(movementRight, duration, false);
        
        // Original orientation of the sprite. It will look right.
        sprite = right;
        
        // build a collision map based on tile properties in the TileD map
        blocked = new boolean[m_horseMap.getWidth()][m_horseMap.getHeight()];

       for (int xAxis=0;xAxis<m_horseMap.getWidth(); xAxis++) {
            for (int yAxis=0;yAxis<m_horseMap.getHeight(); yAxis++) {
                int tileID = m_horseMap.getTileId(xAxis, yAxis, 0);
                String value = m_horseMap.getTileProperty(tileID, "blocked", "false");
                if ("true".equals(value)) {
                    blocked[xAxis][yAxis] = true;
                }
            }
        }
      
		
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		Input input = container.getInput();
        if (input.isKeyDown(Input.KEY_UP)) {
            sprite = up;
            if (!isBlocked(x, y - delta * 0.1f)) {
                sprite.update(delta);
                // The lower the delta the slowest the sprite will animate.
                y -= delta * 0.1f;
            }
        }
        else if (input.isKeyDown(Input.KEY_DOWN)) {
            sprite = down;
            if (!isBlocked(x, y + SIZE + delta * 0.1f)) {
                sprite.update(delta);
                y += delta * 0.1f;
            }
        }
        else if (input.isKeyDown(Input.KEY_LEFT)) {
            sprite = left;
            if (!isBlocked(x - delta * 0.1f, y)) {
                sprite.update(delta);
                x -= delta * 0.1f;
            }
        }
        else if (input.isKeyDown(Input.KEY_RIGHT)) {
            sprite = right;
            if (!isBlocked(x + SIZE + delta * 0.1f, y)) {
                sprite.update(delta);
                x += delta * 0.1f;
            }
        }	
		
	}
	
    private boolean isBlocked(float x, float y) {
        int xBlock = (int)x / SIZE;
        int yBlock = (int)y / SIZE;
        return blocked[xBlock][yBlock];
    }
	
	
}
