package game.interactables;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class VisiblePortal extends PortalObject {

	public VisiblePortal(int key, int xLoc, int yLoc, int destination,
			int xDestination, int yDestination, String path) throws SlickException {
		super(key, xLoc, yLoc, destination, xDestination, yDestination);
		setSprite(new Image(path));
	}

	@Override
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub

	}

	@Override
	public Types getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
