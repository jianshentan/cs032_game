package game.gameplayStates;

import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import game.Dialogue;
import game.StateManager;
import game.StaticObject;
import game.collectables.SmallPlug;
import game.interactables.Interactable;
import game.interactables.InvisiblePortal;
import game.popup.MainFrame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class DolphinEntrance extends GamePlayState {

	private boolean m_isHorsesShown = false, m_freed = false, m_rendered = false, m_horseSounds = false;
	private ArrayList<Runnable> m_threads;
	private int m_i, m_lastNoise, m_nextNoise, m_lastSound=-1;
	private ArrayList<MainFrame> m_frames;
	public DolphinEntrance(int id) {
		m_frames = new ArrayList<MainFrame>();
		m_stateID = id;
		m_nextNoise = 0;
		m_lastNoise = 0;
	}
	public void additionalUpdate(GameContainer container, StateBasedGame stateManager, int delta){
		if (m_freed){
			if(m_rendered){
				m_freed = false;
				TownDay td = (TownDay) stateManager.getState(StateManager.TOWN_DAY_STATE);
				td.setFree();
				Runnable freeHorsesThread = new Runnable() {
					public void run() {
						freeHorses();
					}
				};
				freeHorsesThread.run();
			}else{
				long time = System.currentTimeMillis();
				for(MainFrame frame: m_frames){
					while(System.currentTimeMillis()-time<10){
						
					}
					time = System.currentTimeMillis();
					frame.toFront();
				}
				m_rendered = true;
			}
				
		}
		if(m_horseSounds){
			m_nextNoise+=delta;
			if(m_nextNoise>m_lastNoise){
				m_lastNoise = 1500+(int)(Math.random()*1000)-500;
				m_nextNoise = 0;
				
				playHorseNoise();
				
			}
		}
	}
	public void additionalEnter(GameContainer container, StateBasedGame stateManager) {
		if(m_freed){
			
			String[] di2 = {"I guess blowing up that dolphin freed the horses from it's control", "Or something"};
			this.displayDialogue(di2);
			((InvisiblePortal) this.getObject("portalC")).setDestinationLocation(11, 17);
		}
		if (!m_isHorsesShown) {
			this.displayDialogue(new String[] {"You realize that you are in the zoo.",
					"Or rather, what used to be the zoo before it was converted " +
					"into a horse repository.",
					"Maybe if you free the horses, this place will be closed down for good..."});
			m_isHorsesShown = true;
			m_horseSounds = true;
			// pop up!
			final java.awt.Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
			for (m_i=0; m_i<20; m_i++) {
				Runnable thread = new Runnable() {
					public void run() {
						try {
							int randX = (int)(Math.random()*(ScreenSize.getWidth()-300));
							int randY = (int)(Math.random()*(ScreenSize.getHeight()-300));
							int randHorse = (int)(Math.random()*5)+1;
							MainFrame frame = new MainFrame(randX, randY, 256, 256, 
									"assets/popupHorses/popup_horse0"+randHorse+".png");
							m_frames.add(frame);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				};
				m_threads.add(thread);
				thread.run();
			}

		}
	}
	
	@Override
	public void additionalInit(GameContainer container, StateBasedGame stateManager) throws SlickException {
		this.m_tiledMap = new TiledMap("assets/maps/dolphinEntrance.tmx");
		this.m_map = new simpleMap();
		m_playerX = SIZE*2;
		m_playerY = SIZE*8;
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
		
		m_threads = new ArrayList<Runnable>();
		
		if(!this.isLoaded()) {
			//m_interactables = new HashMap<String, Interactable>();
			//m_objects = new HashMap<String, GameObject>();
			m_dialogue = new HashMap<Integer, Dialogue>(); // think about whether this needs to be a hashmap instead
			
			StaticObject entrance = new StaticObject("entrance", SIZE, 0, "assets/dolphinProtectors.png");
			entrance.setRenderPriority(5);
			this.addObject(entrance, false);
			
			
			InvisiblePortal portalA = new InvisiblePortal("portalA", 2*SIZE, SIZE, StateManager.DOLPHIN_STATE, -1,-1);
			InvisiblePortal portalB = new InvisiblePortal("portalB", 3*SIZE, SIZE, StateManager.DOLPHIN_STATE, -1,-1);
			this.addObject(portalA, true);
			this.addObject(portalB, true);
			
			StaticObject doormat = new StaticObject("doormat", 2*SIZE, 8*SIZE, "assets/gameObjects/doormat.png");
			this.addObject(doormat, false);
			InvisiblePortal portalC = new InvisiblePortal("portalC", 2*SIZE, 9*SIZE, StateManager.TOWN_DAY_STATE, 8, 14);
			this.addObject(portalC, true);


			SmallPlug smallPlug = new SmallPlug("smallPlug", 1*SIZE, 3*SIZE);
			this.addObject(smallPlug, true);
			
			StaticObject note = new StaticObject("note", 4*SIZE, 5*SIZE, "assets/gameObjects/notepad.png");

			note.setDialogue(new String[] {"Hmm... why would there be a random note on the ground?",
					"Never minding that, you read it.",
					"\"Hello, adventurer!\"",
					"\"Perhaps you wished to enter the domain of the GREAT SUPERINTELLIGENT DOLPHIN.\"",
					"\"I would advise you to reconsider. The chamber is filled with WATER, which is, " +
					"as you might know, toxic for your sustained health.\"",
					"\"You are well advised to find some clever method of lowering the water level.\""});
			this.addObject(note, true);
		}
	}
	public void setFree(){
		m_freed = true;
	}
	public void freeHorses(){
		long time = System.currentTimeMillis();
		
		
		try {
			Sound release = new Sound("assets/sounds/CageOpening.wav");
			int cage = 0;
			for(MainFrame frame: m_frames){
				while(System.currentTimeMillis()-time<500){
					
				}
				if(cage%5==0){
					playHorseNoise();
				}
				cage+=1;
				time = System.currentTimeMillis();
				release.play();
				frame.setVisible(false);
			}

		} catch (SlickException e) {
			for(MainFrame frame: m_frames){
				while(System.currentTimeMillis()-time<500){
					
				}
				time = System.currentTimeMillis();
				frame.setVisible(false);
			}
		}
		m_horseSounds = false;
	}
	@Override
	public void setupObjects(int city, int dream) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setupDialogue(GameContainer container, int city, int dream)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void dialogueListener(Interactable i) {
		// TODO Auto-generated method stub

	}
	public void playHorseNoise(){
		try{
			int sw = (int) (Math.random()*10);
			while(sw==m_lastSound){
				sw =(int) (Math.random()*10);
			}
			m_lastSound = sw;
			switch(sw){
			case 0: {
				Sound noise = new Sound("assets/sounds/HorseNoise1.wav");
				noise.play();
				break;
			}
			case 1: {
				Sound noise = new Sound("assets/sounds/HorseNoise2.wav");
				noise.play();
				break;
			}
			case 2: {
				Sound noise = new Sound("assets/sounds/HorseNoise3.wav");
				noise.play();
				break;
			}
			case 3: {
				Sound noise = new Sound("assets/sounds/HorseNoise4.wav");
				noise.play();
				break;
			}
			case 4: {
				Sound noise = new Sound("assets/sounds/HorseNoise5.wav");
				noise.play();
				break;
			}
			case 5: {
				Sound noise = new Sound("assets/sounds/HorseNoise6.wav");
				noise.play();
				break;
			}
			case 6: {
				Sound noise = new Sound("assets/sounds/HorseNoise7.wav");
				noise.play();
				break;
			}
			case 7: {
				Sound noise = new Sound("assets/sounds/HorseNoise8.wav");
				noise.play();
				break;
			}
			case 8: {
				Sound noise = new Sound("assets/sounds/HorseNoise9.wav");
				noise.play();
				break;
			}
			default: {
				Sound noise = new Sound("assets/sounds/HorseNoise10.wav");
				noise.play();
				break;
			}
			}
		}catch(SlickException e){
			e.printStackTrace();
		}
	}

}
