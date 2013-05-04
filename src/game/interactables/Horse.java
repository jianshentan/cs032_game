package game.interactables;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


import game.AIState;
import game.Direction;
import game.Enemy;
import game.GameObject;
import game.GameObject.Types;
import game.MovingObject;

import org.newdawn.slick.Image;
import game.gameplayStates.GamePlayState;
import game.player.Player;

public class Horse extends Enemy implements Interactable{
	
	public Horse(int key, GamePlayState room, Player player, float x, float y,int xTarget, int yTarget) throws SlickException {
		super(room, player, x, y);
		SpriteSheet horsesheet = new SpriteSheet("assets/HorseStampede.png",256,256);
		this.setSprite(horsesheet.getSprite(0,0));
		//this.m_key = key;
		this.setLeadTo(xTarget, yTarget);
		this.m_ai = AIState.LEAD;
		int [] duration = {200,200};
		this.m_sprite = new Animation(new Image[] {horsesheet.getSprite(0,0),horsesheet.getSprite(1,0)}, duration, true);
		m_up = m_sprite;
        m_down = m_sprite;
        m_left = m_sprite;
        m_right = m_sprite;	
        
        m_up_stand = m_sprite;
        m_down_stand = m_sprite;
        m_left_stand = m_sprite;
        m_right_stand = m_sprite;
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
		return GameObject.Types.HORSE;
	}

}
