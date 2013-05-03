package game.quests;

import java.util.ArrayList;

import game.gameplayStates.GamePlayState;
import game.player.Player;

/**
 * This class represents a quest- a sequence of goals
 * the player has to accomplish in order to accomplish some task.
 *
 */
public abstract class Quest {
	
	protected int m_questID;
	public int getID() { return m_questID; }
	protected ArrayList<QuestGoal> m_goals;
	protected int m_currentGoal;
	public int getProgress() { return m_currentGoal; }
	protected boolean m_isActive;
	
	
	public void startQuest(GamePlayState state, Player player) {
		String[] desc = m_goals.get(0).onStartText();
		if(desc!=null) {
			state.displayDialogue(desc);
		}
		m_isActive = true;
	}
	
	public void updateQuest(GamePlayState state, Player player) {
		if(m_isActive==false) {
			return;
		}
		if(m_goals.get(m_currentGoal).isAccomplished(state, player)) {
			m_currentGoal += 1;
			if (m_currentGoal >= m_goals.size()) {
				m_isActive = false;
			} else {
				String[] desc = m_goals.get(m_currentGoal).onStartText();
				if(desc!=null) {
					state.displayDialogue(desc);
				}
			}
		}
	}
	
	public boolean isActive() {
		return m_isActive;
	}
	
	public abstract void questOver(GamePlayState state, Player player);

}
