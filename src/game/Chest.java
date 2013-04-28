package game;


import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.w3c.dom.Node;

public class Chest extends GameObject implements Interactable{
	private Image m_open, m_closed;
	private boolean m_isOpen;
	private int m_key;
	@Override
	public int getKey() {return m_key;}
	
	public Chest(int key, int xLoc, int yLoc) throws SlickException{
		m_x = xLoc;
		m_y = yLoc;
		m_closed = new Image("assets/chestClose.png");
		m_open = new Image("assets/chestOpen.png");
		m_sprite = m_closed;
		m_isOpen = false;
		m_key = key;
	}

	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		if(m_sprite.equals(m_closed)){
			m_isOpen = true;
			m_sprite = m_open;
		}else{
			m_isOpen = false;
			m_sprite = m_closed;
		}
		return this;
	}
	@Override
	public int[] getSquare() {
		int[] loc = {(int)m_x/SIZE, (int)m_y/SIZE};
		return loc;
	}
	@Override
	public Types getType() {
		return Types.CHEST;
	}
	
	@Override
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub
		writer.writeStartElement("Interactable");
		writer.writeAttribute("type", Types.CHEST.toString());
		writer.writeAttribute("m_x", String.valueOf(this.m_x));
		writer.writeAttribute("m_y", String.valueOf(this.m_y));
		writer.writeAttribute("m_isOpen", String.valueOf(this.m_isOpen));
		writer.writeEndElement();
	}
	
	/**
	 * Loads a Chest from an XML node.
	 * @param node
	 * @return
	 * @throws SlickException 
	 */
	public static Chest loadFromNode(Node node) throws SlickException {
		int xLoc = (int) Double.parseDouble(node.getAttributes().getNamedItem("m_x").getNodeValue());
		int yLoc = (int) Double.parseDouble(node.getAttributes().getNamedItem("m_y").getNodeValue());
		int[] position = {xLoc, yLoc};
		return new Chest(GamePlayState.positionToKey(position), xLoc, yLoc);
	}
}
