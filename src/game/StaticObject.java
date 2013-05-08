package game;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import game.gameplayStates.GamePlayState;
import game.interactables.Interactable;
import game.io.SaveGame;
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
	private String m_spritePath;
	
	public StaticObject(String name, int xLoc, int yLoc, String spritePath) throws SlickException {
		super(name);
		m_x = xLoc;
		m_y = yLoc;
		m_spritePath = spritePath;
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
	public void writeAttributes(XMLStreamWriter writer) throws XMLStreamException {
		writer.writeAttribute("m_spritePath", m_spritePath);
		if(m_dialogue!=null) {
			writer.writeStartElement("m_dialogue");
			writer.writeAttribute("length", String.valueOf(m_dialogue.length));
			SaveGame.save(writer, m_dialogue);
			writer.writeEndElement();
		}
		
	}

}
