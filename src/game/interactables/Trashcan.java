package game.interactables;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.GameObject;
import game.collectables.BigPlug;
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
		if(getSprite().equals(m_closed)){
			state.displayDialogue(new String[]{"Everything in here seems pretty dirty", "Oh! Except that pink thing", "Better take it"});
			try {
				p.addToInventory(new BigPlug());
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setSprite(m_open);
		}else{
			state.displayDialogue(new String[]{"Nothing left of interest"});
		}
		return this;
	}

	@Override
	public Types getType() {
		// TODO Auto-generated method stub
		return Types.TRASHCAN;
	}

}
