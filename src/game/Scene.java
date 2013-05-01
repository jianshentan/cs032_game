package game;

import game.gameplayStates.GamePlayState;
import game.player.Player;

public class Scene {
	
	private GamePlayState m_state;
	private Player m_player;
	private int[][] m_locations;
	
	public Scene(GamePlayState state, Player player, int[][] locations) {
		m_state = state;
		m_player = player;
		m_locations = locations;
	}
	
	public void playScene() {
		m_player.enterScene(m_state, m_locations);
	}

}
