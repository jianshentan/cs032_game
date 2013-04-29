package game.interactables;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.GameObject;
import game.gameplayStates.GamePlayState;
import game.player.Player;

public class Bed extends GameObject implements Interactable{

	private int m_key;
	public int getKey() {return m_key;}
	
	public Bed(int key, int xLoc, int yLoc) throws SlickException {
		m_x = xLoc;
		m_y = yLoc;
		setSprite(new Image("assets/gameObjects/bed.png"));
		m_key = key;
	}

	@Override
	public int[] getSquare() {
		int[] loc = {(int)m_x/SIZE, (int)m_y/SIZE}; 
		return loc;
	}
	
	@Override
	public Types getType() {
		return Types.BED;
	}

	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		System.out.println("interacting with bed");
		return null;
	}

	@Override
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub
	}

}
