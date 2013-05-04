package game.collectables;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.Collectable;

public class BigPlug extends Collectable {
	
	public BigPlug() throws SlickException{
		setSprite(new Image("assets/BigPlug.png"));
	}

	@Override
	public String getItemName() {
		// TODO Auto-generated method stub
		return "Big Plug";
	}

	@Override
	public String getItemText() {
		// TODO Auto-generated method stub
		return "Wow, this is uncomfortably big";
	}

	@Override
	public void writeAttributes(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Types getType() {
		// TODO Auto-generated method stub
		return Types.BIG_PLUG;
	}
}
