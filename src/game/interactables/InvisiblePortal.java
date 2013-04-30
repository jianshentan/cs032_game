package game.interactables;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class InvisiblePortal extends PortalObject {

	public InvisiblePortal(int key, int xLoc, int yLoc, int destination,
			int xDestination, int yDestination) throws SlickException {
		super(key, xLoc, yLoc, destination, xDestination, yDestination);
		setSprite(new Image("assets/colors/clear.png")); 
	}

	@Override
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub

	}

	@Override
	public Types getType() {
		return Types.DOOR_MAT;
	}

}
