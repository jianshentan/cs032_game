package game;

import game.Interactables.Types;

import org.newdawn.slick.Image;

public abstract class GameObject {
	
	public static enum Types {
		CHEST,
		CHICKEN_WING,
	}
	
	protected Image m_sprite;
	public static final int SIZE = 64;
	protected int m_x, m_y;
	
	public float getX() {return m_x;}
	public float getY() {return m_y;}
	public Image getImage() {return m_sprite;}	
	
	public abstract int[] getSquare();
	public abstract Types getType();
	
}
