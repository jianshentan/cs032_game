package game.player;

import game.Collectable;
import game.GameObject.Types;
import game.Loadable;
import game.StateManager;
import game.collectables.Pill;

import java.util.ArrayList;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Inventory implements Loadable<Inventory> {
	
	private Font m_font;
	private int BLOCKSIZE = 64;
	private int m_width = 7*BLOCKSIZE;
	private int m_height = 5*BLOCKSIZE;
	private int m_x = 76, m_y = 140;
	private Image m_background;
	private int[] m_textBox = new int[] {m_x + BLOCKSIZE*4 + BLOCKSIZE/2, m_y + BLOCKSIZE/2};
	private int[] m_usingBox = new int[] {m_x + BLOCKSIZE*5, m_y + BLOCKSIZE*3 + BLOCKSIZE/2};
	private Collectable m_using = null;
	public Collectable getCurrItem() {return m_using;}
	
	private Collectable[] m_items;
	private int m_pointer = 0;
	private int m_inputDelta = 0;
	
	private Image m_cursor;
	
	private TrueTypeFont m_itemName;
	private TrueTypeFont m_itemDesc;
	
	//TODO: mini inventory
	private boolean m_mini;
	private Collectable[] m_miniItems;
	
	public Inventory(GameContainer container) throws SlickException {
		// set up box to display text in
		m_font = container.getDefaultFont();
		m_x = 76;
		m_y = 140;
		
		m_background = new Image("assets/inventoryMenu.png");
		
		// set up inventory logic
		m_items = new Collectable[16];
		m_miniItems = new Collectable[8];
		
		// load sprites
		m_cursor = new Image("assets/colors/white.png");
		m_cursor.setAlpha((float)0.3);
		
		java.awt.Font font1 = new java.awt.Font("SansSerif", java.awt.Font.BOLD, 12);
		m_itemName = new TrueTypeFont(font1, false);
			
		java.awt.Font font2 = new java.awt.Font("SansSerif", java.awt.Font.BOLD, 10);
		m_itemDesc = new TrueTypeFont(font2, false);
	}
	
	/**
	 * Toggles the mini inventory
	 * @param b
	 */
	public void setMini(boolean b) {
		m_mini = b;
	}
	
	public void update(GameContainer container, int delta) {
		Input input = container.getInput();
		m_inputDelta-=delta;	
		if (m_inputDelta<0 && input.isKeyDown(Input.KEY_UP)) {
			if (m_pointer >= 4)
				m_pointer = m_pointer - 4;
			m_inputDelta = 200;
        }
        else if (m_inputDelta<0 && input.isKeyDown(Input.KEY_DOWN)) {
        	if (m_pointer < 12)
        		m_pointer = m_pointer + 4;
        	m_inputDelta = 200;
        }
        else if (m_inputDelta<0 && input.isKeyDown(Input.KEY_LEFT)) {
        	if (m_pointer % 4 != 0)
        		m_pointer--;
        	m_inputDelta = 200;
        }
        else if (m_inputDelta<0 && input.isKeyDown(Input.KEY_RIGHT)) {
        	if (m_pointer % 4 != 3)
        		m_pointer++;
        	m_inputDelta = 200;
        }
        if(m_inputDelta<0&&input.isKeyDown(Input.KEY_SPACE)) {
        	if (m_mini) {
        		if (m_miniItems[m_pointer] != null) {
        			m_using = m_miniItems[m_pointer];
        		} else {
        			m_using = null;
        		}
        	}
        	else {
	        	if (m_items[m_pointer] != null) {
	        		m_using = m_items[m_pointer];
	        	} else {
	        		m_using = null;
	        	}
        	}
        	m_inputDelta = 200;
        }	
        if (m_inputDelta<0&&input.isKeyDown(Input.KEY_5)) {
        	int SIZE = 64;
        	
        	m_miniItems[0] = new Pill("alprazolam", "assets/pills/p01.png", 18*SIZE, 17*SIZE);
        	m_miniItems[1] = new Pill("citalopram", "assets/pills/p02.png", 15*SIZE, 9*SIZE);
        	m_miniItems[2] = new Pill("sertraline", "assets/pills/p03.png", 18*SIZE, 5*SIZE);
        	m_miniItems[3] = new Pill("lorazepam", "assets/pills/p04.png", 16*SIZE, 11*SIZE);
        	m_miniItems[4] = new Pill("fluoxetine HCL", "assets/pills/p05.png", 9*SIZE, 12*SIZE);
        	m_miniItems[5] = new Pill("escitalopram", "assets/pills/p06.png", 2*SIZE, 18*SIZE);
        	m_miniItems[6] = new Pill("trazodone HCL", "assets/pills/p07.png", 1*SIZE, 12*SIZE);
        	m_miniItems[7] = new Pill("duloxetine", "assets/pills/p08.png", 4*SIZE, 5*SIZE);
        	
        }
	}
	
	public void render(Graphics g) throws SlickException{
		if(m_mini) {
			//do something...
			//have items be 230x30
			//display names below
			//first, draw gray boxes for items
			//then, draw items - pointer highlighted by yellow box
			g.setColor(Color.gray);
			g.fillRect(5, 5, 160, 160);
			int bs = 30;
			g.setColor(Color.black);
			for(int i = 0; i<8; i++) {
				g.drawRect(10 + (i%4)*(5+bs), 10+(10+bs)*(i/4), bs, bs);
			}
			g.setColor(Color.yellow);
			int start = (int) (8*Math.floor(m_pointer/8));
			for(int i = 0; i<8; i++) {
				int j = (int) (start + i) % m_miniItems.length;
				if(j==m_pointer) {
					g.drawRect(10 + (i%4)*(5+bs), 10 + (10+bs)*(i/4), bs, bs);
				}
				Collectable c = m_miniItems[j];
				if(c!=null) {
					Image img = c.getImage();
					g.drawImage(img, 10 + (i%4)*(5+bs), 10 + (10+bs)*(i/4), 
							10+ bs + (5+bs)*(i%4), 10+bs+(10+bs)*(i/4), 
							0, 0, img.getWidth(), img.getHeight());
				}
			}
			if(m_pointer < m_miniItems.length && m_miniItems[m_pointer]!=null) {
				g.drawString(m_miniItems[m_pointer].getItemName(), 10, 90);
			}
			if(m_using!=null) {
				g.drawString("Using: ", 10, 120);
				Image img = m_using.getImage();
				g.setColor(Color.black);
				g.drawRect(65, 120, bs, bs);
				g.drawImage(img, 65, 120, 65+bs, 120+bs, 
						0, 0, img.getWidth(), img.getHeight());
			}
			return;
		}
		else {
			m_background.draw(m_x, m_y);
			// draw items
			for (int i=0; i<m_items.length; i++) 
				if (m_items[i] != null)
					m_items[i].getSprite().draw(BLOCKSIZE*(i%4) + 108,
											 BLOCKSIZE*((int)Math.floor(i/4)) + 172);
			// draw item text
			if (m_items[m_pointer] != null) {
				String itemName = m_items[m_pointer].getItemName();
				g.setColor(Color.white);
				g.setFont(m_itemName);
				g.drawString(itemName, 10 + m_textBox[0], m_textBox[1]);
				ArrayList<String> itemText = wrap(m_items[m_pointer].getItemText(), BLOCKSIZE*2 + 30);
				g.setFont(m_itemDesc);
				g.setColor(Color.white);
				for (int i=0; i<itemText.size(); i++)
					g.drawString(itemText.get(i), 
								 10 + m_textBox[0],
								 m_textBox[1] + (i+1)*m_font.getLineHeight());
			}
			// draw cursor
			m_cursor.draw(BLOCKSIZE*(m_pointer%4) + 108, 
						  BLOCKSIZE*((int)Math.floor(m_pointer/4)) + 172);
			
			// draw using item
			if (m_using != null)
				m_using.getSprite().draw(m_usingBox[0], m_usingBox[1]);
		}
	}
	
	public void addItem(Collectable item) {
		Collectable[] items = m_items;
		if (item instanceof Pill) 
			items = m_miniItems;
		
		for (int i=0; i<items.length; i++) {
			if (items[i] == null) {
				items[i] = item;
				break;
			}
		}
	}
	
	/**
	 * Removes the first item of a given type.
	 * @param item
	 */
	public void removeItem(Types item) {
		if(m_using!=null && m_using.getType()==item)
			m_using = null;
		
		Collectable[] items = m_items;
		if (m_mini) 
			items = m_miniItems;
		
		for(int i = 0; i<items.length; i++) {
			if(items[i]!=null){
				if(items[i].getType()==item) {
					items[i] = null;
					break;
				}
			}
		}
	}
	
	/**
	 * Removes the first item with a given name.
	 * @param name
	 */
	public void removeItem(String name) {
		if(m_using!=null && m_using.getName().equals(name)) {
			m_using = null;
		}
		
		Collectable[] items = m_items;
		if (m_mini)
			items = m_miniItems;
		
		for(int i = 0; i<items.length; i++) {
			if(items[i]!=null && items[i].getName().equals(name)) {
				items[i] = null;
				break;
			}
		}
	}
	
	/**
	 * Returns true if the inventory contains an item of the given type.
	 * @param item
	 * @return
	 */
	public boolean contains(Types item){
		Collectable[] items = m_items;
		if (m_mini)
			items = m_miniItems;
		
		for(int i = 0; i< items.length; i++) {
			if(items[i]!=null){
				if(items[i].getType()==item){
					return true;
				}
			}
		}
		return false;
	}

	private ArrayList<String> wrap(String text, int width) {
        ArrayList<String> list = new ArrayList<String>();
        String str = text;
        String line = "";
        
        //we will go through adding characters, once we hit the max width
        //we will either split the line at the last space OR split the line
        //at the given char if no last space exists
        
        //while text is not empty
        int i = 0;
        int lastSpace = -1;
        while (i<str.length()) {
            char c = str.charAt(i);
            if (Character.isWhitespace(c))
                lastSpace = i;
            
            //time to wrap 
            if (c=='\n' || m_font.getWidth(line + c) > width) {
                //if we've hit a space recently, use that
                int split = lastSpace!=-1 ? lastSpace : i;
                int splitTrimmed = split;
                
                //if we are splitting by space, trim it off for the start of the next line
                if (lastSpace!=-1 && split<str.length()-1) 
                   splitTrimmed++;
                
                line = str.substring(0, split);
                str = str.substring(splitTrimmed);
                
                //add the line and reset our values
                list.add(line);
                line = "";
                i = 0;
                lastSpace = -1;
            } 
            else {
                line += c;
                i++;
            }
        }
        // leftovers
        if (str.length()!=0)
            list.add(str);
        return list;
    }
	
	public void writeToXML(XMLStreamWriter writer) throws XMLStreamException {
		writer.writeStartElement("Inventory");
		writer.writeAttribute("m_x", String.valueOf(m_x));
		writer.writeAttribute("m_y", String.valueOf(m_y));
		
		for(Collectable c : this.m_items) {
			if(c!=null)
				c.writeToXML(writer);
		}
		
		writer.writeEndElement();
	}
	
	public Inventory loadFromXML(Node n, GameContainer c, StateManager g) throws SlickException {
		NodeList children = n.getChildNodes();
		for(int i = 0; i<children.getLength(); i++) {
			Node child = children.item(i);
			if(child.getNodeName().equals("Interactable")) {
				Node c2 = child;
				
				
				
			}
		}
		return this;
	}
}
