package game.interactables;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.GameObject;
import game.gameplayStates.GamePlayState;
import game.player.Player;

public class TableToHack extends GameObject implements Interactable {
	private int m_stage = 0;

	public TableToHack(String name, int xLoc, int yLoc) 
			throws SlickException, FileNotFoundException, UnsupportedEncodingException {
		super(name);
		m_x = xLoc;
		m_y = yLoc;
		
		// stage 1
		setSprite(new Image("assets/gameObjects/table.png"));
		PrintWriter writer = new PrintWriter("brain.txt", "UTF-8");
		writer.print("HackCode:");
		writer.close();	
	}

	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		if (m_stage == 0) {
			if (queryMetaData().compareTo("") == 0) { // default
				state.displayDialogue(new String[] {"You hope your computer can give you some answers...",
							"\"404\"",
							"You wonder if you can use your computer to hack the virtual machine chairs.",
							"\"Dig deeper in to virtual world. Dig deeper into YOUR virtual world.\"",
							"\"Find the brain that is the clue. Clean our minds and keep it true.\"",
							"Curious curious curious. You ponder deeply at what your computer is trying to tell you.",
							"Finally, a strange message pops up on the screen: \"male donkey + female horse = ?\""});
			}
			else if (queryMetaData().compareTo("") != 0 && 
					 queryMetaData().compareTo("mule") != 0) { // wrong answer but something was written
				state.displayDialogue(new String[] {"Your computer is spazzing out..",
						"Looks like you've accessed the system but didn't get it quite right.."});
			}
			else if (queryMetaData().compareTo("mule") == 0) { // right answer
				m_stage++;
				state.displayDialogue(new String[] {"The screen turns white and you hear a slight buzzing",
						"You wonder to yourself if that even did anything.",
						"More text appears on the screen: \"\""});
			}
		}
		else if (m_stage == 1) {
			
		}
		return null;
	}
	
	public String queryMetaData() {
		String ret = "";
		FileReader fileReader;
		try {
			fileReader = new FileReader("brain.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuilder string = new StringBuilder();
			String line = null;
			while((line = bufferedReader.readLine()) != null) 
				string.append(line+"");
			bufferedReader.close();
			ret = string.toString().substring(9);
			System.out.println("read: " + ret + " | size: " + ret.length());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public Types getType() {
		return Types.TABLE_TO_HACK;
	}

}
