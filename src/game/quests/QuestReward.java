package game.quests;

import game.Collectable;
import game.gameplayStates.GamePlayState;
import game.player.Player;

/**
 * This class represents rewards for quests.
 *
 */
public abstract class QuestReward {

	public abstract void onAccomplished(GamePlayState state, Player player);
	
	/**
	 * This gives the player an item.
	 *
	 */
	public static class ItemReward extends QuestReward {
		
		private Collectable item;

		public ItemReward(Collectable item) {
			this.item = item;
		}
		
		@Override
		public void onAccomplished(GamePlayState state, Player player) {
			player.addToInventory(item);
			
		}
	}
	
	/**
	 * This class awards the player with a health gain.
	 *
	 */
	public static class HealthReward extends QuestReward {
		
		private int amount;
		
		public HealthReward(int amount) {
			this.amount = amount;
		}
		
		@Override
		public void onAccomplished(GamePlayState state, Player player) {
			player.getHealth().updateHealth(amount);
		}
	}
	
	
}
