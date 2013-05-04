package game;



import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOver extends BasicGameState {
	//TODO- display picture in background
	private int m_inputDelta = 0;
	private int m_stateID = 0;
	private float m_x = 0;
    private float m_y = 0;
	private float m_width = 600;
	private float m_height = 600;
 	private Rectangle m_rectangle;
	private ShapeFill m_shapeFill;
	private Image m_background = null;
	private TrueTypeFont m_gameOverFont;
	public GameOver(int stateID){
		m_stateID = stateID;
		//System.out.println("game over state entered");
		m_rectangle = new Rectangle(m_x, m_y, m_width, m_height);
		m_shapeFill = new GradientFill(m_x, m_y, Color.white,
									   m_x + m_width, m_y + m_height, Color.white);
		Font font = new Font("SansSerif", Font.BOLD, 40);
		m_gameOverFont = new TrueTypeFont(font, false);
	}
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		m_background = new Image("assets/GameOverMaggot.jpg");
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		m_background.draw(0,0);
		g.setColor(Color.pink);
		g.setFont(m_gameOverFont);
		g.drawString("GAME OVER", m_width/2-125, m_height/2);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame stateManager, int delta)
			
			throws SlickException {
		     m_inputDelta+= delta;
		     if(m_inputDelta>5000){
		    	 StateManager s = (StateManager) stateManager;
		    	 s.reset();
		     }
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return m_stateID;
	}

}
