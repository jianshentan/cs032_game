package game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;

public class Inventory {
	private Font m_font;
	private int m_width, m_height;
	private int m_x, m_y;
	private Rectangle m_rectangle;
	private GradientFill m_shapeFill;
	
	
	public Inventory(GameContainer container) {
		// set up box to display text in
		m_font = container.getDefaultFont();
		m_width = container.getWidth(); // pause menu width is same as the screen width
		m_height = container.getHeight()/4; // pause menu height is half the screen height
		m_x = 0;
		m_y = 3 * m_height;
		
		m_rectangle = new Rectangle(m_x, m_y, m_width, m_height);
		m_shapeFill = new GradientFill(m_x, m_y, Color.white, // top left corner
									   m_x + m_width, m_y + m_height, Color.white); // bottom right
	}
}
