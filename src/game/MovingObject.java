package game;

public class MovingObject extends GameObject{
	protected Room m_room;
	protected static final int SIZE = 64;
	protected static final int BUFFER = 14;
	protected float m_x, m_y;
	public float getX() { return m_x; }
	public void setX(float x) { m_x = x; }
	public float getY() { return m_y; }
	public void setY(float y) { m_y = y; }
	public MovingObject(Room room){
		m_room = room;
	}
	protected boolean isBlocked(float x, float y, Direction dir) {
    	switch(dir){
			case UP: {
				int xBlock1 = ((int)x +BUFFER) / SIZE;
		        int yBlock = (int)y / SIZE;
		        int xBlock2 = ((int)x + SIZE-BUFFER)/SIZE;
		        return m_room.blocked(xBlock1, yBlock)|m_room.blocked(xBlock2, yBlock);
			}
			case DOWN: {
				int xBlock1 = ((int)x +BUFFER) / SIZE;
		        int yBlock = (int)y / SIZE;
		        int xBlock2 = ((int)x + SIZE-BUFFER)/SIZE;
		        return m_room.blocked(xBlock1, yBlock)|m_room.blocked(xBlock2, yBlock);
			}
			case LEFT: {
				int xBlock = (int)x / SIZE;
		        int yBlock1 = ((int)y +BUFFER)/ SIZE;
		        int yBlock2 = ((int) y +SIZE - BUFFER)/SIZE;
		        return m_room.blocked(xBlock, yBlock1)||m_room.blocked(xBlock, yBlock2);
			}
			case RIGHT: {
				int xBlock = (int)x / SIZE;
		        int yBlock1 = ((int)y +BUFFER)/ SIZE;
		        int yBlock2 = ((int) y +SIZE - BUFFER)/SIZE;
		        return m_room.blocked(xBlock, yBlock1)||m_room.blocked(xBlock, yBlock2);
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
}
