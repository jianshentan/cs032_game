package game;

import game.gameplayStates.GamePlayState;
import game.player.Player;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Collectable extends GameObject {
	private int m_key;
	
	public abstract String getItemName();
	public abstract String getItemText();
	public abstract void writeToXML(XMLStreamWriter writer) throws XMLStreamException;
	//public static Collectable loadFromNode(Node n);
	public int getKey() {return m_key;}
	public void setKey(int key) {
		if(m_key==0)
			m_key = key;
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
	public Dialogue useDialogue() {
		return null;
	}
	
	public void update(int delta) {
		
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		
	}
}
