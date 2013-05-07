package game.popup;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class CloseFrame extends JDialog {

	public CloseFrame(int x, int y, int width, int height, String path) throws IOException {	
		setFocusableWindowState(false);
		setLocation(x, y);
		setSize(width, height);
		setMinimumSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
        BufferedImage image = ImageIO.read(new File(path));
		setContentPane(new ImagePanel(image));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setUndecorated(false);
		pack();
		setVisible(true);
		
	}
	
}
