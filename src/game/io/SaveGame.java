package game.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import game.MainMenu;
import game.StateManager;
import game.gameplayStates.GamePlayState;
import game.player.Player;

/**
 * Creates a save game file.
 *
 */
public class SaveGame {
	
	private static SaveGame instance;
	private String m_savePath;
	
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
	 * @throws TransformerFactoryConfigurationError 
	 * @throws TransformerConfigurationException 
	 */
	public void save(StateBasedGame stateManager) throws FileNotFoundException, XMLStreamException, TransformerConfigurationException, TransformerFactoryConfigurationError {
		FileOutputStream outputStream = new FileOutputStream(new File(m_savePath));
		GameState currentState = stateManager.getCurrentState();
		GamePlayState room = (GamePlayState) currentState;
		Player p = room.getPlayer();
		
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		
		XMLOutputFactory xmlFactory = XMLOutputFactory.newFactory();
		XMLStreamWriter writer = xmlFactory.createXMLStreamWriter(outputStream);
		writer.writeStartDocument();
		writer.writeStartElement("SaveData");
		//TODO: first, write out all gameObjects
		
		writer.writeAttribute("currentState", String.valueOf(stateManager.getCurrentStateID()));
		writer.writeAttribute("cityState", String.valueOf(StateManager.m_cityState));
		writer.writeAttribute("dreamState", String.valueOf(StateManager.m_dreamState));
		
		writer.writeCharacters("\n");
		StateManager.writeToXML(writer);
		
		p.writeToXML(writer);
		writer.writeCharacters("\n");
		for(int id = 0; id< stateManager.getStateCount(); id++) {
			currentState = stateManager.getState(id);
			if(currentState == null || currentState.getClass() == MainMenu.class) {
				continue;
			}
			room = (GamePlayState) currentState;
			
			room.writeToXML(writer);
			writer.writeCharacters("\n");
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
	
	/**
	 * Saves a boolean array
	 * calling class would have to write start/end element tags and length attributes
	 * @param array
	 * @throws XMLStreamException 
	 */
	public static void save(XMLStreamWriter writer, boolean[][] array) throws XMLStreamException { 
		for(int i = 0; i<array.length; i++) {
			writer.writeStartElement("i"+String.valueOf(i));
			for(int j = 0; j<array[i].length; j++) {
				writer.writeAttribute("j"+String.valueOf(j), String.valueOf(array[i][j]));
			}
			writer.writeCharacters("\n");
			writer.writeEndElement();
		}
	}
	
	/**
	 * Saves a String array (dialogue)
	 * @param writer
	 * @param array
	 * @throws XMLStreamException
	 */
	public static void save(XMLStreamWriter writer, String[] array) throws XMLStreamException { 
		for(int i = 0; i<array.length; i++) {
			writer.writeAttribute("i"+String.valueOf(i), array[i]);
		}
	}
	

}
