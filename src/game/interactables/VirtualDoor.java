package game.interactables;

import game.GameObject.Types;
import game.gameplayStates.GamePlayState;
import game.gameplayStates.VirtualRealityHome;
import game.player.Player;
import game.GameObject;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;


public class VirtualDoor extends GameObject implements Interactable {
	private boolean m_open = false;
	private boolean m_complete = false;
	public VirtualDoor(String name, int xLoc, int yLoc) throws SlickException{
		super(name);
		m_x = xLoc;
		m_y = yLoc;
		setSprite(new Image("assets/gameObjects/door.png")); 
	}
	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		if(m_open){
			try {
				Sound creak = new Sound("assets/sounds/creakingDoor.wav");
				creak.play();
			} catch (SlickException e) {
				e.printStackTrace();
			}
			if(!m_complete) {
				VirtualRealityHome home = (VirtualRealityHome) state;
				home.stage3Complete();
				m_complete = true;
			}
		}else{
			try{
				Sound hitDoor = new Sound("assets/sounds/HitDoor.wav");
				hitDoor.play();
			}catch(SlickException e){
				e.printStackTrace();	
			}
			state.displayDialogue(new String[] {"The door seems to be locked. You kick it a few times and only manage to bruise your toe. It hurts. Or... you think it hurts. Which might be the same thing. You decide you don't like virtual reality very much"});
		}
		return this;
	}

	@Override
	public int[] getSquare() {
		// TODO Auto-generated method stub
		return new int[]{(int)m_x/SIZE, (int)m_y/SIZE};
	}
	public void setOpen(boolean b){
		m_open = b;
	}
	/**
	 * Returns true if the door is open.
	 */
	public boolean isOpen() {
		return m_open;
	}


	@Override
	public Types getType() {
		// TODO Auto-generated method stub
		return Types.VIRTUAL_DOOR;
	}

}
