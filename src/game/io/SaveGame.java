package game.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.w3c.dom.Document;

import game.MainMenu;
import game.gameplayStates.GamePlayState;
import game.player.Player;

/**
 * Creates a save game file.
 *
 */
public class SaveGame {
	
	private static SaveGame instance;
	private String m_savePath;
	private Document m_saveDoc;
	
	public static SaveGame getInstance() {
		if(instance==null) {
			instance = new SaveGame();
			return instance;
		} else {
			return instance;
		}
	}
	
	private SaveGame(String path) {
		this.m_savePath = path;
	}
	
	private SaveGame() {
		this.m_savePath = "save1.xml";
	}
	
	public void addState(int stateID, GamePlayState state) {
		
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
		GamePlayState room = (GamePlayState) currentState;
		Player p = room.getPlayer();
		XMLOutputFactory xmlFactory = XMLOutputFactory.newFactory();
		XMLStreamWriter writer = xmlFactory.createXMLStreamWriter(outputStream);
		writer.writeStartDocument();
		writer.writeStartElement("SaveData");
		
		writer.writeAttribute("currentState", String.valueOf(stateManager.getCurrentStateID()));
		
		p.writeToXML(writer);
		for(int id = 0; id< stateManager.getStateCount(); id++) {
			currentState = stateManager.getState(id);
			if(currentState == null || currentState.getClass() == MainMenu.class) {
				continue;
			}
			room = (GamePlayState) currentState;
			
			room.writeToXML(writer);
		}
		writer.writeEndElement();
		writer.writeEndDocument();
		writer.close();
	}
	
	/**
	 * Saves a GamePlayState.
	 * @param room
	 * @throws XMLStreamException
	 * @throws FileNotFoundException
	 */
	public void save(GamePlayState room) throws XMLStreamException, FileNotFoundException {
		FileOutputStream outputStream = new FileOutputStream(new File(m_savePath));
		XMLOutputFactory xmlFactory = XMLOutputFactory.newFactory();
		XMLStreamWriter writer = xmlFactory.createXMLStreamWriter(outputStream);
		writer.writeStartDocument();
		writer.writeStartElement("SaveData");
		writer.writeStartElement("State");
		writer.writeAttribute("stateID", String.valueOf(room.getID()));
		writer.writeEndElement();
		
		room.writeToXML(writer);
		
		
		Player p = room.getPlayer();
		p.writeToXML(writer);
		
		writer.writeEndDocument();
		writer.close();
	}
	

}
