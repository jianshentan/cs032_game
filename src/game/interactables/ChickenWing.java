package game.interactables;

import game.Collectable;
import game.GameObject;
import game.GameObject.Types;
import game.gameplayStates.GamePlayState;
import game.player.Player;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class ChickenWing extends Collectable implements Interactable{
	
	public ChickenWing(int xLoc, int yLoc) throws SlickException {
		m_x = xLoc;
		m_y = yLoc;
		this.setKey(GamePlayState.positionToKey(getSquare()));
		setSprite(new Image("assets/chickenWing.png"));
	}
	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		state.removeObject(this.getKey(), (int) m_x/SIZE, (int) this.m_y/SIZE);
		return this;
	}
	
	@Override
	public int[] getSquare() {
		int[] loc = {(int)m_x/SIZE, (int)m_y/SIZE};
		return loc;
	}
	@Override
	public Types getType() {
		return Types.CHICKEN_WING;
	}
	@Override
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub
		writer.writeStartElement("Interactable");
		writer.writeAttribute("type", Types.CHICKEN_WING.toString());
		writer.writeAttribute("m_x", String.valueOf(this.m_x));
		writer.writeAttribute("m_y", String.valueOf(this.m_y));
		writer.writeEndElement();	
	}
	@Override
	public String getItemName() {
		return "Chicken Wing";
	}
	@Override
	public String getItemText() {
		return "This is a chicken wing that looks shockingly like a horse with one leg.";
	}
	@Override
	public boolean isConsumable() {
		return true;
	}
	/**
	 * Using a chicken wing increases health by 20.
	 */
	@Override
	public void onUse(Player p, GamePlayState state) {
		p.getHealth().updateHealth(20);
	}
	@Override
	public String[] useDialogue() {
		String[] dialogue = {"You cook the raw chicken wing with the warmth of your heart. " +
								"You gain 20 health."};
		return dialogue;
	}
	
}
