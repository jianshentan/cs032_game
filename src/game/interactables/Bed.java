package game.interactables;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import game.GameObject;
import game.StateManager;
import game.gameplayStates.GamePlayState;
import game.player.Player;

public class Bed extends PortalObject {

	public Bed(String name, int xLoc, int yLoc,
			int destination, int xDestination, int yDestination) throws SlickException {
		super(name, xLoc, yLoc, destination, xDestination, yDestination);
		setSprite(new Image("assets/gameObjects/bed.png"));
	}

	@Override
	public Types getType() {
		return Types.BED;
	}
	
	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		if (m_destination == StateManager.TOWN_NIGHT_STATE)
			StateManager.getInstance().enterState(m_destination,
					new FadeOutTransition(Color.white, 2500),
					new FadeInTransition(Color.white, 2500));
		
		GamePlayState destinationState = (GamePlayState)StateManager.getInstance().getState(m_destination);
		if (m_xDestination >= 0 && m_xDestination >= 0)
			destinationState.setPlayerLocation(m_xDestination, m_yDestination);
		additionalFireAction(state, p);
		return this;
	}
	@Override
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub
	}

}
