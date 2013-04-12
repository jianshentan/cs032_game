package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Player extends GameObject{
	
	private float m_x, m_y;
	public float getX() { return m_x; }
	public void setX(float x) { m_x = x; }
	public float getY() { return m_y; }
	public void setY(float y) { m_y = y; }
	
	private Animation m_up, m_down, m_left, m_right, m_sprite;
	
	public Player(float x, float y) throws SlickException {
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
		
	}
}
