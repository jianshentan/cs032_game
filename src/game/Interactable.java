package game;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Objects that implement this class are basically interactive
 * objects in the game world.
 *
 */
public interface Interactable {
	
	public void fireAction();
	/**
	 * Returns the x,y position that the player
	 * has to be at.
	 * @return
	 */
	public int[] getSquare();
	/**
	 * 
	 * @return
	 */
	public Interactables.Types getType();
	/**
	 * Writes the Interactable as an XML element.
	 * Have to start with writer.writeStartElement("Iterable")
	 * and end with writer.writeEndElement();
	 * @param writer
	 */
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException;
	
}
