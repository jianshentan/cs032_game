package game;

import game.GameObject.Types;

import org.newdawn.slick.SlickException;
import org.w3c.dom.Node;

/**
 * Support class for interactable objects- 
 *
 */
public class Interactables {
	
	
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
		} else if(type.equals(Types.CHICKEN_WING.toString())) {
			//return ChickenWing.loadFromNode(node);
		} else if(type.equals(Types.CIGARETTE.toString())) {
			
		} else if(type.equals(Types.DOOR.toString())) {
			
		}
		return null;
		
	}

}
