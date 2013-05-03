package game.collectables;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import game.Collectable;

public class SmallPlug extends Collectable {

	@Override
	public String getItemName() {
		// TODO Auto-generated method stub
		return "Small Plug";
	}

	@Override
	public String getItemText() {
		// TODO Auto-generated method stub
		return "This looks like it could plug something";
	}

	@Override
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Types getType() {
		// TODO Auto-generated method stub
		return Types.SMALL_PLUG;
	}

}
