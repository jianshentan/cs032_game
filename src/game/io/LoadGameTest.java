package game.io;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.newdawn.slick.SlickException;
import org.xml.sax.SAXException;

public class LoadGameTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new LoadGame().load();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
