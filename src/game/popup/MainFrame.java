package game.popup;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * properties:
 * -Undecorated
 * -Draggable
 * -Cannot close/scale
 */
public class MainFrame extends JDialog{
	
	static Point mouseDownCompCoords;
	private JDialog m_f;
	
	public MainFrame(int x, int y, int width, int height, String path) throws IOException {	
		setFocusableWindowState(false);
		setLocation(x, y);
		setSize(width, height);
		setMinimumSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
        BufferedImage image = ImageIO.read(new File(path));
		setContentPane(new ImagePanel(image));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setUndecorated(true);
		getContentPane().add(new JLabel("", JLabel.CENTER),BorderLayout.CENTER);
		pack();
		setVisible(true);
		
		this.addMouseListener(new MouseListener(){
			    public void mouseReleased(MouseEvent e) {
			    	mouseDownCompCoords = null;
			    }
			    public void mousePressed(MouseEvent e) {
			    	mouseDownCompCoords = e.getPoint();
			    }
			    public void mouseExited(MouseEvent e) {
			    }
			    public void mouseEntered(MouseEvent e) {
			    }
			    public void mouseClicked(MouseEvent e) {
			    }
		});
		
		m_f = this;
		
		this.addMouseMotionListener(new MouseMotionListener(){
			    public void mouseMoved(MouseEvent e) {
			    }
			     
			    public void mouseDragged(MouseEvent e) {
			    	Point currCoords = e.getLocationOnScreen();
			    	m_f.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
			    }
		});
	}

}
