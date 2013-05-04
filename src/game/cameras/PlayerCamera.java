package game.cameras;

import org.newdawn.slick.GameContainer;

import game.player.Player;
public class PlayerCamera extends Camera {
	private Player m_player;
	private int m_halfWidth;
	private int m_halfHeight;
	private int m_shakeLength = 0, m_shakeX=0;
	private long m_initialTime = 0;
	private boolean m_shake;
	public PlayerCamera(GameContainer container, Player p){
		m_halfWidth = container.getWidth()/2-32;
		m_halfHeight = container.getHeight()/2-32;
		m_player=p;
	}
	@Override
	public int[] getOffset() {
		// TODO Auto-generated method stub
		m_shakeX = 0;
		//int shakeY = 0;
		if(m_shake){
			if(System.currentTimeMillis()-m_initialTime>m_shakeLength){
				m_shake = false;
			}else{
				m_shakeX = (int) (System.currentTimeMillis())%100/10;
			}
		}
		int offsetX = ((int)m_player.getX())-m_halfWidth +m_shakeX;
		int offsetY = ((int)m_player.getY())-m_halfHeight;
		return new int[] {offsetX, offsetY};
	}

	@Override
	public int[] getPlayerOffset() {
		// TODO Auto-generated method stub
		return new int[]{m_halfWidth + m_shakeX, m_halfHeight};
	}
	public void shake(int shakeL){
		m_shakeLength = shakeL;
		m_initialTime = System.currentTimeMillis();
		m_shake = true;
	}
}
