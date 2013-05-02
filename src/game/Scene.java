package game;

import java.util.HashMap;

import game.gameplayStates.GamePlayState;
import game.player.Player;

public class Scene {
	
	private GamePlayState m_state;
	private Player m_player;
	private int[][] m_locations;
	private HashMap<Integer, Dialogue> m_dialogue;
	private boolean m_inDialogue;
	
	public Scene(GamePlayState state, Player player, int[][] locations) {
		m_state = state;
		m_player = player;
		m_locations = locations;
		m_dialogue = new HashMap<Integer, Dialogue>();
	}
	
	public void playScene() {
		m_player.enterScene(m_state, m_locations);
	}

}
