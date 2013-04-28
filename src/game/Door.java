package game;

import game.GameObject.Types;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Door extends GameObject implements Interactable {

	private int m_destination;
	private int m_key;
	@Override
	public int getKey() {return m_key;}
	
	public Door(int key, int xLoc, int yLoc, int destination) throws SlickException {
		m_x = xLoc;
		m_y = yLoc;
		m_sprite = new Image("assets/door.png");
		m_key = key;
		m_destination = destination;
	}

	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		// change to kitchen state
		System.out.println("HERE");
		StateManager.getInstance().enterState(m_destination);
		return this;
	}

	@Override
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		writer.writeStartElement("Interactable");
		writer.writeAttribute("type", GameObject.Types.DOOR.toString());
		writer.writeAttribute("m_x", String.valueOf(m_x));
		writer.writeAttribute("m_y", String.valueOf(m_y));
		writer.writeAttribute("m_destination", String.valueOf(m_destination));
		writer.writeEndElement();
	}

	@Override
	public int[] getSquare() {
		int[] loc = {(int)m_x/SIZE, (int)m_y/SIZE};
		return loc;
	}

	@Override
	public Types getType() {
		return Types.DOOR;
	}

}
