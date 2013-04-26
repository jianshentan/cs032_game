package game;


import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import game.Interactables.Types;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.w3c.dom.Node;

public class Chest extends GameObject implements Interactable{
	public static final int SIZE = 64;
	private Image m_sprite, m_open, m_closed;
	private int m_x, m_y;
	public int getX() { return m_x; }
	public int getY() { return m_y; }
	public Image getImage(){return m_sprite;}
	
	public Chest(int xLoc, int yLoc) throws SlickException{
		m_x = xLoc;
		m_y = yLoc;
		m_closed = new Image("assets/chestClose.png");
		m_open = new Image("assets/chestOpen.png");
		m_sprite = m_closed;
	}

	@Override
	public void fireAction() {
		if(m_sprite.equals(m_closed)){
			m_sprite = m_open;
		}else{
			m_sprite = m_closed;
		}
		
	}
	@Override
	public int[] getSquare() {
		int[] loc = {m_x/SIZE, m_y/SIZE};
		return loc;
	}
	@Override
	public Types getType() {
		return Types.CHEST;
	}
	
	@Override
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub
		writer.writeStartElement("Iterable");
		writer.writeAttribute("type", Types.CHEST.toString());
		writer.writeAttribute("m_x", String.valueOf(this.m_x));
		writer.writeAttribute("m_y", String.valueOf(this.m_y));
		writer.writeEndElement();
	}
	
	/**
	 * Loads a Chest from an XML node.
	 * @param node
	 * @return
	 * @throws SlickException 
	 */
	public static Chest loadFromNode(Node node) throws SlickException {
		int xLoc = Integer.parseInt(node.getAttributes().getNamedItem("m_x").getNodeValue());
		int yLoc = Integer.parseInt(node.getAttributes().getNamedItem("m_y").getNodeValue());
		return new Chest(xLoc, yLoc);
	}
}
