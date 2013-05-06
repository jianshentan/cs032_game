package game.collectables;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.Collectable;
import game.GameObject;
import game.gameplayStates.GamePlayState;
import game.interactables.Interactable;
import game.player.Player;

/**
 * Pills are simple collectable items used in the hospital quest.
 *
 */
public class Pill extends Collectable implements Interactable{
	
	private String m_imgPath;
	
	public Pill(String name, String imgPath) {
		super(name);
		m_imgPath = imgPath;
		try {
			this.setSprite(new Image(imgPath));
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public String getItemName() {
		return this.getName();
	}

	@Override
	public String getItemText() {
		return "This is a psychotropic drug of some sort.";
	}

	@Override
	public Types getType() {
		return GameObject.Types.PILL;
	}

	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		state.removeObject(this.getName(), (int) m_x/SIZE, (int) this.m_y/SIZE);
		//TODO: fire some windows?
		return this;
	}
	
	/**
	 * Two pills are equal if their names are equal.
	 */
	public boolean equals(Object o) {
		if(o.getClass()==this.getClass()) {
			Pill p = (Pill) o;
			return p.getName().equals(this.getName());
		}
		return false;
	}

}
