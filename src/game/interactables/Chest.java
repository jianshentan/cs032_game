package game.interactables;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

import game.GameObject;
import game.collectables.BigPlug;
import game.gameplayStates.GamePlayState;
import game.player.Player;
import game.popup.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.w3c.dom.Node;

public class Chest extends GameObject implements Interactable{
	private Image m_open, m_closed;
	private boolean m_isOpen;
	private MainFrame m_popup;
	private Runnable m_thread;

	
	public Chest(String name, int xLoc, int yLoc) throws SlickException{
		super(name);
		m_x = xLoc;
		m_y = yLoc;
		m_closed = new Image("assets/chestClose.png");
		m_open = new Image("assets/chestOpen.png");
		setSprite(m_closed);
		m_isOpen = false;
		
		
		m_thread = new Runnable() {
			public void run() {
				try {
					m_popup = new MainFrame(200, 200, 256, 256, "assets/popup_horse.png");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		m_thread.run();
	}

	@Override
	public Interactable fireAction(GamePlayState state, Player p){
		if(getSprite().equals(m_closed)){
			m_popup.setVisible(true);
			setSprite(m_open);
		}else{
			m_popup.setVisible(false);
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
		String name = node.getAttributes().getNamedItem("name").getNodeValue();
		int[] position = {xLoc, yLoc};
		return new Chest(name, xLoc, yLoc);
	}
}
