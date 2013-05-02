package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

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
		this.setLeadTo(destinations[3-StateManager.m_dreamState][0], destinations[3-StateManager.m_dreamState][1]);
		Image [] movementUp = {new Image("assets/Sprite1Back.png"), new Image("assets/Sprite1Back.png")};
        Image [] movementDown = {new Image("assets/Sprite1Front.png"), new Image("assets/Sprite1Front.png")};
        Image [] movementLeft = {new Image("assets/Sprite1Left.png"), new Image("assets/Sprite1Left.png")};
        Image [] movementRight = {new Image("assets/Sprite1Right.png"), new Image("assets/Sprite1Right.png")};
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
