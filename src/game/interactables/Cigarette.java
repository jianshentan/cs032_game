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
import org.newdawn.slick.particles.effects.FireEmitter;

import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.particles.effects.FireEmitter;
import org.newdawn.slick.state.StateBasedGame;

public class Cigarette extends Collectable implements Interactable {
	
	private ParticleEmitter m_emitter;
	private boolean m_emitting;
	protected ParticleSystem m_particleSystem; //particle system
	public ParticleSystem getParticleSystem() {
		return this.m_particleSystem;
	}
	public void addEmitter(ParticleEmitter emitter) {
		if(this.m_particleSystem!=null)
		this.m_particleSystem.addEmitter(emitter);
	}
	
	public Cigarette(String name, int xLoc, int yLoc) throws SlickException {
		super(name);
		m_x = xLoc;
		m_y = yLoc;
		setSprite(new Image("assets/cigarette.png"));
		m_emitter = new FireEmitter(300,300,20);
		m_particleSystem = new ParticleSystem("assets/particles/smoke_1.png");

	}

	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		state.removeObject(this.getName(), (int) m_x/SIZE, (int) this.m_y/SIZE);
		return this;
	}

	@Override
	public void writeAttributes(XMLStreamWriter writer) throws XMLStreamException {

	}

	@Override
	public String getItemName() {
		return "Cigarette";
	}

	@Override
	public String getItemText() {
		return "A smokey little thing... It might one day save your life";
	}

	@Override
	public int[] getSquare() {
		int[] loc = {(int)m_x/SIZE, (int)m_y/SIZE};
		return loc;
	}

	@Override
	public Types getType() {
		return Types.CIGARETTE;
	}
	
	@Override
	public void onUse(Player p, GamePlayState state) {
		m_emitter = new FireEmitter(300,300,20);
		addEmitter(m_emitter);
	}
	
	@Override
	public void onStopUse(Player p, GamePlayState state) {
		m_particleSystem.removeAllEmitters();
	}


	@Override
	public void render(GameContainer container, StateBasedGame stateManager, Graphics g) {
		m_particleSystem.render();
	}

	@Override
	public void update(int delta) {
		m_particleSystem.update(delta);
	}

}
