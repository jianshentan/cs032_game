package game.interactables;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.GameObject;
import game.gameplayStates.GamePlayState;
import game.player.Player;

public class Animal extends GameObject implements Interactable {
	
	private int m_key;
	
	public Animal(int key, String imagePath) throws SlickException {
		this.setSprite(new Image(imagePath));
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
		return GameObject.Types.ANIMAL;
	}

}
