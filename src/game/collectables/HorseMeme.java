package game.collectables;

import java.awt.Toolkit;
import java.io.IOException;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import game.Collectable;
import game.gameplayStates.GamePlayState;
import game.player.Player;
import game.popup.CloseFrame;

public class HorseMeme extends Collectable {

	public HorseMeme() throws SlickException{
		super();
		this.setName("HorseMeme");
		this.setSprite(new Image("assets/imageIcon.png"));
	}

	@Override
	public String getItemName() {
		// TODO Auto-generated method stub
		return "HorseMeme";
	}

	@Override
	public String getItemText() {
		// TODO Auto-generated method stub
		return "A strange girl gave this to you. Maybe it will cheer you up?";
	}

	@Override
	public Types getType() {
		// TODO Auto-generated method stub
		return Types.HORSE_MEME;
	}
	@Override
	public boolean isConsumable() {
		return true;
	}
	@Override
	public void onUse(Player p, GamePlayState state) {
		Runnable thread = new Runnable() {
			final java.awt.Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
			public void run() {
				try {
					int X = (int)((ScreenSize.getWidth()/2-140));
					int Y = (int)((ScreenSize.getHeight()/2-160));
					new CloseFrame(X, Y, 280, 320, 
							"assets/hoarsememe.jpg");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		try {
			Sound sadTrom = new Sound("assets/sounds/sadTrom.wav");
			sadTrom.play();
		} catch (SlickException e) {
		}
		thread.run();
		state.shakeCamera(6000);
		p.getHealth().updateHealth(-25);
		p.m_inInventory = false;
		state.displayDialogue(new String[] {"AAAAAHHHHHHHHHHH!", "The agony!", "Dear lord who would create this thing!?", 
		"This is the worst thing you've ever seen!", "You throw it into the wind" +
		" but you still feel physically sick.", "Maybe you should head to the hospital"});
	}
}
