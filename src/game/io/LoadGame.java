package game.io;

import game.Player;
import game.Room;
import game.StateManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.newdawn.slick.state.GameState;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LoadGame {
	
private String m_loadPath;
	
	public LoadGame(String path) {
		this.m_loadPath = path;
	}
	
	/**
	 * Saves the current game, given a state manager.
	 * @param stateManager
	 * @throws XMLStreamException 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public void load(StateManager stateManager) throws XMLStreamException, ParserConfigurationException, SAXException, IOException {
		InputStream input = new FileInputStream(m_loadPath);
		GameState currentState = stateManager.getCurrentState();
		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
		DocumentBuilder b = f.newDocumentBuilder();
		Document d = b.parse(input);
		NodeList players = d.getElementsByTagName("Player");
		Node p = players.item(0);
		d.getElementsByTagName("Room");
		d.getElementsByTagName("State");
		
	}
	
	public Player getPlayer(XMLEvent v) {
		
		return null;
	}
	

}
