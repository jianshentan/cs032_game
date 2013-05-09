package game.interactables;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.Collectable;
import game.GameObject.Types;
import game.gameplayStates.GamePlayState;
import game.player.Player;

public class Banana extends Collectable implements Interactable{

	public Banana(String name, int xLoc, int yLoc) throws SlickException {
		super(name);
		m_x = xLoc;
		m_y = yLoc;
		setSprite(new Image("assets/gameObjects/banana.png"));
	}
	
	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		state.removeObject(this.getName(), (int) m_x/SIZE, (int) this.m_y/SIZE);
		return this;
	}

	@Override
	public String getItemName() {
		return "Banana";
	}
	
	@Override
	public boolean isConsumable() {
		return true;
	}

	@Override
	public String getItemText() {
		return "I've got this feeling, so appealing, for us to get together and sing. Sing!";
	}

	@Override
	public Types getType() {
		return Types.BANANA;
	}

}
