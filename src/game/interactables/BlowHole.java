package game.interactables;

import game.GameObject;
import game.GameObject.Types;
import game.gameplayStates.GamePlayState;
import game.player.Player;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class BlowHole extends GameObject implements Interactable {
	
	public BlowHole(int xLoc, int yLoc){
		m_x = xLoc;
		m_y = yLoc;
	}
	@Override
	public int getKey() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		// TODO Auto-generated method stub
		if(p.getInventory().contains(Types.SMALL_PLUG)){
			
		}else if(p.getInventory().contains(Types.BIG_PLUG)){
			
		}else if(p.getInventory().contains(Types.CORRECT_PLUG)){
			
		}else{
			
		}
		return null;
	}

	@Override
	public int[] getSquare() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Types getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
