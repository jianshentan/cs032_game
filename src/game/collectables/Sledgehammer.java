package game.collectables;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.Collectable;
import game.GameObject;
import game.gameplayStates.GamePlayState;
import game.interactables.Interactable;
import game.player.Player;

public class Sledgehammer extends Collectable implements Interactable {
	
	public Sledgehammer() {
		super.setName("Sledgehammer");
		try {
			super.setSprite(new Image("assets/sledgehammer.png"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Interactable fireAction(GamePlayState state, Player p) {

		return this;
	}

	@Override
	public String getItemName() {
		// TODO Auto-generated method stub
		return "Sledgehammer";
	}

	@Override
	public String getItemText() {
		// TODO Auto-generated method stub
		return "This sledgehammer might be useful for pounding things. Dangerous things.";
	}

	@Override
	public Types getType() {
		// TODO Auto-generated method stub
		return GameObject.Types.SLEDGEHAMMER;
	}

}
