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
	private int[] m_currentSquare, m_destination;
	private AStarPathFinder m_finder;
	private Path m_path;
	private int m_pathLength, m_currentStep, m_roamCounter;
	
	public Animation getAnimation(){return m_sprite;};
	public  Enemy(Room room, float x, float y, int[][] patrolPoints) throws SlickException{
		super(room);
		m_x = x;
		m_y = y;
		m_currentSquare = new int[2];
		m_destination = new int[2];
		m_currentSquare[0] =(int) (m_x/SIZE);
		m_currentSquare[1] = (int) (m_y/SIZE);
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
			if(Math.abs(m_x/64-m_currentSquare[0])>=1||Math.abs(m_y/64-m_currentSquare[1])>=1){
				setCurrent(m_currentStep);
				m_currentStep+=1;
				System.out.println(m_currentStep);
				if(m_currentStep==m_pathLength){
					System.out.println("stopped");
					m_inTransit = false;
				}else{
					setDestination();
				}
			}
			System.out.println(m_currentSquare[0] + " " + m_destination[0]);
			int x = m_destination[0]-m_currentSquare[0];
			int y = m_destination[1]-m_currentSquare[1];
			m_x+= x * delta*0.1f;
			m_y+= y * delta*0.1f;
			
		}else{
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
		if(m_roamCounter>=m_patrolPoints.length){
			m_roamCounter=0;
		}
		int xDest = m_patrolPoints[m_roamCounter][0];
		int yDest = m_patrolPoints[m_roamCounter][1];
		if(xDest==m_currentSquare[0]&&yDest==m_currentSquare[1]){
			m_roamCounter+=1;
			roamUpdate();
		}else{
			m_path = m_finder.findPath(null, m_currentSquare[0], m_currentSquare[1], xDest, yDest );
			m_pathLength = m_path.getLength();
			for(int i =0; i< m_pathLength; i++){
				//System.out.println("path " + i + " is " + m_path.getX(i) + ", " + m_path.getY(i));
			}
			m_currentStep=1;
			setDestination();
			m_inTransit=true;
		}
	}
	public void patrolUpdate(){
		
	}
	public void huntUpdate(){
		
	}
	private void setDestination(){
		m_destination[0] = m_path.getX(m_currentStep);
		m_destination[1] = m_path.getY(m_currentStep);
	}
	private void setCurrent( int i){
		m_currentSquare[0] = m_path.getX(i);
		m_currentSquare[1] = m_path.getY(i);
	}
}
