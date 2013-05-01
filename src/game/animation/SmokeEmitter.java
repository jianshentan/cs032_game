package game.animation;

import org.newdawn.slick.particles.ConfigurableEmitter;

/**
 * Particle emitter that emits smoke particles.
 *
 */
public class SmokeEmitter extends ConfigurableEmitter {
	
	

	public SmokeEmitter(String name) {
		super(name);
		this.initialLife.setMin(400);
		this.initialLife.setMax(1000);
		this.spawnCount.setMin(5);
		this.spawnCount.setMax(20);
	}
	
	

}
