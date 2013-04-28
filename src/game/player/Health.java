package game.player;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;

public class Health {
	
	private final float m_x;
    private final float m_y;
	private final float m_width;
	private final float m_height;
	private final int MAXHEALTH;
	private final float INTERVAL; 
	private int m_current;
	/**
	 * Returns the current health.
	 * @return
	 */
	public int getCurrentHealth() { return m_current; }
	private Image m_sprite, m_health; 
	public Image getImage() { return m_sprite; }
	
	public Health(float x, float y, int max) throws SlickException {
		m_x = x;
		m_y = y;
		m_width = 121;
		m_height = 30;
		
		m_health = new Image("assets/brainHealth.png");
		m_sprite = m_health;
		
		MAXHEALTH = max;
		INTERVAL = m_width / MAXHEALTH; 
		m_current = MAXHEALTH;
	}
	
	public void updateHealth(int update) {
		m_current += update;
		if (m_current > MAXHEALTH) {
			m_current = MAXHEALTH;
		}
		if (m_current <= 0) {
			m_current = 0;
		}
		
		m_sprite = m_health.getSubImage(0,0,(int)(m_current*INTERVAL),(int)m_height);
	}
	
	public void render() { 
		m_sprite.draw(m_x,m_y);
	}
	
	

}
