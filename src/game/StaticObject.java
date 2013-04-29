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
	
	private int m_key;
	public int getKey() {return m_key;}

	public StaticObject(int xLoc, int yLoc, String spritePath) throws SlickException {
		m_x = xLoc;
		m_y = yLoc;
		String s1 = String.valueOf(xLoc);
		String s2 = String.valueOf(yLoc);
		m_key = Integer.parseInt(s1+s2);
		setSprite(new Image(spritePath));
	}
	

	@Override
	public Types getType() {
		return Types.STATIC;
	}

	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		return this;
	}

	@Override
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub
		
	}

}
