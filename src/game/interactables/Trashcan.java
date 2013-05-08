package game.interactables;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.GameObject;
import game.collectables.CorrectPlug;
import game.gameplayStates.GamePlayState;
import game.player.Player;
import game.popup.MainFrame;

public class Trashcan extends GameObject implements Interactable{
	private Image m_open, m_closed;
	
	public Trashcan(String name, int xLoc, int yLoc) throws SlickException{
		super(name);
		m_x = xLoc;
		m_y = yLoc;
		m_closed = new Image("assets/chestClose.png");
		m_open = new Image("assets/chestOpen.png");
		setSprite(m_closed);
	}



	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		// TODO Auto-generated method stub
		System.out.println("interacting with trash can");
		if(getSprite().equals(m_closed)){
			firstAction(state, p);
			setSprite(m_open);
		}else{
			subsequentAction(state, p);
		}
		return this;
	}
	public void firstAction(GamePlayState state, Player p){
		
	}
	public void subsequentAction(GamePlayState state, Player p){
		
	}
	@Override
	public Types getType() {
		// TODO Auto-generated method stub
		return Types.TRASHCAN;
	}

}
