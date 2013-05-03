package game.interactables;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.Collectable;
import game.gameplayStates.GamePlayState;

public class Wrench extends Collectable {

	public Wrench(int xLoc, int yLoc) throws SlickException {
		m_x = xLoc;
		m_y = yLoc;
		this.setKey(GamePlayState.positionToKey(getSquare()));
		setSprite(new Image("assets/gameObjects/wrench.png"));	
	}

	@Override
	public String getItemName() {
		return "Wrench";
	}

	@Override
	public String getItemText() {
		return "Suggested usage -> 'fix face with it'. But perhaps a wrench will come of value in other ways";
	}

	@Override
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub

	}

	@Override
	public Types getType() {
		return Types.WRENCH;
	}

}
