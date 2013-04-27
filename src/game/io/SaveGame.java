package game.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import game.Player;
import game.Room;

/**
 * Creates a save game file.
 *
 */
public class SaveGame {
	
	private String m_savePath;
	
	public SaveGame(String path) {
		this.m_savePath = path;
	}
	
	public SaveGame() {
		this.m_savePath = "save1.xml";
	}
	
	/**
	 * Saves the current game, given a state manager. This only works if
	 * the current state is a Room state.
	 * @param stateManager
	 * @throws FileNotFoundException 
	 * @throws XMLStreamException 
	 */
	public void save(StateBasedGame stateManager) throws FileNotFoundException, XMLStreamException {
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
			
			room.writeToXML(writer);
			
			
			Player p = room.getPlayer();
			p.writeToXML(writer);
			
			writer.writeEndDocument();
		}
	}
	

}
