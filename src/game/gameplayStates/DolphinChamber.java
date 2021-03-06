package game.gameplayStates;

import game.Scene;
import game.StateManager;
import game.interactables.BlowHole;
import game.interactables.Interactable;
import game.interactables.InvisiblePortal;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

public class DolphinChamber extends GamePlayState {
	
	private boolean m_waterDown;
	/**
	 * Turns down the water.
	 */
	public void waterDown(boolean b) {
		this.m_waterDown = b;
	}
	private boolean m_showWaterDownText;
	
	public DolphinChamber(int id) {
		m_stateID = id;
	}
	
	@Override
	public void additionalInit(GameContainer container, StateBasedGame stateManager) throws SlickException {
		this.m_tiledMap = new TiledMap("assets/maps/dolphinMap2.tmx");
		this.m_map = new simpleMap();
		m_playerX = SIZE*3;
		m_playerY = SIZE*13;
		
		if(!this.isLoaded()) {
			InvisiblePortal portalA = new InvisiblePortal("portalA", 3*SIZE, 14*SIZE, StateManager.DOLPHIN_ENTRANCE, 2,2);
			InvisiblePortal portalB = new InvisiblePortal("portalB", 4*SIZE, 14*SIZE, StateManager.DOLPHIN_ENTRANCE, 3,2);
			BlowHole bh = new BlowHole("bh", 3*SIZE, 3*SIZE);
			this.addObject(portalA, true);
			this.addObject(portalB, true);
			this.addObject(bh, true);
		}
		this.setBlockedTiles();
	}

	@Override
	public void setupObjects(int city, int dream) throws SlickException {
		// TODO Auto-generated method stub
		this.setMusic("assets/sounds/DolphinCompiled.wav");
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
	public void endEvent(){
		this.shakeCamera(4000);
		Sound explosion;
		try {
			explosion = new Sound("assets/sounds/Explosions.wav");
			explosion.play();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StateManager.getInstance().enterState(StateManager.DOLPHIN_ENTRANCE, new FadeOutTransition(Color.white, 4000), 
		new FadeInTransition(Color.white, 1000));
		DolphinEntrance destinationState = (DolphinEntrance) StateManager.getInstance().getState(StateManager.DOLPHIN_ENTRANCE);
		destinationState.setPlayerLocation(2*SIZE, 2*SIZE);
		destinationState.setFree();
		//quest is over, decrement city state
		StateManager.m_cityState--;
	}
	public void additionalEnter(GameContainer container, StateBasedGame stateManager) {
		if(this.isEntered()==false && m_waterDown == false) {
			Scene s = new Scene(this, m_player, new float[][] {{3,13}, {3,4},{3,13}});
			//s.setPlayerInvisible(true);
			s.setCamera(true);
			s.playScene();
			this.displayDialogue(new String[] {"Water, everywhere. Too bad you can't swim.",
					"Is there a way to drain the water?"});
		}
		if(m_waterDown == true) {
			try {
				this.m_tiledMap = new TiledMap("assets/maps/dolphinMap1.tmx");
			} catch (SlickException e) {
				e.printStackTrace();
			}
			this.m_map = new simpleMap();
			m_playerX = SIZE*3;
			m_playerY = SIZE*13;
			this.setBlockedTiles();
			if(m_showWaterDownText == false) {
				this.displayDialogue(new String[] {"Hey, look, the water is gone!",
						"Problem: it's a well-known fact that giant dolphins can live for up to five days " +
						"without water.",
						"You'll have to take care of it somehow."});
			}
		}
		
	}

}
