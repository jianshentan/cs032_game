package game.cameras;

import org.newdawn.slick.GameContainer;

import game.player.Player;
public class PlayerCamera extends Camera {
	private Player m_player;
	private int m_halfWidth;
	private int m_halfHeight;
	public PlayerCamera(GameContainer container, Player p){
		m_halfWidth = container.getWidth()/2-32;
		m_halfHeight = container.getHeight()/2-32;
		m_player=p;
	}
	@Override
	public int[] getOffset() {
		// TODO Auto-generated method stub
		int offsetX = ((int)m_player.getX())-m_halfWidth;
		int offsetY = ((int)m_player.getY())-m_halfHeight;
		return new int[] {offsetX, offsetY};
	}

	@Override
	public int[] getPlayerOffset() {
		// TODO Auto-generated method stub
		return new int[]{m_halfWidth, m_halfHeight};
	}

}
