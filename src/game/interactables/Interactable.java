package game.interactables;

import game.GameObject;
import game.gameplayStates.GamePlayState;
import game.player.Player;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Objects that implement this class are basically interactive
 * objects in the game world.
 *
 */
public interface Interactable {
	
	/**
	 * Returns the globally unique key.
	 * @return
	 */
	public int getKey();
	/**
	 * Returns the name of the interactable.
	 * Names should be unique within a GameState.
	 * @return String
	 */
	public String getName();
	/**
	 * Fires the interactable's action.
	 * @return this interactable
	 */
	public Interactable fireAction(GamePlayState state, Player p);
	/**
	 * Returns the x,y position on the tile map that the
	 * interactable is located at.
	 * @return
	 */
	public int[] getSquare();
	/**
	 * Writes the Interactable as an XML element.
	 * Have to start with writer.writeStartElement("Interactable")
	 * and end with writer.writeEndElement();
	 * @param writer
	 */
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException;
	/**
	 * Returns the type of the interactable, as a GameObject type.
	 * @return
	 */
	public GameObject.Types getType();
	
}
