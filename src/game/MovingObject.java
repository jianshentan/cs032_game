package game;

import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;

import game.gameplayStates.GamePlayState;
import game.player.Player;

public class MovingObject extends GameObject{
	protected GamePlayState m_game;
	protected static final int SIZE = 64;
	protected static final int BUFFER = 14;
	protected static final int RAD = 30;
	protected float m_x, m_y;
	private int[] m_target;
	private boolean m_sceneMode; //this is true if the object is in scene mode.
	public boolean getSceneMode() { return this.m_sceneMode; }
	public void setSceneMode(boolean mode) { this.m_sceneMode = mode; }
	
	//stuff for implementing scenes - scripted movements
	private int[][] m_patrolPoints;
	private int[] m_currentSquare, m_destination;
	private AStarPathFinder m_finder;
	private Path m_path;
	private int m_pathLength, m_currentStep, m_roamCounter;
	
	public void setTarget(int[] pos) { this.m_target = pos; }
	public float getX() { return m_x; }
	public void setX(float x) { m_x = x; }
	public float getY() { return m_y; }
	public void setY(float y) { m_y = y; }
	
	public MovingObject(GamePlayState game){
		m_game = game;
		m_currentSquare = new int[2];
		m_destination = new int[2];
		m_currentSquare[0] =(int) (m_x/SIZE);
		m_currentSquare[1] = (int) (m_y/SIZE);
	}
	/**
	 * Moves the MovingObject in the given direction, with the given
	 * delta.
	 * @param dir
	 * @param delta
	 */
	protected void move(Direction dir, int delta) {
		if(dir==Direction.DOWN) {
			if (!isBlocked(m_x, m_y + SIZE + delta * 0.1f, dir)) {
	            m_y += delta * 0.1f;
	        }
		} else if(dir==Direction.LEFT) {
			if (!isBlocked(m_x - delta * 0.1f, m_y, Direction.LEFT)) {
                m_x -= delta * 0.1f;
            }
		} else if(dir==Direction.RIGHT) {
			if (!isBlocked(m_x + SIZE + delta * 0.1f, m_y, Direction.RIGHT)) {
                m_x += delta * 0.1f;
            }
		} else if(dir==Direction.UP) {
			if (!isBlocked(m_x, m_y - delta * 0.1f, Direction.UP)) {
            	m_y -= delta * 0.1f;
            }
		}
	}
	
	/**
	 * Makes this MovingObject follow a certain path.
	 * @param room
	 * @param patrolPoints
	 */
	public void enterScene(GamePlayState room, int[][] patrolPoints) {
		m_sceneMode = true;
		m_game = room;
		m_currentSquare = new int[2];
		m_destination = new int[2];
		m_currentSquare[0] =(int) (m_x/SIZE);
		m_currentSquare[1] = (int) (m_y/SIZE);
		m_patrolPoints = patrolPoints;
		m_currentStep=0;
		m_finder = new AStarPathFinder(room.getMap(), 50, false);
		int xDest = m_patrolPoints[m_roamCounter][0];
		int yDest = m_patrolPoints[m_roamCounter][1];
		m_path = m_finder.findPath(null, m_currentSquare[0], m_currentSquare[1], xDest, yDest );
		m_currentStep = 1;
		m_pathLength = m_path.getLength();
		setDestination();
		//System.out.println(m_pathLength);
	}
	
	/**
	 * Updates with a target
	 * @param delta
	 */
	public void update(int delta){
		//System.out.println(m_path);
		if(m_sceneMode){
			//when you've moved a square
			if(Math.abs(m_x/64-m_currentSquare[0])>=1||Math.abs(m_y/64-m_currentSquare[1])>=1){
				//the new current is now the old destination
				setCurrent(m_currentStep);
				m_currentStep+=1;
				if(m_currentStep>=m_pathLength){
					m_sceneMode = false;
					this.patrolUpdate();
				}else{
					//System.out.println(m_currentStep + " "  + m_pathLength);
					setDestination();
				}
			}
			int x = m_destination[0]-m_currentSquare[0];
			int y = m_destination[1]-m_currentSquare[1];
			m_x+= x * delta*0.1f;
			m_y+= y * delta*0.1f;
		}
	}
	private void setDestination(){
		m_destination[0] = m_path.getX(m_currentStep);
		m_destination[1] = m_path.getY(m_currentStep);
		//updateSprite();
	}
	private void setCurrent( int i){
		m_currentSquare[0] = m_path.getX(i);
		m_currentSquare[1] = m_path.getY(i);
	}
	private void patrolUpdate(){
		//finds the path from current point to next point in the roam list
		if(m_roamCounter>=m_patrolPoints.length){
			return;
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
			m_sceneMode=true;
		}
	}
	
	protected boolean isBlocked(float x, float y, Direction dir) {
    	switch(dir){
			case UP: {
				int xBlock1 = ((int)x +BUFFER) / SIZE;
		        int yBlock = (int)y / SIZE;
		        int xBlock2 = ((int)x + SIZE-BUFFER)/SIZE;
		        return m_game.blocked(xBlock1, yBlock)|m_game.blocked(xBlock2, yBlock);
			}
			case DOWN: {
				int xBlock1 = ((int)x +BUFFER) / SIZE;
		        int yBlock = (int)y / SIZE;
		        int xBlock2 = ((int)x + SIZE-BUFFER)/SIZE;
		        return m_game.blocked(xBlock1, yBlock)|m_game.blocked(xBlock2, yBlock);
			}
			case LEFT: {
				int xBlock = (int)x / SIZE;
		        int yBlock1 = ((int)y +BUFFER)/ SIZE;
		        int yBlock2 = ((int) y +SIZE - BUFFER)/SIZE;
		        return m_game.blocked(xBlock, yBlock1)||m_game.blocked(xBlock, yBlock2);
			}
			case RIGHT: {
				int xBlock = (int)x / SIZE;
		        int yBlock1 = ((int)y +BUFFER)/ SIZE;
		        int yBlock2 = ((int) y +SIZE - BUFFER)/SIZE;
		        return m_game.blocked(xBlock, yBlock1)||m_game.blocked(xBlock, yBlock2);
			} default: {
				System.out.println("ERROR WHRE IS THIS " + dir + " ENUM COMING FROM");
				return false;
			}
		}
	}
	@Override
	public int[] getSquare() {
		// TODO Auto-generated method stub
		return new int[] {(int) (m_x/SIZE), (int) (m_y/SIZE)};
	}
	@Override
	public Types getType() {
		// TODO Auto-generated method stub
		return null;
	}
	protected boolean checkCollision(MovingObject mo1, MovingObject mo2){
		if(mo1.m_game!=mo2.m_game)
			return false;
		float xDist = mo1.getX()-mo2.getX();
		float yDist = mo1.getY()-mo2.getY();
		float radSum = 2*RAD;
		return radSum*radSum>xDist*xDist+yDist*yDist;
	}
}
