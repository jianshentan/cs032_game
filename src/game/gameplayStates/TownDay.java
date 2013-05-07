package game.gameplayStates;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import game.Dialogue;
import game.GameObject;
import game.Person;
import game.Scene;
import game.StateManager;
import game.StaticObject;
import game.collectables.*;
import game.interactables.Animal;
import game.interactables.Door;
import game.interactables.Horse;
import game.interactables.InvisiblePortal;
import game.interactables.Interactable;
import game.interactables.PortalObject;
import game.interactables.Trashcan;
import game.interactables.Wrench;
import game.player.Player;
import game.quests.Quest;
import game.quests.QuestGoal;
import game.quests.QuestGoal.InteractionGoal;
import game.quests.QuestReward;
import game.quests.QuestStage;

public class TownDay extends Town{
	
	private boolean m_quest1Given;
	private boolean m_horsesFreed;
	private Image m_overlay;
	
	public TownDay(int stateID) {
		m_stateID = stateID;
	}
	
	@Override
	public void additionalEnter(GameContainer container, StateBasedGame stateManager){
	//	m_horsesFreed = true;
		if(m_horsesFreed){
			m_horsesFreed = false;

			Horse horse1;
			try {
				int[][] horse_stops = {{10,14},{12,10},{19,6},{19,4},{19,6},{12,10},{23,14},{10,20},{4,22},{4,28},{11,28}};
				horse1 = new Horse("horseHerd", this, m_player, 10*SIZE, 14*SIZE, horse_stops);
				this.addObject(horse1, true);
				m_enemies.add(horse1);
				this.getObject("tree1").setRenderPriority(false);
				this.getObject("tree2").setRenderPriority(false);
				Sound gallop = new Sound("assets/sounds/HorsesRunning.wav");
				gallop.play();
				//TODO: make gallop sound longer, since gallop scene is now longer.
				Scene s = new Scene(this,m_player,new float[][]  {{11,14},{13,11},{17,10},{21,6},{17,6},{13,8},{16,17},{22,17},{23,16},{20,16},{14,17},{9,24},{4,24},{6,28},{12,29}});
				s.setCamera(true);
				s.playScene();
				
			} catch (SlickException e) {
				System.err.println("horse error!");
			}
		}

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
			//m_interactables = new HashMap<String, Interactable>();
			//m_objects = new HashMap<String, GameObject>();	
			
			PortalObject portal = new InvisiblePortal("portal", 11*SIZE, 29*SIZE, StateManager.HOME_STATE, 2, 3);
			this.addObject(portal, true);
			
			
			Person person_1 = new Person("person_1", 9*SIZE, 22*SIZE, "assets/characters/human_2.png", null);
			person_1.setDialogue(new String[] 
					{"\"Can you help me find my cats? There are 2 of them.\""});
			this.addObject(person_1, true);
			m_blocked[9][22] = true;
			
			Person wrenchGiver = new Person("wrenchGiver", 16*SIZE, 25*SIZE, "assets/characters/human_3.png", new Wrench(-1,-1));
			wrenchGiver.setDialogue(new String[] 
					{"\"Young man, for what reason have you let your mustache grow?\"",
					"\"It really looks quite terrible on a face like ours.\"",
					"\"Here's a wrench I found, maybe you can fix your face with it.\"",
					"* you've received a wrench *"});
			this.addObject(wrenchGiver, true);
			m_blocked[16][25] = true;
			
			Person buttPlugPerson = new Person("buttPlugPerson", 4*SIZE, 8*SIZE, "assets/characters/human_2.png", new BigPlug());
			buttPlugPerson.setDialogue(new String[]
					{"\"!\"",
					"\"You startled me!\"",
					"\"Let's just keep this our little secret\"",
					"\"Take this as payment\"",
					"* You've received a butt-plug *"
					});
			this.addObject(buttPlugPerson, true);
			m_blocked[4][8] = true;
			
			Door dolphinDoor = new Door("dolphinDoor", 8*SIZE, 13*SIZE, StateManager.DOLPHIN_ENTRANCE, 2, 8);
			this.addObject(dolphinDoor, true);
			
			Door virtualRealityRoomDoor = new Door("virtualRealityRoomDoor", 22*SIZE, 17*SIZE, StateManager.VIRTUAL_REALITY_ROOM_STATE, 3, 4);
			this.addObject(virtualRealityRoomDoor, true);
			//put somewhere more reasonable
			Trashcan trash = new Trashcan("trash", 20*SIZE, 12*SIZE);
			this.addObject(trash, true);
			
			Door hospitalDoor = new Door("hospitalDoor", 5*SIZE, 21*SIZE, StateManager.HOSPITAL_ENTRANCE_STATE, -1, -1); 
			this.addObject(hospitalDoor, true);
			
			//TODO: place cats
			Animal cat1 = new Animal("cat1", "assets/cat1.png", this, m_player,
					 14*SIZE, 17*SIZE, 23, 12);
			this.addObject(cat1, true);
			//m_objects.put(cat1.getKey(), cat1);
			m_enemies.add(cat1);
			
			Animal cat2 = new Animal("cat2", "assets/cat2.png", this, m_player,
					 4*SIZE, 26*SIZE, 1, 18);
			this.addObject(cat2, true);
			//m_objects.put(cat1.getKey(), cat2);
			m_enemies.add(cat2);
			
			//TODO: add new people who give information about stuff
			Person dolphinHater = new Person("dolphinHater", 11*SIZE, 16*SIZE,"assets/characters/human_4.png", null);
			dolphinHater.setDialogue(new String[] {
					"\"Those dolphins are always up to no good.\"",
					"\"They turned the old zoo into a horse stable!\"",
					"\"I wish that someone would shove something up all their holes!\""});
			this.addObject(dolphinHater, true);
			m_blocked[11][16] = true;
			
			Person optimist = new Person("optimist", 13*SIZE, 11*SIZE,"assets/characters/human_4.png", null);
			optimist.setDialogue(new String[] {
					"\"You know what I think about this town?\"",
					"\"I think it's a great place to live.\"",
					"\"We have the best fire response system in the world!\""});
			this.addObject(optimist, true);
			m_blocked[13][11] = true;
			
			//TODO: add signs
			StaticObject sign1 = new StaticObject("sign1", 7*SIZE, 13*SIZE, "assets/gameObjects/sign.png");
			sign1.setDialogue(new String[] {"\"The Horse Stables (formerly the zoo)\""});
			this.addObject(sign1, true);
			
			StaticObject fireHydrant = new StaticObject("fireHydrant", 6*SIZE, 23*SIZE, "assets/firehydrant.png");
			this.addObject(fireHydrant, true);
			m_blocked[6][23] = true;
			
		}

		
	}
	
	@Override
	public void additionalRender(GameContainer container, StateBasedGame stateManager, Graphics g) {
		if(m_overlay!=null)
			g.drawImage(m_overlay, 0, 0);
	}

	@Override
	public void dialogueListener(Interactable i) {

		
	}

	@Override
	public void additionalSetupObjects(int city, int dream) throws SlickException {
		
		if(city==4) {
			
			if(m_quest1Given == false) {
				
				StaticObject fireHydrant = (StaticObject)this.getObject("fireHydrant");
				fireHydrant.setDialogue(new String[] {"This is a fire hydrant."});
				
				Quest fireHydrantQuest = new Quest("fireHydrantQuest");
				QuestStage goal1 = new QuestStage().addGoal(new QuestGoal.InteractionTypeGoal(fireHydrant));
				goal1.addGoal(new QuestGoal.ItemEquippedGoal(new Wrench(-1,-1)));
				goal1.setReward(new QuestReward.WaterDownReward());
				//goal1.setStartText(new String[] {"You wonder how you can close down the zoo."});
				goal1.setEndText(new String[] {"You open the fire hydrant with the wrench. " +
						"A great column of water sprays upward like a geyser. There are pretty rainbows."});
				fireHydrantQuest.addStage(goal1);
				//TODO: add a goal for the plug
				m_player.addQuest(fireHydrantQuest);
				fireHydrantQuest.startQuest(this);
				m_quest1Given = true;
				
				//TODO: add cat quest
				Quest catQuest = new Quest("catQuest");
				QuestStage c1 = new QuestStage().addGoal(new QuestGoal.InteractionGoal(this.getInteractable("person_1")));
				QuestStage c2 = new QuestStage().setStartText(new String[]
						{"You want to find some cats!"});
				ArrayList<Interactable> cats = new ArrayList<Interactable>();
				cats.add(getInteractable("cat1"));
				cats.add(getInteractable("cat2"));
				c2.addGoal(new QuestGoal.MultiInteractGoal(cats));
				catQuest.addStage(c1);
				catQuest.addStage(c2);
				
				
				
			}
		}
		else if(city==3) {
			m_overlay = new Image("assets/black_15.png");
			
			((Person) this.getObject("dolphinHater")).setDialogue(new String[] {"\"Can you do something about all" +
					" the horses? I hate horses.\""});
			((Person) this.getObject("person_1")).setDialogue(new String[] {"\"My cats...*sob*...*sob*\""});
			((Person) this.getObject("optimist")).setDialogue(new String[] {"\"What happened to the flowers?\"",
					"\"They were so beautiful...\""});
			((Person) this.getObject("wrenchGiver")).setDialogue(new String[] {"\"Young man, for what reason " +
					"have you not fixed your face yet?\"",
					"\"Did you do something else with that wrench I gave you?\""});
			
			this.removeObject("dolphinDoor");
			StaticObject dolphinDoor = new StaticObject("dolphinDoor", 8*SIZE, 13*SIZE, "assets/gameObjects/door.png");
			dolphinDoor.setDialogue(new String[] {"The door is locked.",
					"\"Stables closed due to disturbances,\"" +
					" a sign on the door reads."});
			this.addObject(dolphinDoor, true);
			
			this.getObject("tree1").setSprite(new Image("assets/gameObjects/treebroken.png"));
			this.getObject("tree2").setSprite(new Image("assets/gameObjects/treebroken.png"));
			m_blocked[19][7] = false;
			m_blocked[20][7] = false;
			m_blocked[11][20] = false;
			m_blocked[12][20] = false;
			
			this.getObject("fireHydrant").setSprite(new Image("assets/gameObjects/firehydrantbroken.png"));
			
			Animal cat1 = (Animal)this.getObject("cat1");
			Animal cat2 = (Animal)this.getObject("cat2");
			int[] cat1_loc = cat1.getSquare();
			int[] cat2_loc = cat2.getSquare();
			
			m_enemies.remove(cat1);
			m_enemies.remove(cat2);
			this.removeObject("cat1");
			this.removeObject("cat2");
			StaticObject static_cat1 = new StaticObject("cat1",  cat1_loc[0]*SIZE, cat1_loc[1]*SIZE,"assets/cat1dead.png");
			StaticObject static_cat2 = new StaticObject("cat2", cat2_loc[0]*SIZE, cat2_loc[1]*SIZE, "assets/cat2dead.png");
			this.addObject(static_cat1,false);
			this.addObject(static_cat2,false);
			
		}
		else if(city==2) {
			m_overlay = new Image("assets/black_40.png");
			((Person) this.getObject("person_1")).setDialogue(new String[] {"\"My cats...*sob*...*sob*\""});
			((Person) this.getObject("optimist")).setDialogue(new String[] {"\"What happened to the flowers?\"",
				"\"They were so beautiful...\""});
		}
		
	}

	@Override
	public void setupDialogue(GameContainer container, int city, int dream) {
	}
	public void setFree(){
		m_horsesFreed = true;
	}
}
