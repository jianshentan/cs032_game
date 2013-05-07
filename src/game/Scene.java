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
	private float[][] m_locations;
	private HashMap<Integer, String[]> m_dialogue;
	private boolean m_invisiblePlayer;
	private boolean m_camera;
	private float m_cameraSpeed = 1;
	
	public Scene(GamePlayState state, Player player, float[][] locations) {
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
	
	public void setCameraSpeed(float s) {
		m_cameraSpeed = s;
	}
	
	public void playScene() {
		m_state.enterScene();
		if(m_invisiblePlayer)
			m_state.setInvisiblePlayer(true);
		if(!m_camera) {
			// convert to int[][]
			int[][] locations = new int[m_locations.length][];
			for (int i=0; i<m_locations.length; i++) {
				locations[i] = new int[2];
				for (int j=0; j<2; j++) 
					locations[i][j] = (int) m_locations[i][j];
			}
				
			m_player.enterScene(m_state, locations);
		}
		else {
			SceneCamera cam = new SceneCamera(m_locations, m_player, m_state);
			cam.setSpeed(m_cameraSpeed);
			m_state.setCamera(cam);
		}
	}

}
