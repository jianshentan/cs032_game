package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import game.gameplayStates.GamePlayState;
import game.player.Player;

public class Spectre extends Enemy {
	
	public Spectre(GamePlayState state, Player player, float x, float y, int[][] destinations) throws SlickException{
		super(state, player, x, y);
		//set ai
		m_ai = AIState.LEAD;
		if(destinations.length!=4){
			System.out.println("ERROR: spectre has wrong # of destinations");
		}
		//set sprites
		SpriteSheet spectreSprite = new SpriteSheet("assets/characters/humanoid.png", 64, 64);
		this.setLeadTo(destinations[3-StateManager.m_dreamState][0], destinations[3-StateManager.m_dreamState][1]);
		Image [] movementUp = {spectreSprite.getSprite(1,0), spectreSprite.getSprite(1,0)};
        Image [] movementDown = {spectreSprite.getSprite(0,0), spectreSprite.getSprite(0,0)};
        Image [] movementLeft = {spectreSprite.getSprite(3,0), spectreSprite.getSprite(3,0)};
        Image [] movementRight = {spectreSprite.getSprite(2,0), spectreSprite.getSprite(2,0)};
        int [] duration = {300, 300}; 
        
        //turn sprites into animations
        m_up = new Animation(movementUp, duration, false);
        m_down = new Animation(movementDown, duration, false);
        m_left = new Animation(movementLeft, duration, false);
        m_right = new Animation(movementRight, duration, false);	
        
        m_sprite = m_right;
	}
	public void arriveEvent(){
		System.out.println("ARRIVE OVERRIDDEN");
		m_ai = AIState.WAIT;
		
	}
}
