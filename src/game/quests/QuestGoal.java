package game.quests;

import game.gameplayStates.GamePlayState;
import game.interactables.Interactable;
import game.player.Player;

/**
 * This class represents the goal of a quest.
 *
 */
public abstract class QuestGoal {
	
	protected String[] m_startText;
	protected String[] m_endText;
	protected QuestReward m_reward;
	
	public QuestGoal(String[] startText, String[] endText) {
		m_startText = startText;
		m_endText = endText;
	}
	
	public QuestGoal() {
		
	}
	
	public final void setReward(QuestReward reward) {
		m_reward = reward;
	}
	
	public abstract boolean isAccomplished(GamePlayState state, Player player);
	
	public abstract boolean isAccomplished(GamePlayState state, Player player, Interactable interactable);
	
	public final void onAccomplished(GamePlayState state, Player player) {
		m_reward.onAccomplished(state, player);
	}
		
	public String[] onStartText()  {
		return m_startText;
	}
	
	public String[] onEndText() {
		return m_endText;
	}

}
