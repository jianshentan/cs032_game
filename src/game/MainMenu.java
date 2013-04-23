package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenu extends BasicGameState {
	
	private int m_stateID = 0;
	
	private Image m_background = null;
	private Image m_startButton = null;
	private static int m_startButtonX = 500;
	private static int m_startButtonY = 100;

	public MainMenu(int stateID) {
		m_stateID = stateID;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame stateManager)
			throws SlickException {
		m_background = new Image("assets/MainMenuBackgroundImage.jpg");
		m_startButton = new Image("assets/steakTile.png");
	}

	@Override
	public void render(GameContainer container, StateBasedGame stateManager, Graphics g)
			throws SlickException {
		m_background.draw(0,0);
		m_startButton.draw(m_startButtonX, m_startButtonY);
	}

	@Override
	public void update(GameContainer container, StateBasedGame stateManager, int delta)
			throws SlickException {
		Input input = container.getInput();
		int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();
        
        boolean hoverOnStartButton = false;
        
        if ((mouseX >= m_startButtonX && mouseX <= m_startButtonX + m_startButton.getWidth()) &&
            (mouseY >= m_startButtonY && mouseY <= m_startButtonY + m_startButton.getHeight())) {
                hoverOnStartButton = true;
        }
        
        if (hoverOnStartButton) {
        	if ( input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) ){
                stateManager.enterState(StateManager.ROOM_STATE);
            }
        }
	}

	@Override
	public int getID() {
		return m_stateID;
	}

}
