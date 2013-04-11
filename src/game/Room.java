package game;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Room extends BasicGame{
	
	private TiledMap m_horseMap;
	
	public Room() {
		super ("Room");
		try {
			m_horseMap = new TiledMap("assets/testmap.tmx");
		} catch (SlickException e) {
			System.out.println("ERROR: Could not open testmap.tmx");
		}
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		// TODO Auto-generated method stub
		
	}
	
	
}
