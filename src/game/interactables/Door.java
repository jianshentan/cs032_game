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
import org.newdawn.slick.Sound;

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
	public void writeAttributes(XMLStreamWriter writer) throws XMLStreamException {
		
	}
	public void additionalFireAction(GamePlayState state, Player p){
		try {
			Sound doorOpen = new Sound("assets/sounds/doorSound.wav");
			doorOpen.play();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
