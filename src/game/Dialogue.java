package game;

import game.gameplayStates.GamePlayState;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.fills.GradientFill;

/**
 * Displays text in blocks. If player can make decisions in the dialogue, the dialogue 
 * has to be in the last textblock
 */
public class Dialogue {
	
	/*
	 * List<List<String>> :
	 * -- the outer list represents the each textblock. when this is iterated through, the 
	 *    dialogue should exit
	 * -- the inside list represents each line and handles wrapping the text
	 */
	private List<ArrayList<String>> m_text = new ArrayList<ArrayList<String>>();
	private int m_numTextBlocks;
	private int m_currTextBlock = 0;
	private int m_numLines;
	private int m_currLine = 0;
	
	// selection funtionality
	private ArrayList<String> m_prompt = new ArrayList<String>();
	private ArrayList<String> m_selectionOptions = new ArrayList<String>(); 
	private int m_selection = -1;
	private boolean m_selectionMode = false; // becomes true when you are in a textblock with selection

	private float m_x;
    private float m_y;
	private float m_width;
	private float m_height;
	
	private Rectangle m_rectangle;
	private ShapeFill m_shapeFill;
	private Font m_font;
	
	private int m_inputDelta = 0;
	private GamePlayState m_game;
	
	/**
	 * Dialogue
	 * @param game
	 * @param container
	 * @param textArray An array of string where each element in the array is a separate textblock
	 * @param selectionArray An array of string where the first element in the array is a prompt, 
	 * 		 	and every subsequent element is a selectable option
	 * 			ie. ["do you like ice cream", "yes", "no"]
	 * 			if null, no selection feature
	 */
	public Dialogue(GamePlayState game, 
					GameContainer container, 
					String[] textArray, 
					String[] selectionArray) {
		// set up box to display text in
		m_game = game;
		m_font = container.getDefaultFont();
		m_width = container.getWidth(); // pause menu width is same as the screen width
		m_height = container.getHeight()/4; // pause menu height is half the screen height
		m_x = 0;
		m_y = 3 * m_height;
		
		m_rectangle = new Rectangle(m_x, m_y, m_width, m_height);
		m_shapeFill = new GradientFill(m_x, m_y, Color.white, // top left corner
									   m_x + m_width, m_y + m_height, Color.white); // bottom right
		
		// set up text to display
		for (String text : textArray) {
			ArrayList<String> lines = wrap(text, (int)m_width-50); 
			m_text.add(lines);
		}
		m_numTextBlocks = m_text.size(); // should be > 0
		m_currTextBlock = 0;
		
		// set up selection
		if (selectionArray != null) {
			m_prompt = wrap(selectionArray[0], (int)m_width-50);
			for (int i=1; i<selectionArray.length; i++)
				m_selectionOptions.add(selectionArray[i]);
			m_selection = 0; // correlates to index of m_selectionOptions
		}
	}
	
	public void render(Graphics g) throws SlickException {
		g.fill(m_rectangle, m_shapeFill);
		g.setColor(Color.black);
		
		// render text
		if (!m_selectionMode) {
			if (m_currTextBlock < m_text.size()) {
				List<String> text = m_text.get(m_currTextBlock);
				for (int i=0; i<m_currLine; i++) {
					if (text.size() > i) {
						String line = text.get(i);
						g.drawString(line, m_x + 25, 25 + m_y + i*m_font.getLineHeight());
					}
				}
			}
		}
		// render selections
		else {
			int i;
			for (i=0; i<m_prompt.size(); i++) {
				String line = m_prompt.get(i);
				g.drawString(line, m_x + 25, 25 + m_y + i*m_font.getLineHeight());
			}
			for (int j=0; j<m_selectionOptions.size(); j++) {
				g.drawString(m_selectionOptions.get(j), 50 + m_x, 
						35 + m_y + i*m_font.getLineHeight() + j*m_font.getLineHeight());
				if (j == m_selection) 
					g.drawString(">", 40 + m_x,
							35 + m_y + i*m_font.getLineHeight() + j*m_font.getLineHeight());
			}
		}
	}
	
	public void update(GameContainer container, StateBasedGame stateManager, int delta) {
		if (m_currTextBlock <= m_numTextBlocks-1) {
			List<String> text = m_text.get(m_currTextBlock);
			m_numLines = text.size();
		}
		
		// handles input
		Input input = container.getInput();
		m_inputDelta-=delta;
        if (m_inputDelta<0 && input.isKeyDown(Input.KEY_UP) && m_selectionMode) {
        	if (m_selection > 0)
        		m_selection--;
        	m_inputDelta=200;
        }
        if (m_inputDelta<0 && input.isKeyDown(Input.KEY_DOWN) && m_selectionMode) {
        	if (m_selection < m_selectionOptions.size()-1)
        		m_selection++;
        	m_inputDelta=200;
        }
        if(m_inputDelta<0 && input.isKeyDown(Input.KEY_SPACE) && !m_selectionMode){
        	//System.out.println("updating");
        	if (m_currLine >= m_numLines) {
        		m_currLine = 0;
        		// got to next text block
        		if (m_currTextBlock >= m_numTextBlocks-1) {
        			m_currTextBlock = 0;
        			if (m_selection != -1) { // move on to dialogue options
        				m_selectionMode = true;
        			}
        			else { // exit dialogue
	        			m_game.setDialogueState(false); // TODO: + delete dialogue instance?
	        			m_game.exitScene();
        			}
        		}
        		else
        			m_currTextBlock++;
        	}
        	else
        		m_currLine++;
        	m_inputDelta=200;
        }			
        // if space in selection mode
        if(m_inputDelta<0 && input.isKeyDown(Input.KEY_SPACE) && m_selectionMode){
        	m_game.setDialogueState(false);
        	m_game.exitScene();
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
}
