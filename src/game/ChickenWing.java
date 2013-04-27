package game;

import game.Interactables.Types;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ChickenWing extends GameObject implements Collectable, Interactable{
	
	public ChickenWing(int xLoc, int yLoc) throws SlickException {
		m_x = xLoc;
		m_y = yLoc;
		m_sprite = new Image("assets/chickenWing.png");
	}
	@Override
	public Interactable fireAction() {
		return this;
	}
	
	@Override
	public int[] getSquare() {
		int[] loc = {(int)m_x/SIZE, (int)m_y/SIZE};
		return loc;
	}
	@Override
	public Types getType() {
		return Types.CHICKEN_WING;
	}
	@Override
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub
		writer.writeStartElement("Iterable");
		writer.writeAttribute("type", Types.CHICKEN_WING.toString());
		writer.writeAttribute("m_x", String.valueOf(this.m_x));
		writer.writeAttribute("m_y", String.valueOf(this.m_y));
		writer.writeEndElement();	
	}
	
}
