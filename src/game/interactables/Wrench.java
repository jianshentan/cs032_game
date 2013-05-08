package game.interactables;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.Collectable;
import game.gameplayStates.GamePlayState;

public class Wrench extends Collectable {

	public Wrench(int xLoc, int yLoc) throws SlickException {
		super();
		this.setName("wrench");
		m_x = xLoc;
		m_y = yLoc;
		setSprite(new Image("assets/gameObjects/wrench.png"));	
	}

	@Override
	public String getItemName() {
		return "Wrench";
	}

	@Override
	public String getItemText() {
		return "Looks pretty strong. Perhaps it can be used to break stuff.";
	}

	@Override
	public void writeAttributes(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub

	}

	@Override
	public Types getType() {
		return Types.WRENCH;
	}

}
