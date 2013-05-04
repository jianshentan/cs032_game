package game.interactables;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


import game.Enemy;
import game.GameObject;
import game.GameObject.Types;
import game.MovingObject;

import org.newdawn.slick.Image;
import game.gameplayStates.GamePlayState;
import game.player.Player;

public class Horse extends Enemy implements Interactable{

	private int key;
	private Image m_norm, m_run;
	
	public Horse(GamePlayState room, Player player, float x, float y) throws SlickException {
		super(room, player, x, y);
		
		SpriteSheet horsesheet = new SpriteSheet("assets/Horses.png",128,128);
		
		
	}

	
	
	
	@Override
	public int getKey() {
		return key;
	}

	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getSquare() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Types getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
