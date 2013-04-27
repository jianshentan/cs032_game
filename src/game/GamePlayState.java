package game;

import org.newdawn.slick.state.BasicGameState;

/**
 * All game states that are in gameplay-mode need to inherit from this class
 * -- pausemenu
 * -- text/dialouge 
 */
public abstract class GamePlayState extends BasicGameState {
	
	protected boolean m_isPaused = false;
	protected boolean m_inDialogue = false;
	protected PauseMenu m_pauseMenu = null;
	
	public void setPauseState(boolean state) { m_isPaused = state; }
	public boolean getPauseState() { return m_isPaused; }
	
	public void setDialogueState(boolean state) { m_inDialogue = state; }
	public boolean getDialogueState() { return m_inDialogue; }
}
