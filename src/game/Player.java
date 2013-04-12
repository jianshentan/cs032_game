package game;



import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player extends GameObject{
	
	private Room m_room;
	private float m_x, m_y;
	private int inputDelta = 0;
	private Animation m_up, m_down, m_left, m_right, m_sprite;
	private Direction m_dir;
    private static final int SIZE = 64;
	private static final int BUFFER = 14;
	public Animation getAnimation() { return m_sprite; }
	public void setAnimation(Animation animation) { m_sprite = animation; }
	public float getX() { return m_x; }
	public void setX(float x) { m_x = x; }
	public float getY() { return m_y; }
	public void setY(float y) { m_y = y; }
	
	public Player(Room room, float x, float y) throws SlickException {
		m_room = room;
		m_x = x;
		m_y = y;
		
		Image [] movementUp = {new Image("assets/Sprite1Back.png"), new Image("assets/Sprite1Back.png")};
        Image [] movementDown = {new Image("assets/Sprite1Front.png"), new Image("assets/Sprite1Front.png")};
        Image [] movementLeft = {new Image("assets/Sprite1Left.png"), new Image("assets/Sprite1Left.png")};
        Image [] movementRight = {new Image("assets/Sprite1Right.png"), new Image("assets/Sprite1Right.png")};
        int [] duration = {300, 300}; 
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
        m_dir = Direction.RIGHT;
	}
	
	public void update(GameContainer container, int delta, int SIZE) {
		// The lower the delta the slowest the sprite will animate.
		Input input = container.getInput();
		inputDelta-=delta;
        if (input.isKeyDown(Input.KEY_UP)) {
        	m_sprite = m_up;
        	m_dir = Direction.UP;
            if (!isBlocked(m_x, m_y - delta * 0.1f, Direction.UP)) {
            	m_sprite.update(delta);
            	m_y -= delta * 0.1f;
            }
        }
        else if (input.isKeyDown(Input.KEY_DOWN)) {
            m_sprite = m_down;
            m_dir = Direction.DOWN;
            if (!isBlocked(m_x, m_y + SIZE + delta * 0.1f, Direction.DOWN)) {
                m_sprite.update(delta);
                m_y += delta * 0.1f;
            }
        }
        else if (input.isKeyDown(Input.KEY_LEFT)) {
            m_sprite = m_left;
            m_dir = Direction.LEFT;
            if (!isBlocked(m_x - delta * 0.1f, m_y, Direction.LEFT)) {
                m_sprite.update(delta);
                m_x -= delta * 0.1f;
            }
        }
        else if (input.isKeyDown(Input.KEY_RIGHT)) {
            m_sprite = m_right;
            m_dir = Direction.RIGHT;
            if (!isBlocked(m_x + SIZE + delta * 0.1f, m_y, Direction.RIGHT)) {
                m_sprite.update(delta);
                m_x += delta * 0.1f;
            }
        }
        if(inputDelta<0&&input.isKeyDown(Input.KEY_SPACE)){
        	int currentX = (int) (m_x + (SIZE/2))/SIZE;
        	int currentY = (int) (m_y + (SIZE/2))/SIZE;
        	int[] dirOffset = Direction.getDirOffsets(m_dir);
        	int[] squareFacing = {currentX + dirOffset[0], currentY + dirOffset[1]};
        	m_room.interact(squareFacing);
        	inputDelta=500;
        }
		
	}
	
	public boolean isBlocked(float x, float y, Direction dir) {
    	switch(dir){
			case UP: {
				int xBlock1 = ((int)x +BUFFER) / SIZE;
		        int yBlock = (int)y / SIZE;
		        int xBlock2 = ((int)x + SIZE-BUFFER)/SIZE;
		        return m_room.getBlocked(xBlock1, yBlock)|m_room.getBlocked(xBlock2, yBlock);
			}
			case DOWN: {
				int xBlock1 = ((int)x +BUFFER) / SIZE;
		        int yBlock = (int)y / SIZE;
		        int xBlock2 = ((int)x + SIZE-BUFFER)/SIZE;
		        return m_room.getBlocked(xBlock1, yBlock)|m_room.getBlocked(xBlock2, yBlock);
			}
			case LEFT: {
				int xBlock = (int)x / SIZE;
		        int yBlock1 = ((int)y +BUFFER)/ SIZE;
		        int yBlock2 = ((int) y +SIZE - BUFFER)/SIZE;
		        return m_room.getBlocked(xBlock, yBlock1)||m_room.getBlocked(xBlock, yBlock2);
			}
			case RIGHT: {
				int xBlock = (int)x / SIZE;
		        int yBlock1 = ((int)y +BUFFER)/ SIZE;
		        int yBlock2 = ((int) y +SIZE - BUFFER)/SIZE;
		        return m_room.getBlocked(xBlock, yBlock1)||m_room.getBlocked(xBlock, yBlock2);
			} default: {
				System.out.println("ERROR WHRE IS THIS " + dir + " ENUM COMING FROM");
				return false;
			}
		}
	}
}
