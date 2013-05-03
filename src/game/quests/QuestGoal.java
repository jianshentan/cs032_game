package game.quests;

import java.util.ArrayList;

import game.Collectable;
import game.gameplayStates.GamePlayState;
import game.interactables.Interactable;
import game.player.Player;

/**
 * This represents a goal of a quest stage. A stage is completed
 * when all its goals are accomplished simultaneously.
 *
 */
public abstract class QuestGoal {

	public abstract boolean isAccomplished(GamePlayState state, Player player);
	
	public abstract boolean isAccomplished(GamePlayState state, Player player, Interactable interactable);
	
	/**
	 * Goal is to interact with some interactable.
	 *
	 */
	public static class InteractionGoal extends QuestGoal {
		private Interactable m_targetInteractable;
		
		public InteractionGoal(Interactable target) {
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
			//System.out.println("trying quest goal");
			if(interactable.equals(m_targetInteractable))
				return true;
			return false;
		}
	}
	
	/**
	 * Goal is to interact with multiple objects.
	 *
	 */
	public static class MultiInteractGoal extends QuestGoal {
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
	
	/**
	 * This goal returns true if the player has an item equipped.
	 *
	 */
	public static class ItemEquippedGoal extends QuestGoal {
		
		private Collectable itemNeeded;
		
		public ItemEquippedGoal(Collectable item) {
			this.itemNeeded = item;
		}

		@Override
		public boolean isAccomplished(GamePlayState state, Player player) {
			if(player.getUsing().equals(itemNeeded)) {
				return true;
			}
			return false;
		}

		@Override
		public boolean isAccomplished(GamePlayState state, Player player,
				Interactable interactable) {
			if(player.getUsing().equals(itemNeeded)) {
				return true;
			}
			return false;
		}
		
	}
	
}
