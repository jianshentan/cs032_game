package game.interactables;

import org.newdawn.slick.SlickException;


import game.gameplayStates.GamePlayState;
import game.gameplayStates.VirtualRealityHome;
import game.player.Player;

public class VirtualTrash extends Trashcan {
	private boolean m_deleted = false;
	public VirtualTrash(String name, int xLoc, int yLoc) throws SlickException {
		super(name, xLoc, yLoc);
	}

	public void firstAction(GamePlayState state, Player p){
		subsequentAction(state, p);
	}
	public void subsequentAction(GamePlayState state, Player p){
		if(p.getUsing().getType()==(Types.REALITY_EXECUTABLE)){
			m_deleted = true;
			p.getInventory().removeItem(Types.REALITY_EXECUTABLE);
			VirtualRealityHome home = (VirtualRealityHome) state;
			home.finish();
		}else{
			if(m_deleted){
				state.displayDialogue(new String[]{"The paper has disappeared, you better get out of here. This place is about to blow!"});
			}else{
				state.displayDialogue(new String[]{"It's empty. You're a lot cleaner in the virtual world"});
			}
		}
	}
	public Types getType(){
		return Types.VIRTUAL_TRASHCAN;
	}
}
