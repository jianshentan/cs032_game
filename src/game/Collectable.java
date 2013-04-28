package game;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public abstract class Collectable extends GameObject {
	private int m_key;
	
	public abstract String getItemName();
	public abstract String getItemText();
	public abstract void writeToXML(XMLStreamWriter writer) throws XMLStreamException;
	//public static Collectable loadFromNode(Node n);
	public int getKey() {return m_key;}
	public void setKey(int key) {
		if(m_key==0)
			m_key = key;
	}
}
