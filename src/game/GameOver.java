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
	public GameOver(int stateID){
		m_stateID = stateID;
		System.out.println("game over state entered");
	}
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
			System.out.println("game over");
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame stateManager, int delta)
			throws SlickException {
		     m_inputDelta+= delta;
		     if(delta>1000){
		    	 System.out.println("reset");
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
