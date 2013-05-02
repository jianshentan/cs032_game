package game.player;

import game.Collectable;
import game.Loadable;
import game.StateManager;

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
	
	public Inventory(GameContainer container) throws SlickException {
		// set up box to display text in
		m_font = container.getDefaultFont();
		m_x = 76;
		m_y = 140;
		
		m_background = new Image("assets/inventoryMenu.png");
		
		// set up inventory logic
		m_items = new Collectable[16];
		
		// load sprites
		m_cursor = new Image("assets/redSquare.png");
		m_cursor.setAlpha((float)0.3);
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
        	if (m_items[m_pointer] != null) {
        		m_using = m_items[m_pointer];
        		m_using.use();
        	}
        	m_inputDelta = 200;
        }	
	}
	
	public void render(Graphics g) throws SlickException{
		m_background.draw(m_x, m_y);
		// draw items
		for (int i=0; i<m_items.length; i++) 
			if (m_items[i] != null)
				m_items[i].getSprite().draw(BLOCKSIZE*(i%4) + 108,
										 BLOCKSIZE*((int)Math.floor(i/4)) + 172);
		// draw item text
		if (m_items[m_pointer] != null) {
			String itemName = m_items[m_pointer].getItemName();
			g.setColor(Color.red);
			g.drawString(itemName, m_textBox[0], m_textBox[1]);
			ArrayList<String> itemText = wrap(m_items[m_pointer].getItemText(), BLOCKSIZE*2);
			g.setColor(Color.blue);
			for (int i=0; i<itemText.size(); i++)
				g.drawString(itemText.get(i), 
							 m_textBox[0],
							 m_textBox[1] + (i+1)*m_font.getLineHeight());
		}
		// draw cursor
		m_cursor.draw(BLOCKSIZE*(m_pointer%4) + 108, 
					  BLOCKSIZE*((int)Math.floor(m_pointer/4)) + 172);
		
		// draw using item
		if (m_using != null)
			m_using.getSprite().draw(m_usingBox[0], m_usingBox[1]);

	}
	
	public void addItem(Collectable item) {
		for (int i=0; i<m_items.length; i++) {
			if (m_items[i] == null) {
				m_items[i] = item;
				break;
			}
		}
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
	
	public Collectable getUsing() {
		return this.m_using;
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
