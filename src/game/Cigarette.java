package game;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Cigarette extends Collectable implements Interactable {
	
	public Cigarette(int xLoc, int yLoc) throws SlickException {
		m_x = xLoc;
		m_y = yLoc;
		this.setKey(GamePlayState.positionToKey(getSquare()));
		m_sprite = new Image("assets/cigarette.png");
	}

	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		state.removeObject(this.getKey(), (int) m_x/SIZE, (int) this.m_y/SIZE);
		return this;
	}

	@Override
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		writer.writeStartElement("Interactable");
		writer.writeAttribute("type", GameObject.Types.CIGARETTE.toString());
		writer.writeEndElement();
	}

	@Override
	public String getItemName() {
		return "Cigarette";
	}

	@Override
	public String getItemText() {
		return "A smokey little thing... It might one day save your life";
	}

	@Override
	public int[] getSquare() {
		int[] loc = {(int)m_x/SIZE, (int)m_y/SIZE};
		return loc;
	}

	@Override
	public Types getType() {
		return Types.CIGARETTE;
	}

}
