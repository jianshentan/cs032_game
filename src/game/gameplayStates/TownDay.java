package game.gameplayStates;

import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import game.Dialogue;
import game.GameObject;
import game.Person;
import game.StateManager;
import game.StaticObject;
import game.interactables.Door;
import game.interactables.InvisiblePortal;
import game.interactables.Interactable;
import game.interactables.PortalObject;
import game.interactables.Wrench;
import game.quests.Quest;
import game.quests.QuestGoal;
import game.quests.QuestReward;
import game.quests.QuestStage;

public class TownDay extends GamePlayState {
	
	private boolean m_quest1Given;

	public TownDay(int stateID) {
		m_stateID = stateID;
	}
	
	@Override
	public void additionalInit(GameContainer container, StateBasedGame stateManager) throws SlickException {
		m_playerX = SIZE*11;
		m_playerY = SIZE*28;
		
		
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
			// setup objects
			m_interactables = new HashMap<Integer, Interactable>();
			m_objects = new HashMap<Integer, GameObject>();	
			
			PortalObject portal = new InvisiblePortal(1129, 11*SIZE, 29*SIZE, StateManager.HOME_STATE, 2, 3);
			m_interactables.put(1129, portal);
			m_objects.put(1129, portal);
			
			StaticObject doormat = new StaticObject(11*SIZE, 28*SIZE, "assets/gameObjects/doormat.png");
			m_objects.put(1128, doormat);
			
			Person person_1 = new Person(1127, 11*SIZE, 27*SIZE, "assets/characters/human_2.png", null);
			m_interactables.put(1127, person_1);
			m_objects.put(1127, person_1); 
			m_blocked[11][27] = true;
			
			Person person_2 = new Person(1625, 16*SIZE, 25*SIZE, "assets/characters/human_3.png", new Wrench(-1,-1));
			m_interactables.put(1625, person_2);
			m_objects.put(1625, person_2);
			m_blocked[16][25] = true;
			
			Door dolphinDoor = new Door(813, 8*SIZE, 13*SIZE, StateManager.DOLPHIN_ENTRANCE, 2, 8);
			m_interactables.put(813, dolphinDoor);
			m_objects.put(813, dolphinDoor);
		}

		
	}

	@Override
	public void dialogueListener(Interactable i) {
		// person_1: key = 1127
		if (m_interactables.containsKey(1127) && m_dialogue.containsKey(1127)) 
			if (i.getSquare()[0] == m_interactables.get(1127).getSquare()[0] && 
				i.getSquare()[1] == m_interactables.get(1127).getSquare()[1]) { 
				m_dialogueNum = 1127;
				m_inDialogue = true;
			}
		// person_2: key = 1625
		if (m_interactables.containsKey(1625) && m_dialogue.containsKey(1625))
			if (i.getSquare()[0] == m_interactables.get(1625).getSquare()[0] &&
				i.getSquare()[1] == m_interactables.get(1625).getSquare()[1]) {
				m_dialogueNum = 1625;
				m_inDialogue = true;
			}
		// dolphin door: key = 813;
		if (m_interactables.containsKey(813) && m_dialogue.containsKey(813))
			if (i.getSquare()[0] == m_interactables.get(1625).getSquare()[0] &&
				i.getSquare()[1] == m_interactables.get(1625).getSquare()[1]) {
				m_dialogueNum = 813;
				m_inDialogue = true;	
			}
	}

	@Override
	public void setupObjects(int city, int dream) throws SlickException {
		//TODO: set up cats
		
		if(city==3) {
			StaticObject fireHydrant = null;
			try {
				fireHydrant = new StaticObject(6*SIZE, 23*SIZE, "assets/firehydrant.png");
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			m_interactables.put(623, fireHydrant);
			m_objects.put(623, fireHydrant);
			m_blocked[6][23] = true;
			
//			InvisiblePortal portal = new InvisiblePortal(813, 8*SIZE, 13*SIZE, 
//					StateManager.DOLPHIN_STATE, 3*SIZE, 13*SIZE);
//			m_interactables.put(813, portal);
//			m_objects.put(813, portal);
			if(m_quest1Given == false) {
				Quest fireHydrantQuest = new Quest(0);
				QuestStage goal1 = new QuestStage().addGoal(new QuestGoal.InteractionGoal(fireHydrant));
				goal1.addGoal(new QuestGoal.ItemEquippedGoal(new Wrench(-1,-1)));
				goal1.setReward(new QuestReward.WaterDownReward());
				//goal1.setStartText(new String[] {"You wonder how you can close down the zoo."});
				goal1.setEndText(new String[] {"You open the fire hydrant with the wrench. " +
						"Water sprays out everywhere."});
				fireHydrantQuest.addStage(goal1);
				m_player.addQuest(fireHydrantQuest);
				fireHydrantQuest.startQuest(this);
				m_quest1Given = true;
			}
		}
		
	}

	@Override
	public void setupDialogue(GameContainer container, int city, int dream) {
		int[] dialoguePos;
		m_dialogue.clear();
		if (city == 3 && dream == 2) {
			m_dialogue = new HashMap<Integer, Dialogue>();
			Dialogue person_1_dialogue = new Dialogue(this, container, new String[] 
					{"can you help me find my cats? there are 5 of them."}, new String[]
							{"", "yes", "no"});
			m_dialogue.put(1127, person_1_dialogue);
			
			Dialogue person2Dialogue = new Dialogue(this, container, new String[] 
					{"Young man, for what reason have you let your mustache grow?",
					"It really looks quite terrible on a face like ours.",
					"Here's a wrench I found, maybe you can fix your face with it.",
					"* you've received a wrench *"}, null);
			dialoguePos = new int[] {16, 25};
			m_dialogue.put(positionToKey(dialoguePos), person2Dialogue);
			
//			Dialogue dolphinDoor = new Dialogue(this, container, new String[]
//					{"This is where it escaped... ", 
//					"I've got to block it off so that it won't get away next time"}, null);
//			m_dialogue.put(813, dolphinDoor);
		}
		else if (city == 2 && dream == 2) {
		}
	}

}
