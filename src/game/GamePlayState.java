package game;

import org.newdawn.slick.state.BasicGameState;

/**
 * All game states that are in gameplay-mode need to inherit from this class
 */
public abstract class GamePlayState extends BasicGameState {
	
	protected boolean m_isPaused = false;
	
	public void setPauseState(boolean state) { 
		m_isPaused = state;
	}

	public boolean getPauseState() {
		return m_isPaused;
	}
}
