package game;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import game.GameObject.Types;
import game.gameplayStates.GamePlayState;
import game.interactables.Interactable;
import game.player.Player;

public class Person extends GameObject implements Interactable{
	
	private Image m_up, m_down, m_left, m_right;
	private Direction m_dir;
	private String[] m_dialogue;
	private int key;
	private Collectable m_item = null;
	
	public Person(int key, int xLoc, int yLoc, String spritefile, Collectable item) throws SlickException {
		m_x = xLoc;
		m_y = yLoc;
		this.key = key;
		
		if (item != null)
			m_item = item;
		
		SpriteSheet spritesheet = new SpriteSheet(spritefile, 64,64);
		m_up = spritesheet.getSprite(1,0);
		m_down = spritesheet.getSprite(0,0);
		m_left = spritesheet.getSprite(3,0);
		m_right = spritesheet.getSprite(2,0);
		
		setSprite(m_down);
		m_dir = Direction.LEFT;
	}
	
	public void setDialogue(String[] text) {
		this.m_dialogue = text;
	}
	
	@Override
	public int getKey() {
		return key;
	}
	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		Direction playerfacing = p.getDirection();
		switch (playerfacing) {
    	case UP:
    		setSprite(m_down);
    		break;
    	case DOWN:
    		setSprite(m_up);
    		break;
    	case LEFT:
    		setSprite(m_right);
    		break;
    	case RIGHT:
    		setSprite(m_left);
    		break;
    	}
		if(this.m_dialogue!=null) {
			state.displayDialogue(m_dialogue);
		}
		if (m_item != null) {
			p.addToInventory(m_item);
			m_item = null;
		}
		return this;
	}
	@Override
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Types getType() {
		// TODO Auto-generated method stub
		return GameObject.Types.PERSON;
	}

}
