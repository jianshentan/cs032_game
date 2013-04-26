package game;

import org.newdawn.slick.*;

public class Enemy extends MovingObject{
	private Animation m_up, m_down, m_left, m_right, m_sprite;
	private Direction m_dir;
	private AIState m_ai;
	private boolean m_inTransit;
	public Animation getAnimation(){return m_sprite;};
	public  Enemy(Room room, float x, float y) throws SlickException{
		super(room);
		m_x = x;
		m_y = y;
		
		Image [] movementUp = {new Image("assets/Sprite2Back.png"), new Image("assets/Sprite2Back.png")};
        Image [] movementDown = {new Image("assets/Sprite2Front.png"), new Image("assets/Sprite2Front.png")};
        Image [] movementLeft = {new Image("assets/Sprite2Left.png"), new Image("assets/Sprite2Left.png")};
        Image [] movementRight = {new Image("assets/Sprite2Right.png"), new Image("assets/Sprite2Right.png")};
        int [] duration = {300, 300}; 
        
        m_up = new Animation(movementUp, duration, false);
        m_down = new Animation(movementDown, duration, false);
        m_left = new Animation(movementLeft, duration, false);
        m_right = new Animation(movementRight, duration, false);	
        
        // Original orientation of the sprite. It will look right.
        m_sprite = m_right;
        m_dir = Direction.RIGHT;
        m_ai = AIState.PATROL;
        m_inTransit = false;
	}
	public void update(int delta){
		switch(m_ai){
			case ROAM: {
				
			}
			case PATROL:{
				
			}
			case HUNT:{
				
			}
		}
	}
	public void roamUpdate(){
		switch(m_dir){
		case UP:{
			
		}
		case DOWN:{
			
		}
		case LEFT:{
			
		}
		case RIGHT:{
			
		}default:{
			
		}
		}
	}
	public void patrolUpdate(){
		
	}
	public void huntUpdate(){
		
	}
}
