package game.gameplayStates;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

import game.Enemy;
import game.GameObject;
import game.Spectre;
import game.StateManager;
import game.StaticObject;
import game.interactables.Door;
import game.interactables.Interactable;
import game.interactables.InvisiblePortal;
import game.interactables.PortalObject;
import game.quests.Quest;
import game.quests.QuestGoal;
import game.quests.QuestGoal.LocationGoal;
import game.quests.QuestStage;

public class TownNight extends Town {

	private Image m_nightMask;
	private int m_dream = 3;
	public TownNight(int stateID) throws SlickException {
		m_stateID = stateID;
		m_nightMask = new Image("assets/nightMask_white.png");
	}
	
	@Override
	public void additionalInit(GameContainer container, StateBasedGame stateManager) throws SlickException {
		m_playerX = SIZE*11;
		m_playerY = SIZE*28;
		//add ghost
		
		if(m_mapPath != null) {
			m_tiledMap = new TiledMap(m_mapPath);
		}
		else
			try {
				m_tiledMap = new TiledMap("assets/maps/townDay.tmx");
			} catch (SlickException e) {
				System.out.println("ERROR: Could not townDay.tmx");
			}
		m_map = new simpleMap();
		m_blocked = new boolean[m_tiledMap.getWidth()][m_tiledMap.getHeight()];
		for (int xAxis=0; xAxis<m_tiledMap.getWidth(); xAxis++) {
			for (int yAxis=0; yAxis<m_tiledMap.getHeight(); yAxis++) {
				int tileID = m_tiledMap.getTileId(xAxis, yAxis, 0);
				String value = m_tiledMap.getTileProperty(tileID, "blocked", "false");
				if ("true".equals(value)) {
					m_blocked[xAxis][yAxis] = true;
				}
			}
		}
		
		if (!this.isLoaded()) {
			StaticObject dolphinDoor = new StaticObject("dolphinDoor", 8*SIZE, 13*SIZE, "assets/gameObjects/door.png");
			this.addObject(dolphinDoor, false);
			
		}
		//this.setMusic("/home/jack/Music/track01.wav");
	}
	
	/**
	 * Draws the night mask.
	 */
	public void additionalRender(GameContainer container, StateBasedGame stateManager, Graphics g) {
		g.drawImage(m_nightMask, 0, 0);
	}

	@Override
	public void dialogueListener(Interactable i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void additionalSetupObjects(int city, int dream) throws SlickException {
		//setup enemies
		m_dream = dream;
		if(dream==3) {
			//TODO: set up some other stuff... text quests?
			int[][] leadPoints = {{8,14},{8,14},{8,14},{8,14}};
			Spectre spec = new Spectre(this, m_player, SIZE*10, SIZE*26, leadPoints);
			m_enemies.add(spec);
			
			Quest learning = new Quest("learning");
			QuestStage stage1 = new QuestStage().addGoal(new QuestGoal.LocationGoal(11, 28))
					.setStartText(new String[] 
							{"Who... is that mysterious humanoid figure with no face, you wonder",
							"You feel a compulsion to follow it."});
			learning.addStage(stage1);
			QuestStage stage2 = new QuestStage()
					.addGoal(new QuestGoal.MultiLocationGoal(new int[][] {{10, 21},{11,21},{10,22},{11,22}}))
					.setEndText(new String[] {"Wait... stop! you shout, to no avail. It's as if the figure" +
							" is leading you on a pursuit."});
			learning.addStage(stage2);
			learning.startQuest(this);
			m_player.addQuest(learning);
		}
		if(dream==2) {
			m_enemies = new ArrayList<Enemy>();
			int[][] leadPoints = {{22,18},{22,18},{22,18},{22,18}};
			Spectre spec = new Spectre(this, m_player, SIZE*10, SIZE*26, leadPoints);
			m_enemies.add(spec);
		}
		
	}

	@Override
	public void setupDialogue(GameContainer container, int city, int dream) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * This brings the player back home, and decrements dream state by 1.
	 */
	@Override
	public void stateEnd(int endCode) {
		m_player.removeQuest("learning");
		StateManager.m_dreamState -= 1;
		StateManager.getInstance().enterState(StateManager.HOME_STATE, 
				new FadeOutTransition(Color.black, 2000), 
				new FadeInTransition(Color.black, 2000));
	}

}
