package game.interactables;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.AIState;
import game.Enemy;
import game.GameObject;
import game.gameplayStates.GamePlayState;
import game.player.Player;

/**
 * Animals inherit patrol/movement behaviors from Enemy, but
 * they only have one sprite.
 *
 */
public class Animal extends Enemy implements Interactable {
	
	private int m_key;
	
	public Animal(int key, String imagePath, GamePlayState room, Player player, float x, float y,
			int xTarget, int yTarget) throws SlickException {
		super(room, player, x, y);
		this.setSprite(new Image(imagePath));
		this.m_key = key;
		this.setLeadTo(xTarget, yTarget);
		this.m_ai = AIState.LEAD;
		this.m_sprite = new Animation(new Image[] {new Image(imagePath)}, 1000, false);
	}

	@Override
	public int getKey() {
		return m_key;
	}

	@Override
	protected void updateSprite() {
		
	}
	
	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		// TODO Auto-generated method stub
		state.displayDialogue(new String[] {"Fortunately, you are well trained " +
				"in cat tranquilization techniques.",
				"However, you have no space in your pockets to put a cat."
		});
		return this;
	}

	@Override
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub

	}

	@Override
	public Types getType() {
		return GameObject.Types.ANIMAL;
	}

}
