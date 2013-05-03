package game.quests;

import game.gameplayStates.GamePlayState;
import game.interactables.Interactable;
import game.player.Player;

public class InteractionQuestGoal extends QuestGoal {
	
	private Interactable m_targetInteractable;
	
	public InteractionQuestGoal(Interactable target) {
		super();
		m_targetInteractable = target;
	}

	@Override
	public boolean isAccomplished(GamePlayState state, Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * The goal is accomplished if the goal is interacted with.
	 */
	@Override
	public boolean isAccomplished(GamePlayState state, Player player,
			Interactable interactable) {
		if(interactable == m_targetInteractable)
			return true;
		return false;
	}

	@Override
	public void onAccomplished(GamePlayState state, Player player) {
		// TODO Auto-generated method stub

	}


}
