package game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;

public class Inventory {
	private Font m_font;
	private int m_width, m_height;
	private int m_x, m_y;
	private Rectangle m_rectangle;
	private GradientFill m_shapeFill;
	
	private Collectable[][] m_items;
	private int m_pointer = 0;
	private int m_inputDelta = 0;
	
	private Image m_cursor;
	
	public Inventory(GameContainer container) throws SlickException {
		// set up box to display text in
		m_font = container.getDefaultFont();
		m_width = 448;
		m_height = 320;
		m_x = 78;
		m_y = 150;
		
		m_rectangle = new Rectangle(m_x, m_y, m_width, m_height);
		m_shapeFill = new GradientFill(m_x, m_y, Color.white, // top left corner
									   m_x + m_width, m_y + m_height, Color.white); // bottom right
		
		// set up inventory logic
		m_items = new Collectable[4][4];
		
		// load sprites
		m_cursor = new Image("assets/redSquare.png");
	}
	
	public void update(GameContainer container, int delta) {
		Input input = container.getInput();
		m_inputDelta-=delta;	
		if (m_inputDelta<0 && input.isKeyDown(Input.KEY_UP)) {
			if (m_pointer >= 4)
				m_pointer = m_pointer - 4;
			m_inputDelta = 200;
        }
        else if (m_inputDelta<0 && input.isKeyDown(Input.KEY_DOWN)) {
        	if (m_pointer >= 12)
        		m_pointer = m_pointer + 4;
        	m_inputDelta = 200;
        }
        else if (m_inputDelta<0 && input.isKeyDown(Input.KEY_LEFT)) {
        	if (m_pointer % 4 == 3)
        		m_pointer--;
        	m_inputDelta = 200;
        }
        else if (m_inputDelta<0 && input.isKeyDown(Input.KEY_RIGHT)) {
        	if (m_pointer % 4 == 0)
        		m_pointer++;
        	m_inputDelta = 200;
        }
        if(m_inputDelta<0&&input.isKeyDown(Input.KEY_SPACE)) {
        	m_inputDelta = 200;
        }	
	}
	
	public void render(Graphics g) {
		g.fill(m_rectangle, m_shapeFill);
		g.setColor(Color.black);
	}
}
