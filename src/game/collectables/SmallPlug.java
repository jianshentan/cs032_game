package game.collectables;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.Collectable;
import game.gameplayStates.GamePlayState;
import game.interactables.Interactable;
import game.player.Player;

public class SmallPlug extends Collectable implements Interactable {
	
	public SmallPlug(String name, int x, int y) throws SlickException{
		super(name);
		this.setX(x);
		this.setY(y);
		setSprite(new Image("assets/SmallPlug.png"));
	}
	@Override
	public String getItemName() {
		// TODO Auto-generated method stub
		return "Small Plug";
	}

	@Override
	public String getItemText() {
		// TODO Auto-generated method stub
		return "This looks like it could plug something";
	}

	@Override
	public void writeAttributes(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Types getType() {
		// TODO Auto-generated method stub
		return Types.SMALL_PLUG;
	}
	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		state.removeObject(this.getName());
		return this;
	}

}
