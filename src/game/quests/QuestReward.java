package game.quests;

import org.newdawn.slick.SlickException;

import game.Collectable;
import game.GameObject;
import game.Person;
import game.StateManager;
import game.StaticObject;
import game.gameplayStates.DolphinChamber;
import game.gameplayStates.GamePlayState;
import game.gameplayStates.VirtualRealityRoom;
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
	
	/**
	 * This reward adds a game object to the map.
	 *
	 */
	public static class WaterDownReward extends QuestReward {

		@Override
		public void onAccomplished(GamePlayState state, Player player) {
			DolphinChamber d = (DolphinChamber) StateManager.getInstance().getState(StateManager.DOLPHIN_STATE);
			d.waterDown(true);
		}
		
	}
	
	/**
	 * This reward ends Quest 2.
	 *
	 */
	public static class Quest2Reward extends QuestReward {

		@Override
		public void onAccomplished(GamePlayState state, Player player) {
			StateManager.m_cityState--;
			player.getGame().displayDialogue(new String[] {"\"Well done,\" " +
					"you hear the booming voice say."});
			VirtualRealityRoom room = (VirtualRealityRoom) StateManager.getInstance()
					.getState(StateManager.VIRTUAL_REALITY_ROOM_STATE);
			room.removeObject("VRC");
			StaticObject VRC = null;
			try {
				VRC = new StaticObject("VRC", 6*GameObject.SIZE, GameObject.SIZE, 
						"assets/gameObjects/virtualRealityChair.png");
				VRC.setDialogue(new String[] {"You try sitting on the chair, but you don't end up in your " +
						"happy place."});
			} catch (SlickException e) {
				e.printStackTrace();
			}
			room.addObject(VRC, true);
			Person guide = (Person) room.getObject("guide");
			guide.setDialogue(new String[] {"Oops. Looks like something broke. You don't happen to know " +
					"anything about that, do you?"});
		}
		
	}
	
	/**
	 * Reward for quest 3
	 *
	 */
	public static class Quest3Reward extends QuestReward {

		@Override
		public void onAccomplished(GamePlayState state, Player player) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
}
