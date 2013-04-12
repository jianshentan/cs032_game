package game;


import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Chest extends GameObject implements Interactable{
	public static final int SIZE = 64;
	private Image m_sprite, m_open, m_closed;
	private int m_x, m_y;
	public int getX() { return m_x; }
	public int getY() { return m_y; }
	public Image getImage(){return m_sprite;}
	
	public Chest(int xLoc, int yLoc) throws SlickException{
		m_x = xLoc;
		m_y = yLoc;
		m_closed = new Image("assets/chestClose.png");
		m_open = new Image("assets/chestOpen.png");
		m_sprite = m_closed;
	}

	@Override
	public void fireAction() {
		if(m_sprite.equals(m_closed)){
			m_sprite = m_open;
		}else{
			m_sprite = m_closed;
		}
		
	}
	@Override
	public int[] getSquare() {
		int[] loc = {m_x/SIZE, m_y/SIZE};
		return loc;
	}
}
