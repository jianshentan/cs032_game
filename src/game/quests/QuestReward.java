package game.quests;

import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import game.Collectable;
import game.GameObject;
import game.Person;
import game.Scene;
import game.StateManager;
import game.StaticObject;
import game.gameplayStates.DolphinChamber;
import game.gameplayStates.GamePlayState;
import game.gameplayStates.HospitalBase;
import game.gameplayStates.HospitalMaze;
import game.gameplayStates.VirtualRealityRoom;
import game.player.Player;
import game.popup.MainFrame;

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
	 * This reward completes part of quest 1.
	 *
	 */
	public static class WaterDownReward extends QuestReward {

		@Override
		public void onAccomplished(GamePlayState state, Player player) {
			DolphinChamber d = (DolphinChamber) StateManager.getInstance().getState(StateManager.DOLPHIN_STATE);
			d.waterDown(true);
			GamePlayState townDay = (GamePlayState) StateManager.getInstance().getState(StateManager.TOWN_DAY_STATE);
			StaticObject fireHydrant = (StaticObject) townDay.getObject("fireHydrant");
			fireHydrant.setDialogue(new String[] {"The fire hydrant still has water gushing out of it."});
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
	
	class WindowThread implements Runnable {
		private String m_path;
		private java.awt.Dimension m_screenSize;
		private int[] m_size = new int[2];
		
		public void setSize(int[] size) {
			m_size[0] = size[0];
			m_size[1] = size[1];
		}
		public void setPath(String path) {
			m_path = path;
			m_screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		}
		public void run() {
			try {
				int randX = (int)(Math.random()*(m_screenSize.getWidth()-m_size[0]));
				int randY = (int)(Math.random()*(m_screenSize.getHeight()-m_size[1]));
				MainFrame frame = new MainFrame(randX, randY, m_size[0], m_size[1], m_path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Reward for quest 3
	 *
	 */
	public static class Quest3Reward extends QuestReward {
		
		@Override
		public void onAccomplished(GamePlayState state, Player player) {
			HospitalBase game = (HospitalBase)state;
			float[][] path = {{6,3}};
			Scene s = new Scene(game, game.getPlayer(), path);
			s.playScene();
				
			WindowThread thread1 = new WindowThread();
			thread1.setPath("assets/hospitalLetter.png");
			thread1.setSize(new int[] {450, 500});
			thread1.run();	
			
			WindowThread thread2 = new WindowThread();
			thread2.setPath("assets/completeLevel3Text.png");
			thread2.setSize(new int[] {450, 200});
			thread2.run();	
			
			HospitalMaze hospitalMaze = (HospitalMaze) StateManager.getInstance().getState(StateManager.HOSPITAL_MAZE_STATE);
			ArrayList<MainFrame> frames = hospitalMaze.getFrames();
			for (MainFrame f : frames) {
				f.dispose();
			}
			
			int destination = StateManager.TOWN_DAY_STATE;
			StateManager.getInstance().enterState(destination, 
					new FadeOutTransition(Color.black, 1000), 
					new FadeInTransition(Color.black, 1000));
			GamePlayState destinationState = (GamePlayState)StateManager.getInstance().getState(destination);
			destinationState.setPlayerLocation(8*64, 22*64);
			
		}
		
	}
	
	
}
