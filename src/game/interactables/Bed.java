package game.interactables;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.GameObject;
import game.gameplayStates.GamePlayState;
import game.player.Player;

public class Bed extends PortalObject {

	public Bed(int key, int xLoc, int yLoc,
			int destination, int xDestination, int yDestination) throws SlickException {
		super(key, xLoc, yLoc, destination, xDestination, yDestination);
		setSprite(new Image("assets/gameObjects/bed.png"));
	}

	@Override
	public Types getType() {
		return Types.BED;
	}

	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		System.out.println("interacting with bed");
		return null;
	}

	@Override
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub
	}

}
