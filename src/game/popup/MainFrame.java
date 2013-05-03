package game.popup;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class MainFrame extends JFrame{

//	  private BufferedImage m_image;
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        // Draw image centered.
//        int x = (getWidth() - m_image.getWidth())/2;
//        int y = (getHeight() - m_image.getHeight())/2;
//        g.drawImage(m_image, x, y, this);
//    }
	
	public MainFrame() throws IOException {
		setFocusableWindowState(false);
		setSize(256, 256);
		setMinimumSize(new Dimension(256, 256));
		setMaximumSize(new Dimension(256, 256));
		String path = "assets/popup_horse.png";
        BufferedImage image = ImageIO.read(new File(path));
		setContentPane(new ImagePanel(image));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setVisible(false);
		
	}

}
