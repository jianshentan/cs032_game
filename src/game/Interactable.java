package game;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Objects that implement this class are basically interactive
 * objects in the game world.
 *
 */
public interface Interactable {
	
	public Interactable fireAction();
	/**
	 * Returns the x,y position on the tile map that the
	 * interactable is located at.
	 * @return
	 */
	public int[] getSquare();
	/**
	 * Writes the Interactable as an XML element.
	 * Have to start with writer.writeStartElement("Iterable")
	 * and end with writer.writeEndElement();
	 * @param writer
	 */
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException;
	
	public GameObject.Types getType();
	
}
