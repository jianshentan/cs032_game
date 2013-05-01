package game;

import game.gameplayStates.GamePlayState;

public class MovingObject extends GameObject{
	protected GamePlayState m_game;
	protected static final int SIZE = 64;
	protected static final int BUFFER = 14;
	protected static final int RAD = 30;
	protected float m_x, m_y;
	public float getX() { return m_x; }
	public void setX(float x) { m_x = x; }
	public float getY() { return m_y; }
	public void setY(float y) { m_y = y; }
	
	public MovingObject(GamePlayState game){
		m_game = game;
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
		return null;
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
