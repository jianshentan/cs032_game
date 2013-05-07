package game.interactables;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.Collectable;

public class RealityExecutable extends Collectable {
	
	public RealityExecutable() throws SlickException {
		super();
		this.setName("Reality.exe");
		setSprite(new Image("assets/gameObjects/notepad.png"));	
	}

	@Override
	public String getItemName() {
		// TODO Auto-generated method stub
		return "Reality.exe";
	}

	@Override
	public String getItemText() {
		// TODO Auto-generated method stub
		return "This notepad has over millions of lines of text. It's impossible to parse.";
	}

	@Override
	public Types getType() {
		// TODO Auto-generated method stub
		return Types.REALITY_EXECUTABLE;
	}

}
