package game;

import java.util.HashMap;

import game.gameplayStates.GamePlayState;
import game.player.Player;

public class Scene {
	
	private GamePlayState m_state;
	private Player m_player;
	private int[][] m_locations;
	private HashMap<Integer, String[]> m_dialogue;
	private boolean m_invisiblePlayer;
	
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
	
	public void playScene() {
		m_state.enterScene();
		if(m_invisiblePlayer)
			m_state.setInvisiblePlayer(true);
		m_player.enterScene(m_state, m_locations);
	}

}
