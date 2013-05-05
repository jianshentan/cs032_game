package game;

import java.util.HashMap;

import game.cameras.SceneCamera;
import game.gameplayStates.GamePlayState;
import game.player.Player;

/**
 * A scene is the 
 *
 */
public class Scene {
	
	private GamePlayState m_state;
	private Player m_player;
	private int[][] m_locations;
	private HashMap<Integer, String[]> m_dialogue;
	private boolean m_invisiblePlayer;
	private boolean m_camera;
	
	public Scene(GamePlayState state, Player player, int[][] locations) {
		m_state = state;
		m_player = player;
		m_locations = locations;
		m_dialogue = new HashMap<Integer, String[]>();
	}
	
	public void addDialogue(int id, String[] s) {
		m_dialogue.put(id, s);
	}
	
	public void setPlayerInvisible(boolean b) {
		m_invisiblePlayer = b;
	}
	
	/**
	 * This is set to true if you're changing the camera perspective
	 * instead of moving the player.
	 * @param b
	 */
	public void setCamera(boolean b) {
		m_camera = b;
	}
	
	public void playScene() {
		m_state.enterScene();
		if(m_invisiblePlayer)
			m_state.setInvisiblePlayer(true);
		if(!m_camera)
			m_player.enterScene(m_state, m_locations);
		else
			m_state.setCamera(new SceneCamera(m_locations, m_player, m_state));
	}

}
