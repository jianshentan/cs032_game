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

	/**
	 * Loads something from an XML node, given a game container and
	 * a state manager.
	 * @param n
	 * @param c
	 * @param g
	 * @return
	 * @throws SlickException
	 */
	public E loadFromXML(Node n, GameContainer c, StateManager g) throws SlickException;
	
}
