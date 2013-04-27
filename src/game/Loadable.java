package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.w3c.dom.Node;

/**
 * Classes that implement this interface can be loaded
 * from an XML node.
 *
 */
public interface Loadable<E> {

	public E loadFromXML(Node n, GameContainer c, StateBasedGame g) throws SlickException;
	
}
