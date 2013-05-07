package game.interactables;

import game.collectables.CorrectPlug;
import game.gameplayStates.GamePlayState;
import game.player.Player;

import org.newdawn.slick.SlickException;

public class OutsideTrashcan extends Trashcan {

	public OutsideTrashcan(String name, int xLoc, int yLoc)
			throws SlickException {
		super(name, xLoc, yLoc);
		
	}
	@Override
	public void firstAction(GamePlayState state, Player p){
		state.displayDialogue(new String[]{"Everything in here seems pretty dirty", "Oh! Except that pink thing", "Better take it"});
		try {
			p.addToInventory(new CorrectPlug());
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void subsequentAction(GamePlayState state, Player p){
		state.displayDialogue(new String[]{"Nothing left of interest"});
	}
	public Types getType(){
		return Types.OUTSIDE_TRASHCAN;
	}

}
