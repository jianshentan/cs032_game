package game.quests;

import java.util.ArrayList;

import game.StateManager;
import game.gameplayStates.GamePlayState;
import game.interactables.Interactable;
import game.player.Player;

/**
 * This class represents a quest- a sequence of goals
 * the player has to accomplish in order to accomplish some task.
 *
 */
public class Quest {
	
	private int m_questID;
	private String m_name;
	public String getName() {return m_name;}
	public int getID() { return m_questID; }
	protected ArrayList<QuestStage> m_goals;
	protected int m_currentGoal;
	public int getProgress() { return m_currentGoal; }
	protected boolean m_isActive;
	
	public Quest(String name) {
		this.m_questID = StateManager.getKey();
		StateManager.addQuest(m_questID, this);
		this.m_name = name;
		this.m_goals = new ArrayList<QuestStage>();
		this.m_currentGoal = 0;
		this.m_isActive = false;
	}
	
	
	public final void addStage(QuestStage goal) {
		m_goals.add(goal);
	}
	
	public void startQuest(GamePlayState state) {
		String[] desc = m_goals.get(0).onStartText();
		if(desc!=null) {
			state.displayDialogue(desc);
		}
		m_isActive = true;
	}
	
	/**
	 * Triggered on each update.
	 * @param state
	 * @param player
	 */
	public final void updateQuest(GamePlayState state, Player player) {
		if(m_isActive==false) {
			return;
		}
		if(m_goals.get(m_currentGoal).isAccomplished(state, player)) {
			m_goals.get(m_currentGoal).onAccomplished(state, player);
			m_currentGoal += 1;
			if (m_currentGoal >= m_goals.size()) {
				m_isActive = false;
				String[] desc = m_goals.get(m_currentGoal-1).onEndText();
				if(desc!=null) {
					state.displayDialogue(desc);
				}
				this.questOver(state, player);
			} else {
				String[] desc = m_goals.get(m_currentGoal).onStartText();
				if(desc!=null) {
					state.displayDialogue(desc);
				}
			}
		}
	}

	/**
	 * Triggered on each interaction.
	 * @param state
	 * @param player
	 * @param interactable
	 */
	public final void updateQuest(GamePlayState state, Player player, Interactable interactable) {
		if(m_isActive==false) {
			return;
		}
		if(m_goals.get(m_currentGoal).isAccomplished(state, player, interactable)) {
			m_goals.get(m_currentGoal).onAccomplished(state, player);
			
			m_currentGoal += 1;
			if (m_currentGoal >= m_goals.size()) {
				String[] desc = m_goals.get(m_currentGoal-1).onEndText();
				if(desc!=null) {
					state.displayDialogue(desc);
				}
				m_isActive = false;
				this.questOver(state, player);
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
	
	protected void questOver(GamePlayState state, Player player) {
		
	}

}
