package game.interactables;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.net.URL;

import game.GameObject;
import game.gameplayStates.GamePlayState;
import game.player.Player;

import javax.swing.JFrame;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.w3c.dom.Node;

public class Chest extends GameObject implements Interactable{
	private Image m_open, m_closed;
	private boolean m_isOpen;
	private ChestPopup m_chestPopup;
	private Runnable m_thread;
	
	private int m_key;
	@Override
	public int getKey() {return m_key;}
	
	private class ChestPopup extends JFrame {
		
		public ChestPopup() {
			this.setFocusableWindowState(false);
			this.setSize(200, 200);
			this.setMinimumSize(new Dimension(200, 200));
			this.setMaximumSize(new Dimension(200, 200));
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.pack();
			this.setVisible(false);
			
			setLayout(new BorderLayout());
//
//            //file you want to play
//            URL mediaURL = //Whatever
//            //create the media player with the media url
//            Player mediaPlayer = Manager.createRealizedPlayer(mediaURL);
//            //get components for video and playback controls
//            Component video = mediaPlayer.getVisualComponent();
//            Component controls = mediaPlayer.getControlPanelComponent();
//            add(video,BorderLayout.CENTER);
//            add(controls,BorderLayout.SOUTH);
		}
		
	}
	
	public Chest(int key, int xLoc, int yLoc) throws SlickException{
		m_x = xLoc;
		m_y = yLoc;
		m_closed = new Image("assets/chestClose.png");
		m_open = new Image("assets/chestOpen.png");
		setSprite(m_closed);
		m_isOpen = false;
		m_key = key;
		
		
		m_thread = new Runnable() {
			public void run() {
				m_chestPopup = new ChestPopup();
			}
		};
		m_thread.run();
	}

	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		if(getSprite().equals(m_closed)){
			m_chestPopup.setVisible(true);
			m_isOpen = true;
			setSprite(m_open);
		}else{
			m_chestPopup.setVisible(false);
			m_isOpen = false;
			setSprite(m_closed);
		}
		return this;
	}
	
	@Override
	public Types getType() {
		return Types.CHEST;
	}
	
	@Override
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		// TODO Auto-generated method stub
		writer.writeStartElement("Interactable");
		writer.writeAttribute("type", Types.CHEST.toString());
		writer.writeAttribute("m_x", String.valueOf(this.m_x));
		writer.writeAttribute("m_y", String.valueOf(this.m_y));
		writer.writeAttribute("m_isOpen", String.valueOf(this.m_isOpen));
		writer.writeEndElement();
	}
	
	/**
	 * Loads a Chest from an XML node.
	 * @param node
	 * @return
	 * @throws SlickException 
	 */
	public static Chest loadFromNode(Node node) throws SlickException {
		int xLoc = (int) Double.parseDouble(node.getAttributes().getNamedItem("m_x").getNodeValue());
		int yLoc = (int) Double.parseDouble(node.getAttributes().getNamedItem("m_y").getNodeValue());
		int[] position = {xLoc, yLoc};
		return new Chest(GamePlayState.positionToKey(position), xLoc, yLoc);
	}
}
