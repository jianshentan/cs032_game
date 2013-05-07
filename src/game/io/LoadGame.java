package game.io;

import game.StateManager;
import game.gameplayStates.GamePlayState;
import game.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import org.newdawn.slick.SlickException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LoadGame {
	
private String m_loadPath;
	
	public LoadGame(String path) {
		this.m_loadPath = path;
	}
	
	public LoadGame() {
		this.m_loadPath = "save1.xml";
	}
	
	/**
	 * Loads a game, returning a StateManager.
	 * 
	 * @throws XMLStreamException 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws SlickException 
	 */
	public void load(StateManager stateManager) throws XMLStreamException, 
			ParserConfigurationException, SAXException, IOException, SlickException {		
		InputStream input = new FileInputStream(m_loadPath);
		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
		DocumentBuilder b = f.newDocumentBuilder();
		Document d = b.parse(input);
		Node root = d.getFirstChild();
		
		NodeList players = d.getElementsByTagName("Player");
		Node p = players.item(0);
		
		NodeList objects = d.getElementsByTagName("GameObjects");
		//load all the objects
		
		
		NodeList rooms = d.getElementsByTagName("Room");
		
		for(int i = 0; i<rooms.getLength(); i++) {
			Node roomNode = rooms.item(i);
			int id = Integer.parseInt(roomNode.getAttributes().getNamedItem("id").getNodeValue());
			GamePlayState r2 = (GamePlayState) stateManager.getState(id);
			//Player player = Player.loadFromNode(p, r2, stateManager.getContainer());
			r2.loadFromXML(roomNode, stateManager.getContainer(), stateManager);
		}
		int id = Integer.parseInt(root.getAttributes().getNamedItem("currentState").getNodeValue());
		GamePlayState r2 = (GamePlayState) stateManager.getState(id);
		Player player = Player.loadFromNode(p, r2, stateManager.getContainer());
		r2.setPlayer(player);
		
		//Player player = Player.loadFromNode(p, r);
		//TODO: load should create an entirely new game...
		System.out.println(d.getFirstChild().getAttributes().getLength());
		stateManager.enterState(id);
	}
	
	/**
	 * This is used to create a new player for a new level, from file. It keeps
	 * the health and inventor
 * @throws ParserConfigurationException y.
	 * @param stateManager
	 * @return
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws SlickException 
	 */
	public Player initializePlayer(StateManager stateManager) throws ParserConfigurationException, SAXException, IOException, SlickException {
		InputStream input = new FileInputStream(m_loadPath);
		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
		DocumentBuilder b = f.newDocumentBuilder();
		Document d = b.parse(input);
		NodeList players = d.getElementsByTagName("Player");
		Node p = players.item(0);
		GamePlayState r2 = (GamePlayState) stateManager.getState(StateManager.ROOM_STATE);
		Player player = Player.loadFromNode(p, r2, stateManager.getContainer());
		return player;
	}
	
	

}
