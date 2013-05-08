package game.quests;

import java.util.ArrayList;

import game.gameplayStates.GamePlayState;
import game.interactables.Interactable;
import game.player.Player;

/**
 * This class represents a segment in a quest.
 * Each stage has a goal/goals that need to be accomplished,
 * and each 
 *
 */
public class QuestStage {
	
	protected String[] m_startText;
	protected String[] m_endText;
	protected ArrayList<QuestGoal> m_goals;
	protected QuestReward m_reward;
	
	public QuestStage(String[] startText, String[] endText) {
		m_startText = startText;
		m_endText = endText;
		m_goals = new ArrayList<QuestGoal>();
	}
	
	public QuestStage() {
		m_goals = new ArrayList<QuestGoal>();
	}
	
	/**
	 * Adds a goal to a quest stage.
	 * @param goal
	 */
	public final QuestStage addGoal(QuestGoal goal) {
		m_goals.add(goal);
		return this;
	}
	
	/**
	 * Sets the quest stage's reward.
	 * @param reward
	 */
	public final QuestStage setReward(QuestReward reward) {
		m_reward = reward;
		return this;
	}
	
	public boolean isAccomplished(GamePlayState state, Player player) {
		for (QuestGoal goal : this.m_goals) {
			if(goal.isAccomplished(state, player)==false) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isAccomplished(GamePlayState state, Player player, Interactable interactable) {
		for (QuestGoal goal : this.m_goals) {
			//System.out.println("checking accomplished quest stage 61 ");
			if(goal.isAccomplished(state, player, interactable)==false) {
				return false;
			}
		}
		return true;
	}
	
	public final void onAccomplished(GamePlayState state, Player player) {
		if(m_reward!=null)
			m_reward.onAccomplished(state, player);
	}
	
	public final QuestStage setStartText(String[] text) {
		m_startText = text;
		return this;
	}
	
	public final QuestStage setEndText(String[] text) {
		m_endText = text;
		return this;
	}
		
	public final String[] onStartText()  {
		return m_startText;
	}
	
	public final String[] onEndText() {
		return m_endText;
	}

}
