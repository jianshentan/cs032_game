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

	/**
	 * Checks if the goal is accomplished- fires at every update
	 * @param state
	 * @param player
	 * @return boolean
	 */
	public abstract boolean isAccomplished(GamePlayState state, Player player);
	
	/**
	 * Checks if goal is accomplished- fires when the player interacts with something
	 * @param state
	 * @param player
	 * @param interactable
	 * @return boolean
	 */
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
			if(interactable == null || m_targetInteractable == null)
				return false;
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
			if(player.getUsing()==null)
				return false;
			if(player.getUsing().equals(itemNeeded)) {
				return true;
			}
			return false;
		}

		@Override
		public boolean isAccomplished(GamePlayState state, Player player,
				Interactable interactable) {
			if(player.getUsing()==null)
				return false;
			if(player.getUsing().equals(itemNeeded)) {
				return true;
			}
			return false;
		}
		
	}
	
	/**
	 * This goal returns true when the player reaches a certain location.
	 *
	 */
	public static class LocationGoal extends QuestGoal {
		
		private int x, y;
		
		public LocationGoal(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean isAccomplished(GamePlayState state, Player player) {
			int[] playerLocation = player.getSquare();
			//System.out.println(playerLocation[0] + playerLocation[1]);
			if(playerLocation[0]==this.x && playerLocation[1]==this.y){
				return true;
			} return false;
		}

		@Override
		public boolean isAccomplished(GamePlayState state, Player player,
				Interactable interactable) {
			int[] playerLocation = player.getSquare();
			if(playerLocation[0]==this.x && playerLocation[1]==this.y){
				return true;
			} return false;
		}
		
	}
	
	
	/**
	 * This goal returns true when the player reaches one of any number of locations.
	 *
	 */
	public static class MultiLocationGoal extends QuestGoal {
		
		private int[][] locations;
		
		public MultiLocationGoal(int[][] locations) {
			this.locations = locations;
		}

		@Override
		public boolean isAccomplished(GamePlayState state, Player player) {
			int[] playerLocation = player.getSquare();
			//System.out.println(playerLocation[0] + playerLocation[1]);
			for(int i = 0; i<this.locations.length; i++) {
				if(playerLocation[0]==this.locations[i][0] && playerLocation[1]==this.locations[i][1]){
					return true;
				} 
			}
			return false;
		}

		@Override
		public boolean isAccomplished(GamePlayState state, Player player,
				Interactable interactable) {
			int[] playerLocation = player.getSquare();
			//System.out.println(playerLocation[0] + playerLocation[1]);
			for(int i = 0; i<this.locations.length; i++) {
				if(playerLocation[0]==this.locations[i][0] && playerLocation[1]==this.locations[i][1]){
					return true;
				}
			}
			return false;
		}
		
	}
	
}
