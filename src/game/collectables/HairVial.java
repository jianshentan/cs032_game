package game.collectables;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.Collectable;
import game.GameObject.Types;
import game.gameplayStates.GamePlayState;
import game.interactables.Interactable;
import game.player.Player;

public class HairVial extends Collectable implements Interactable{
	
	public HairVial(int x, int y) throws SlickException{
		super();
		this.setName("hairVial");
		this.setX(x);
		this.setY(y);
		setSprite(new Image("assets/hairVial.png"));
	}
	@Override
	public String getItemName() {
		// TODO Auto-generated method stub
		return "Hair Vial";
	}

	@Override
	public String getItemText() {
		// TODO Auto-generated method stub
		return "Looks pretty intense.. might make me lose more hair than I'd hope to.";
	}

	@Override
	public void writeAttributes(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Types getType() {
		// TODO Auto-generated method stub
		return Types.HAIR_VIAL;
	}
	
	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		state.removeObject(this.getName());
		return this;
	}

}
