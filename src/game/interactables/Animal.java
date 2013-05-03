package game.interactables;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Image;

import game.GameObject;
import game.gameplayStates.GamePlayState;
import game.player.Player;

public class Animal extends GameObject implements Interactable {
	
	private int m_key;
	
	public Animal(int key, Image image) {
		this.setSprite(image);
		this.m_key = key;
	}

	@Override
	public int getKey() {
		return m_key;
	}

	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub

	}

	@Override
	public Types getType() {
		// TODO Auto-generated method stub
		return GameObject.Types.ANIMAL;
	}

}
