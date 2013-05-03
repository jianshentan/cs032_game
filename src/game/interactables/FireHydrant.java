package game.interactables;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import game.GameObject;
import game.gameplayStates.GamePlayState;
import game.player.Player;

public class FireHydrant extends GameObject implements Interactable {

	
	@Override
	public int getKey() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
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
