package game;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import game.gameplayStates.GamePlayState;
import game.interactables.Interactable;
import game.player.Player;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Objects that don't move 
 * but can still prompt an interaction - typically dialogue
 * This class allows the spritePath to be added externally
 */
public class StaticObject extends GameObject implements Interactable{
	
	private String[] m_dialogue;
	
	public StaticObject(String name, int xLoc, int yLoc, String spritePath) throws SlickException {
		super(name);
		m_x = xLoc;
		m_y = yLoc;
		String s1 = String.valueOf(xLoc);
		String s2 = String.valueOf(yLoc);
		setSprite(new Image(spritePath));
	}
	
	/**
	 * Sets dialogue to be displayed on interaction
	 * @param s - String[]
	 */
	public void setDialogue(String[] s) {
		m_dialogue = s;
	}
	

	@Override
	public Types getType() {
		return Types.STATIC;
	}

	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		if(m_dialogue!=null)
			state.displayDialogue(m_dialogue);
		return this;
	}

	@Override
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub
		
	}

}
