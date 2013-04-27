package game;

import org.newdawn.slick.*;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;

public class Enemy extends MovingObject{
	private Animation m_up, m_down, m_left, m_right, m_sprite;
	private Direction m_dir;
	private AIState m_ai;
	private boolean m_inTransit;
	private int[][] m_patrolPoints;
	private AStarPathFinder m_finder;
	private Path m_path;
	private int m_pathLength, m_currentStep;
	
	public Animation getAnimation(){return m_sprite;};
	public  Enemy(Room room, float x, float y, int[][] patrolPoints) throws SlickException{
		super(room);
		m_x = x;
		m_y = y;
		m_patrolPoints = patrolPoints;
		m_currentStep=0;
		m_path = null;
		m_finder = new AStarPathFinder(room.getMap(), 50, false);
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
        m_ai = AIState.ROAM;
        m_inTransit = false;
	}
	public void update(int delta){
		if(m_inTransit){
			m_x = m_path.getX(m_currentStep)*SIZE;
			m_y = m_path.getY(m_currentStep)*SIZE;
			m_currentStep+=1;
			if(m_currentStep == m_pathLength){
				m_inTransit = false;
			}
		}else{
			System.out.println("here");
			switch(m_ai){
				case ROAM: {
					roamUpdate();
				}
				case PATROL:{
					
				}
				case HUNT:{
					
				}
			}
		}
	}
	public void roamUpdate(){
		m_path = m_finder.findPath(null, 1, 1, 6, 6 );
		m_pathLength = m_path.getLength();
		for(int i =0; i< m_pathLength; i++){
			System.out.println("path " + i + " is " + m_path.getX(i) + ", " + m_path.getY(i));
		}
		m_inTransit=true;
		m_ai = AIState.PATROL;
	}
	public void patrolUpdate(){
		
	}
	public void huntUpdate(){
		
	}
}
