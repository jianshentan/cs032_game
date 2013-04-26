package game.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.state.GameState;

import game.Interactable;
import game.Player;
import game.Room;
import game.StateManager;

/**
 * Creates a save game file.
 *
 */
public class SaveGame {
	
	private String m_savePath;
	
	public SaveGame(String path) {
		this.m_savePath = path;
	}
	
	/**
	 * Saves the current game, given a state manager. This only works if
	 * the current state is a Room state.
	 * @param stateManager
	 * @throws FileNotFoundException 
	 * @throws XMLStreamException 
	 */
	public void save(StateManager stateManager) throws FileNotFoundException, XMLStreamException {
		FileOutputStream outputStream = new FileOutputStream(new File(m_savePath));
		GameState currentState = stateManager.getCurrentState();
		XMLOutputFactory xmlFactory = XMLOutputFactory.newFactory();
		if(currentState.getClass() == Room.class) {
			Room room = (Room) currentState;
			XMLStreamWriter writer = xmlFactory.createXMLStreamWriter(outputStream);
			writer.writeStartDocument();
			
			writer.writeStartElement("State");
			writer.writeAttribute("stateID", String.valueOf(room.getID()));
			writer.writeEndElement();
			
			writer.writeStartElement("Room");
			writer.writeAttribute("mapPath", room.getMapPath());
			
			writer.writeStartElement("Interactables");
			for(Interactable i : room.getInteractables()) {
				i.writeToXML(writer);
			}
			writer.writeEndElement();
			
			writer.writeEndElement();
			
			
			Player p = room.getPlayer();
			writer.writeStartElement("Player");
			writer.writeAttribute("xPos", String.valueOf(p.getX()));
			writer.writeAttribute("yPos", String.valueOf(p.getY()));
			
			writer.writeStartElement("Inventory");
			writer.writeEndElement();
			
			writer.writeEndElement();
			
			writer.writeEndDocument();
		}
	}
	

}
