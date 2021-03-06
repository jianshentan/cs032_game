package game;

import game.io.LoadGame;
import game.io.SaveGame;

import java.io.FileNotFoundException;

import javax.xml.stream.XMLStreamException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MainMenu extends BasicGameState {
	
	private int m_stateID = 0;
	private float m_x = 0;
    private float m_y = 0;
	private float m_width = 600;
	private float m_height = 600;
 	private Rectangle m_rectangle;
	private ShapeFill m_shapeFill;
	
	private int m_mancounter = 0;
	private Animation m_animatedman;
	
	private int m_selection = 0;
	private int m_inputDelta = 0;
	
	private Image m_background = null;
	private Image m_startButton = null;
	private static int m_startButtonX = 500;
	private static int m_startButtonY = 100;

	public MainMenu(int stateID) {
		m_stateID = stateID;
		
		m_rectangle = new Rectangle(m_x, m_y, m_width, m_height);
		m_shapeFill = new GradientFill(m_x, m_y, Color.white,
									   m_x + m_width, m_y + m_height, Color.white);
		
		try {
			SpriteSheet spritefile = new SpriteSheet("assets/characters/player.png",64,64);
			Image [] image_man = new Image[] {spritefile.getSprite(0, 0),spritefile.getSprite(3,0),spritefile.getSprite(1,0),
					spritefile.getSprite(2,0)};
			int [] durations = {10,10,10,10};
			this.m_animatedman = new Animation(image_man,durations,false);
			
		} catch (SlickException e) {
			
		}
		
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame stateManager)
			throws SlickException {
		m_background = new Image("assets/MainMenuBackgroundImage.png");
		m_startButton = new Image("assets/steakTile.png");
	}

	@Override
	public void render(GameContainer container, StateBasedGame stateManager, Graphics g)
			throws SlickException {
		m_background.draw(0,0);
		
		g.setColor(Color.white);
		//g.drawString("continue", m_width/2 - 35, m_height/2 + 35);
		g.drawString("start", m_width/2 - 20, m_height/2 + 35);
		g.drawString("quit", m_width/2 -20, m_height/2 + 70);
		
		switch (m_selection) {
		case 0:
			g.drawString("<", m_width/2 + 40, m_height/2 + 35);
			break;
		case 1:
			g.drawString("<", m_width/2 + 40, m_height/2 + 70);
			break;
		}
		
		this.m_animatedman.draw(m_width/2 - 32, m_height/2 - 120);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame stateManager, int delta)
			throws SlickException {
        this.m_mancounter++;
        
        if (m_mancounter % 150 == 0) {
        	int frame = m_animatedman.getFrame();
            if (frame == 3) {
            	frame = -1;
            }
            m_animatedman.setCurrentFrame(frame + 1);
        }
        
		
		
		
		Input input = container.getInput();
		m_inputDelta-=delta;
        if (m_inputDelta<0 && input.isKeyDown(Input.KEY_UP)) {
        	if (m_selection > 0)
        		m_selection--;
        	m_inputDelta=200;
        }
        else if (m_inputDelta<0 && input.isKeyDown(Input.KEY_DOWN)) {
        	if (m_selection < 1)
        		m_selection++;
        	m_inputDelta=200;
        }
        if(m_inputDelta<0 && input.isKeyDown(Input.KEY_SPACE)){
        	if (m_selection == 0) {
        		stateManager.initStatesList(container);
        		if (StateManager.m_debugMode)
        			stateManager.enterState(StateManager.ROOM_STATE);
        		else
        			stateManager.enterState(StateManager.HOME_STATE,
        					new FadeOutTransition(Color.black, 1000), 
        					new FadeInTransition(Color.black, 1000));
        	}
        	else if (m_selection == 1)
				System.exit(0);
        	m_inputDelta=200;
        }			
	}

	@Override
	public int getID() {
		return m_stateID;
	}

}
