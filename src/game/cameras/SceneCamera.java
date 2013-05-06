package game.cameras;

import game.gameplayStates.GamePlayState;
import game.player.Player;

/**
 * This camera is used to display a scene.
 *
 */
public class SceneCamera extends Camera {

	private double m_xOffset, m_yOffset;
	private float[][] m_path;
	private double m_currentXDest, m_currentYDest;
	private int m_pathStage;
	private Player m_player;
	private GamePlayState m_state;
	private int m_shakeLength = 0, m_shakeX=0;
	private boolean m_shake;
	private long m_initialTime = 0;
	private float m_speed = 1; // default
	
	public SceneCamera(float[][] path, Player p, GamePlayState state) {
		m_path = path;
		m_pathStage = 0;
		m_currentXDest = path[0][0]*64;
		m_currentYDest = path[0][1]*64;
		m_xOffset = m_currentXDest;
		m_yOffset = m_currentYDest;
		m_player = p;
		m_state = state;
	}
	
	/**
	 * speed is determined by s*0.1*delta
	 * @param s
	 */
	public void setSpeed(float s) {
		m_speed = s;
	}
	
	@Override
	public int[] getOffset() {
		m_shakeX = 0;
		//int shakeY = 0;
		if(m_shake){
			if(System.currentTimeMillis()-m_initialTime>m_shakeLength){
				m_shake = false;
			}else{
				m_shakeX = (int) (System.currentTimeMillis())%100/10;
			}
		}
		return new int[] {(int) m_xOffset -268 + m_shakeX, (int) m_yOffset- 268};
	}

	@Override
	public int[] getPlayerOffset() {
		int[] offset = this.getOffset();
		double playerOffsetX = -offset[0] + m_player.getX();
		double playerOffsetY = -offset[1] + m_player.getY();
		//System.out.println(String.format("scene updating %f %f", playerOffsetX, playerOffsetY));
		return new int[] {(int) playerOffsetX, (int) playerOffsetY};
	}

	@Override
	public void shake(int shakeL){
		m_shakeLength = shakeL;
		m_initialTime = System.currentTimeMillis();
		m_shake = true;
	}

	@Override
	public void update(int delta) {
		//System.out.println(String.format("scene updating %f %f", m_xOffset, m_yOffset));
		if(Math.abs(m_xOffset-m_currentXDest)<=5 && Math.abs(m_yOffset-m_currentYDest)<=5) {
			//arrived at destination
			//System.out.println("arrived at point");
			m_pathStage += 1;
			if(m_pathStage >= m_path.length) {
				//This means the scene is done
				//System.out.println("scene done");
				//need to somehow exit the scene...
				m_state.exitScene();
				return;
			}
			else {
				m_currentXDest = m_path[m_pathStage][0]*64;
				m_currentYDest = m_path[m_pathStage][1]*64;
			}
		}
		double x = Math.signum(m_currentXDest - m_xOffset);
		double y = Math.signum(m_currentYDest - m_yOffset);
		m_xOffset += x * delta*0.1*m_speed;
		m_yOffset += y * delta*0.1*m_speed;
		
	}

}
