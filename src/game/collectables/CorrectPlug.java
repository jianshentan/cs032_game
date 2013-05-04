package game.collectables;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import game.Collectable;

public class CorrectPlug extends Collectable {

	public CorrectPlug(){
		
	}

	@Override
	public String getItemName() {
		// TODO Auto-generated method stub
		return "Nice-looking plug";
	}

	@Override
	public String getItemText() {
		// TODO Auto-generated method stub
		return "This plug looks just about right";
	}

	@Override
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Types getType() {
		return Types.CORRECT_PLUG;
	}
}