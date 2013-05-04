package game.interactables;

import game.GameObject;
import game.GameObject.Types;
import game.gameplayStates.GamePlayState;
import game.player.Player;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class BlowHole extends GameObject implements Interactable {

	public BlowHole(String name, int xLoc, int yLoc) throws SlickException{
		super(name);
		m_x = xLoc;
		m_y = yLoc;
		setSprite(new Image("assets/colors/clear.png")); 
	}

	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		// TODO Auto-generated method stub
		Types equipped = p.getUsing().getType();
		switch(equipped){
			case SMALL_PLUG:{
				p.getInventory().removeItem(Types.SMALL_PLUG);
				String[] di = {"You put the plug into the hole, it falls in and is swallowed up by darkness", "It probably wouldn't have been much fun anyway"};
				state.displayDialogue(di);
				break;
			}case BIG_PLUG:{
				p.getInventory().removeItem(Types.BIG_PLUG);
				String[] di = {"You try to jam the plug into the hole, but it just won't fit", "Maybe you want to hold onto this for later"};
				state.displayDialogue(di);
				break;
			}case CORRECT_PLUG:{
				p.getInventory().removeItem(Types.CORRECT_PLUG);
				//VICTORY ACTION
				break;
			}
			default:{
				if(p.getInventory().contains(Types.SMALL_PLUG)||p.getInventory().contains(Types.BIG_PLUG)||p.getInventory().contains(Types.CORRECT_PLUG)){
					String[] di = {"You've got the tools. Time to fill this &!#@% up"};
					state.displayDialogue(di);
				}else{
					String[] di = {"You're not sure what this hole is, but you are overcome with a deep desire to fill it"};
					state.displayDialogue(di);
				}
			}
		}
		return this;
	}


	@Override
	public void writeAttributes(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Types getType() {
		// TODO Auto-generated method stub
		return Types.BLOW_HOLE;
	}

}
