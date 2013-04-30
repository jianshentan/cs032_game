package game.io;

import game.StateManager;

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
			StateManager.getInstance().setLoader(new LoadGame());
			StateManager.getInstance().run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
