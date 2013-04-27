package game;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ChickenWing extends GameObject implements Collectable, Interactable{
//	private Image m_sprite;
//	public static final int SIZE = 64;
//	private int m_x, m_y;
//	public int getX() {return m_x;}
//	public int getY() {return m_y;}
//	public Image getImage() {return m_sprite;}
	
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
		int[] loc = {m_x/SIZE, m_y/SIZE};
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
