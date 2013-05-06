package game.cameras;

import game.player.Player;

import org.newdawn.slick.GameContainer;

/**
 * Cameras allow a certain portion of a map to be shown.
 *
 */
public abstract class Camera {
	
	public Camera(){
		
	}
	/**
	 * This returns the offset of the camera's center in terms of the map.
	 * @return
	 */
	public abstract int[] getOffset();
	/**
	 * This returns the offset of the player from the camera center.
	 * @return
	 */
	public abstract int[] getPlayerOffset();
	public abstract void shake(int shakeL);
	public abstract void update(int delta);
	
	public abstract void refreshCamera(GameContainer container, Player p);
}
