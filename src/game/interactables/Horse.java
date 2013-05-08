package game.interactables;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
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
	private GamePlayState room;
	boolean stampede, patrol;
	
	public Horse(boolean stampede, boolean patrol, String name, GamePlayState room, Player player, float x, float y,int[][] patrolpoints) throws SlickException {
		super(room, player, x, y);
		this.room = room;
		this.setName(name);
		int [] duration = {200,200};
		this.setPatrolPoints(patrolpoints);
		this.stampede = stampede;
		this.patrol = patrol;
		if (stampede == true) {
			SpriteSheet horsesheet = new SpriteSheet("assets/HorseStampede.png",256,256);
			this.setSprite(horsesheet.getSprite(0,0));
			//this.m_key = key;
			this.m_sprite = new Animation(new Image[] {horsesheet.getSprite(0,0),horsesheet.getSprite(1,0)}, duration, true);
			
		} else {
			Image horseimage = new Image("assets/horsehead.png");
			this.m_sprite = new Animation(new Image[] {horseimage,horseimage}, duration, false);
		}
		this.m_ai = AIState.ROAM;
		if (patrol==true) {
			this.m_ai = AIState.PATROL;
		} 
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
		
		if (patrol==false) {
			state.displayDialogue(new String[] {"Oh jeez it's an angry horse",
					"Watch out for its hooves!",
					"NEIGHHHH",
					"You should probably stay away."
			});
			try {
				new Sound("assets/sounds/HorseNoise9.wav").play();
				
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			p.getHealth().updateHealth(-5);
		} else {
			state.displayDialogue(new String[] {"Looks like this horse has decided "+
						"to follow you around.",
						"You try to shoo it away but it won't budge",
						"Guess you have a friend."});
			try {
				new Sound("assets/sounds/HorseNoise1.wav").play();
				
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return this;
	}

	@Override
	public void writeAttributes(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Types getType() {
		return GameObject.Types.HORSE;
	}
	
	@Override
	protected void arriveEvent(){
		if (stampede == true) {
			m_ai = AIState.WAIT;
			room.removeObject(this.getName());
			room.removeEnemy(this.getName());
		} 
	}

}
