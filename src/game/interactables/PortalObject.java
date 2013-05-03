package game.interactables;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import game.GameObject;
import game.StateManager;
import game.gameplayStates.GamePlayState;
import game.player.Player;

public abstract class PortalObject extends GameObject implements Interactable {

	protected int m_destination;
	protected int m_xDestination, m_yDestination;
	protected int m_key;
	
	@Override 
	public int getKey() {return m_key;}
	
	/**
	 * classes that inherit from this needs to define a spritePath 
	 * @param key
	 * @param xLoc
	 * @param yLoc
	 * @param destination 
	 * @param xDestination If this is < 0, then the destination 
	 * 		  location state will use the default initial position
	 * @param yDestination If this is < 0, then the destination
	 * 		  location state will use the default initial position
	 * @throws SlickException
	 */
	public PortalObject(int key, int xLoc, int yLoc,
						int destination, int xDestination, int yDestination) throws SlickException {
		m_x = xLoc;
		m_y = yLoc;
		m_key = key;
		m_destination = destination;
		m_xDestination = xDestination;
		m_yDestination = yDestination;
	}

	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		StateManager.getInstance().enterState(m_destination, 
				new FadeOutTransition(Color.black, 1000), 
				new FadeInTransition(Color.black, 1000));
		
		GamePlayState destinationState = (GamePlayState)StateManager.getInstance().getState(m_destination);
		if (m_xDestination >= 0 && m_xDestination >= 0)
			destinationState.setPlayerLocation(m_xDestination*SIZE, m_yDestination*SIZE);
		additionalFireAction(state, p);
		return this;
	}
	
	// if the object needs to do more than just prompt a change of location on fireAction, it can
	// be done here in classes that inherit from it
	public void additionalFireAction(GamePlayState state, Player p){}
}
