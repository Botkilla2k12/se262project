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
import javax.swing.border.EtchedBorder;


public class ImagePanel extends JPanel implements Observer {
	private GridLayout layout;
	private DISPLAY_MODE_VALUE displayMode;
	
	public ImagePanel(DISPLAY_MODE_VALUE displayMode) {
		setDisplayMode(displayMode);

		this.setLayout(this.layout);
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
	}
	
	@Override
	public void update(Observable subject, Object data) {
		Study study = (Study) subject;
		
		setDisplayMode(study.getStudySettings().getDisplayMode());
		int numEntries = DISPLAY_MODE_VALUE.getValue(displayMode);
		
		ArrayList<BufferedImage> images = study.getCurrentImages();
		
		scale(images.get(0));
		
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
	
	public void scale(BufferedImage image) {
		super.setSize(image.getWidth() * 2, image.getWidth() * 2);
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
