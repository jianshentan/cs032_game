package game.collectables;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import game.Collectable;

public class BigPlug extends Collectable {
	
	public BigPlug(){
		
	}

	@Override
	public String getItemName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getItemText() {
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
		return Types.BIG_PLUG;
	}
}
