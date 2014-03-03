import java.awt.Color;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

/**
 * This class is a panel on which all images from the study are displayed.
 * This class basically functions as a View in MVC architecture.
 * @author Curtis Cali
 *
 */
public class ImagePanel extends JPanel implements Observer {
	private GridLayout layout;
	private DISPLAY_MODE_VALUE displayMode;
	
	/**
	 * Initializes an ImagePanel with a given display mode so that the right
	 * layout can be created.
	 * @param displayMode the display mode of the panel
	 */
	public ImagePanel(DISPLAY_MODE_VALUE displayMode) {
		setDisplayMode(displayMode);
	
		this.displayMode = displayMode;
		this.setLayout(this.layout);
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
	}
	
	/**
	 * This method updates the view based on the current model's state
	 */
	@Override
	public void update(Observable subject, Object data) {
		Study study = (Study) subject;
		
		setDisplayMode(study.getStudySettings().getDisplayMode());
		int numEntries = DISPLAY_MODE_VALUE.getValue(displayMode);
		
		ArrayList<BufferedImage> images = study.getCurrentImages();
		
		super.removeAll();
		
		//for each image, render the image in a JPanel
		for(BufferedImage img: images) {
			super.add(new JLabel(new ImageIcon(img)));
		}
		
		if(images.size() < numEntries) {
			for(int i = 0; i < numEntries - images.size(); i++) {
				super.add(new JLabel());
			}
		}
		
		super.revalidate();
		super.repaint();
	}
	
	private void setDisplayMode(DISPLAY_MODE_VALUE mode) {
		this.displayMode = mode;
		
		if(mode == DISPLAY_MODE_VALUE.ONE_IMAGE) {
			configLayout(1, 1);
		} else {
			configLayout(2, 2);
		}
	}
	
	private void configLayout(int r, int c) {
		if(this.layout == null) { 
			this.layout = new GridLayout(r, c);
		} else {
			this.layout.setColumns(r);
			this.layout.setRows(c);
		}
	}
}
