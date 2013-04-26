package game;

import org.newdawn.slick.SlickException;
import org.w3c.dom.Node;

/**
 * Support class for interactable objects- 
 *
 */
public class Interactables {
	
	/**
	 * All types of interactables
	 *
	 */
	public static enum Types {
		CHEST,
	}
	
	/**
	 * Loads an interactable from an XML node
	 * @param node
	 * @return an Interactable
	 * @throws SlickException 
	 */
	public static Interactable loadFromNode(Node node) throws SlickException {
		//TODO
		String type = node.getAttributes().getNamedItem("type").getNodeValue();
		if(type.equals(Types.CHEST.toString())) {
			return Chest.loadFromNode(node);
		}
		return null;
		
	}

}
