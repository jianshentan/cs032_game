package game;

import org.newdawn.slick.Image;

public abstract class GameObject {
	
	protected Image m_sprite;
	public static final int SIZE = 64;
	protected float m_x, m_y;
	
	public float getX() {return m_x;}
	public float getY() {return m_y;}
	public void setX(float x) { m_x = x; }
	public void setY(float y) { m_y = y; }
	public Image getImage() {return m_sprite;}	
	
	public abstract int[] getSquare();
	public abstract Types getType();
	
	public enum Types {
		CHEST,
		CHICKEN_WING,
		CIGARETTE,
	}
}
