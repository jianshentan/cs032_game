package game;

import game.gameplayStates.GamePlayState;
import game.player.Player;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Collectable extends GameObject {
	
	public abstract String getItemName();
	public abstract String getItemText();
	//public abstract void writeAttributes(XMLStreamWriter writer) throws XMLStreamException;
	//public static Collectable loadFromNode(Node n);

	/**
	 * 
	 */
	public Collectable() {
		super();
		super.setName("collectable");
	}
	
	/**
	 * Initializes a collectable with a name
	 * @param n
	 */
	public Collectable(String n) {
		super(n);
	}
	
	/**
	 * Triggered on using/equipping the item.
	 * @param p - Player using the item
	 * @param state - GamePlayState the item is triggered in
	 */
	public void onUse(Player p, GamePlayState state) {
		
	}
	/**
	 * Triggered when the item is no longer equipped.
	 * @param p
	 * @param state
	 */
	public void onStopUse(Player p, GamePlayState state) {
		
	}
	/**
	 * Returns true if the item is consumable.
	 * @return
	 */
	public boolean isConsumable() {
		return false;
	}
	/**
	 * Returns a Dialogue that is displayed upon use.
	 * @return
	 */
	public String[] useDialogue() {
		return null;
	}
	/**
	 * If the item is being used, it is updated each step.
	 * @param delta
	 */
	public void update(int delta) {
		
	}
	/**
	 * If the item is in use, this renders any effects it may have.
	 * @param container
	 * @param game
	 * @param g
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		
	}
	
	/**
	 * Returns true if the two objects' names are equal.
	 */
	public boolean equals(Object o) {
		if(o.getClass() == this.getClass()) {
			Collectable c = (Collectable) o;
			return this.getType()==c.getType();
		}
		return false;
	}
}
