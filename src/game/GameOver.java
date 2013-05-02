package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOver extends BasicGameState {
	private int m_stateID = 0;
	private float m_x = 0;
    private float m_y = 0;
	private float m_width = 600;
	private float m_height = 600;
 	private Rectangle m_rectangle;
	private ShapeFill m_shapeFill;
	
	private int m_selection = 0;
	private int m_inputDelta = 0;
	
	private Image m_background = null;
	private Image m_startButton = null;
	private static int m_startButtonX = 500;
	private static int m_startButtonY = 100;
	public GameOver(int stateID){
		
	}
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
