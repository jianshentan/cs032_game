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
		return false;
	}

	/**
	 * The goal is accomplished if the player interacts with the target interactable.
	 */
	@Override
	public boolean isAccomplished(GamePlayState state, Player player,
			Interactable interactable) {
		System.out.println("trying quest goal");
		if(interactable == m_targetInteractable)
			return true;
		return false;
	}



}
