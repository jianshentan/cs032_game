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
	private int m_stage = 2;
	private File m_json;
	public TableToHack(String name, int xLoc, int yLoc) 
			throws SlickException, FileNotFoundException, UnsupportedEncodingException {
		super(name);
		//TODO - start w/ cleanup of existing files
		m_x = xLoc;
		m_y = yLoc;
		
		// stage 1
		this.writeJSON();
		setSprite(new Image("assets/gameObjects/table.png"));
		PrintWriter writer = new PrintWriter("brain.txt", "UTF-8");
		writer.print("HackCode:");
		writer.close();	
	}

	@Override
	public Interactable fireAction(GamePlayState state, Player p) {
		if (m_stage == 0) {
			if (queryAnswer1().compareTo("") == 0) { // default
				state.displayDialogue(new String[] {"You hope your computer can give you some answers...",
							"\"404\"",
							"You wonder if you can use your computer to hack the virtual machine chairs.",
							"\"Dig deeper in to virtual world. Dig deeper into YOUR virtual world.\"",
							"\"Find the brain that is the clue. Clean our minds and keep it true.\"",
							"Curious curious curious. You ponder deeply at what your computer is trying to tell you.",
							"Finally, a strange message pops up on the screen: \"male donkey + female horse = ?\""});
			}
			else if (queryAnswer1().compareTo("") != 0 && 
					 queryAnswer1().compareTo("mule") != 0) { // wrong answer but something was written
				state.displayDialogue(new String[] {"Your computer is spazzing out..",
						"Looks like you've accessed the system but didn't get it quite right.."});
			}
			else if (queryAnswer1().compareTo("mule") == 0) { // right answer
				m_stage++;
				state.displayDialogue(new String[] {"The screen turns white and you hear a slight buzzing",
						"You wonder to yourself if that even did anything.",
						"More text appears on the screen: \"Like a new born pheonix or bathtub foam\"",
						"\"make from nothingness the fibonacci poem\"",
						"\"Starting at nothing to no greater than 10,\"",
						"\"Ignore all spaces and like the horse-friend 'hen'\"",
						"\"And like Davinci, dear fibonacci shares\"",
						"\"A commonality - the answer. Name it with care.\""});
			}
		}
		else if (m_stage == 1) {
			if (queryAnswer2().compareTo("404") == 0) {
				state.displayDialogue(new String[] {"\"Like a new born pheonix or bathtub foam\"",
						"\"make from nothingness the fibonacci poem\"",
						"\"Starting at nothing to no greater than 10,\"",
						"\"Ignore all spaces and like the horse-friend 'hen'\"",
						"\"And like Davinci, dear fibonacci shares\"",
						"\"A commonality - the answer. Name it with care.\""});
			}else if (queryAnswer2().compareTo("112358") == 0) {
				m_stage++;
				state.displayDialogue(new String[] {"The screen turns white again and the buzzing continues",
						"The whole room suddenly starts to shake a litte...",
						".. but just barely enough to notice. You realize that you must be breaking something "+
						"about the virtual reality space with these puzzles",
						"\"\""});
				this.writeJSON();
				state.shakeCamera(2000);	
			}else{
				state.displayDialogue(new String[] {"The computer spits out some text",
						"File leonardo.txt found: password failed. Re-reading poem",
						"\"Like a new born pheonix or bathtub foam\"",
						"\"make from nothingness the fibonacci poem\"",
						"\"Starting at nothing to no greater than 10,\"",
						"\"Ignore all spaces and like the horse-friend 'hen'\"",
						"\"And like Davinci, dear fibonacci shares\"",
						"\"A commonality - the answer. Name it with care.\""});
			}
		}
		else if (m_stage == 2) {
			if (queryAnswer3().compareTo("404")==0){
				state.displayDialogue(new String[] {
						"Looks like another file appeared",
						"VirtalRealityState.JSON",
						"Maybe you should be careful"
				});
			}
		}
		return null;
	}
	
	/**
	 * call when done with puzzles
	 * @param state
	 */
	public void finish(GamePlayState state) {
		state.displayDialogue(new String[] {"The room starts shaking... You better get out before" + 
				" you are trapped in the virtual world forever"});
		// state.shakeRoom() 
	}
	public String queryAnswer3() {
		String ret = "";
		FileReader fileReader;
		try {
			fileReader = new FileReader("");
		}catch (FileNotFoundException e){
			return "404";
		}catch (IOException e) {
			System.out.println("Query 3 IOException");
		}
		return ret;
	}
	public String queryAnswer2() {
		String ret = "";
		FileReader fileReader;
		try {
			fileReader = new FileReader("leonardo.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuilder string = new StringBuilder();
			String line = null;
			while((line = bufferedReader.readLine()) != null) 
				string.append(line+"");
			bufferedReader.close();
			ret = string.toString();
			System.out.println("read: " + ret + " | size: " + ret.length());
		} catch (FileNotFoundException e) {
			return "404";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public String queryAnswer1() {
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
	private void writeJSON(){
		try {
			PrintWriter writer = new PrintWriter("virtualREALity.JSON", "UTF-8");
			writer.print(""+
			"{\n"+
			"\t\"scenario home\": {\n"+
			"\t\t\"player\": {\n"+
			"\t\t\t\"hunger\":\"low\",\n"+
			"\t\t\t\"happiness\":\"unhappy\",\n"+
			"\t\t\t\"sex appeal\":\"minimal\",\n"+
			"\t\t\t\"drive\":\"complacent\",\n"+
			"\t\t},\n"+
			"\t\t\"room\": {\n"+
			"\t\t\t\"bed\":\"uncomfortable\",\n"+
			"\t\t\t\"television\":\"distracting\",\n"+
			"\t\t\t\"door\":\"locked\",\n"+
			"\t\t\t\"computer\":\"misleading\",\n"+
			"\t\t}\n"+
			"\t}\n"+
			"}");
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
