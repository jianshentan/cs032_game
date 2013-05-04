package game.interactables;

import game.GameObject;
import game.StateManager;
import game.GameObject.Types;
import game.gameplayStates.GamePlayState;
import game.player.Player;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Door extends PortalObject {
	
	public Door(String name, int xLoc, int yLoc, 
				int destination, int xDestination, int yDestination) throws SlickException {
		super(name, xLoc, yLoc, destination, xDestination, yDestination);
		setSprite(new Image("assets/gameObjects/door.png"));
	}

	@Override
	public Types getType() {
		return Types.DOOR;
	}

	@Override
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub
		
	}

}
