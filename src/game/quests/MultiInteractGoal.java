package game.quests;

import java.util.ArrayList;

import game.gameplayStates.GamePlayState;
import game.interactables.Interactable;
import game.player.Player;

public class MultiInteractGoal extends QuestGoal {
	
	private ArrayList<Interactable> m_targets;
	
	public MultiInteractGoal(ArrayList<Interactable> targets) {
		this.m_targets = targets;
	}

	@Override
	public boolean isAccomplished(GamePlayState state, Player player) {
		return false;
	}

	@Override
	public boolean isAccomplished(GamePlayState state, Player player,
			Interactable interactable) {
		m_targets.remove(interactable);
		if(m_targets.size()==0)
			return true;
		return false;
	}

}
