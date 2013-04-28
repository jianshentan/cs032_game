package game;

import game.gameplayStates.GamePlayState;
import game.player.Player;

import org.newdawn.slick.*;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;

public class Enemy extends MovingObject{
	private Animation m_up, m_down, m_left, m_right, m_sprite;
	private AIState m_ai;
	private boolean m_inTransit;
	private int[][] m_patrolPoints;
	private int[] m_currentSquare, m_destination;
	private AStarPathFinder m_finder;
	private Path m_path;
	private int m_pathLength, m_currentStep, m_roamCounter;
	//private Player m_player;
	public Animation getAnimation(){return m_sprite;};
	public  Enemy(GamePlayState room, Player player, float x, float y, int[][] patrolPoints) throws SlickException{
		super(room);
		//m_player = player;
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
        m_ai = AIState.PATROL;
        m_inTransit = false;
	}
	public void update(int delta){
		if(m_inTransit){
			//when you've moved a square
			if(Math.abs(m_x/64-m_currentSquare[0])>=1||Math.abs(m_y/64-m_currentSquare[1])>=1){
				//the new current is now the old destination
				setCurrent(m_currentStep);
				m_currentStep+=1;
				if(m_currentStep>=m_pathLength){
				//	System.out.println("stopped");
					m_inTransit = false;
				}else{
					//System.out.println(m_currentStep + " "  + m_pathLength);
					setDestination();
				}
			}
			//System.out.println(m_currentSquare[0] + " " + m_destination[0]);
			int x = m_destination[0]-m_currentSquare[0];
			int y = m_destination[1]-m_currentSquare[1];
			if(!checkCollision(this, m_game.getPlayer())){
				m_x+= x * delta*0.1f;
				m_y+= y * delta*0.1f;
			}
			
		}else{
			switch(m_ai){

				case PATROL:{
					patrolUpdate();
					break;
				}
				case HUNT:{
					huntUpdate();
					break;
				}default:{
					
				}
			}
		}
	}
	public void patrolUpdate(){
		//finds the path from current point to next point in the roam list
		if(m_roamCounter>=m_patrolPoints.length){
			m_roamCounter=0;
		}
		int xDest = m_patrolPoints[m_roamCounter][0];
		int yDest = m_patrolPoints[m_roamCounter][1];
		if(xDest==m_currentSquare[0]&&yDest==m_currentSquare[1]){
			m_roamCounter+=1;
			patrolUpdate();
		}else{
			m_path = m_finder.findPath(null, m_currentSquare[0], m_currentSquare[1], xDest, yDest );
			m_pathLength = m_path.getLength();
			m_currentStep=1;
			setDestination();
			m_inTransit=true;
		}
	}

	public void huntUpdate(){
		System.out.println("updated");
		int playerX = (int)((m_game.getPlayer().getX()+SIZE/2)/SIZE);
		int playerY = (int)((m_game.getPlayer().getY()+SIZE/2)/SIZE);
		if(m_currentSquare[0]==playerX&&m_currentSquare[1]==playerY){
			return;
		}
		m_path = m_finder.findPath(null, m_currentSquare[0], m_currentSquare[1], playerX, playerY);
		m_pathLength = m_path.getLength()/2;
		m_currentStep=1;
		setDestination();
		m_inTransit = true;
		
	}
	private void setDestination(){
		m_destination[0] = m_path.getX(m_currentStep);
		m_destination[1] = m_path.getY(m_currentStep);
		updateSprite();
	}
	private void setCurrent( int i){
		m_currentSquare[0] = m_path.getX(i);
		m_currentSquare[1] = m_path.getY(i);
	}
	private void updateSprite(){
		int xDiff = m_destination[0]-m_currentSquare[0];
		int yDiff = m_destination[1]-m_currentSquare[1];
		if(xDiff>0){
			m_sprite=m_right;
		}else if(xDiff<0){
			m_sprite=m_left;
		}else if(yDiff>0){
			m_sprite=m_down;
		}else if(yDiff<0){
			m_sprite = m_up;
		}
	}
}
