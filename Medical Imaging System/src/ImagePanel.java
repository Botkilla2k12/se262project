import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;


public class ImagePanel extends JPanel implements Observer {
	private GridLayout layout;
	private DISPLAY_MODE_VALUE displayMode;
	
	public ImagePanel(DISPLAY_MODE_VALUE displayMode) {
		setDisplayMode(displayMode);

		this.setLayout(this.layout);
		
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
	}
	
	@Override
	public void update(Observable subject, Object data) {
		Study study = (Study) subject;
		
		setDisplayMode(study.getStudySettings().getDisplayMode());
		
		ArrayList<BufferedImage> images = study.getCurrentImages();
		
		//for each image, render the image in a JPanel
		for(BufferedImage img: images) {
			super.add(new JLabel(new ImageIcon(img)));
		}
		
		super.repaint();
	}
	
	public void scale() {
		
	}
	
	private void setDisplayMode(DISPLAY_MODE_VALUE mode) {
		this.displayMode = mode;
		
		if(mode == DISPLAY_MODE_VALUE.ONE_IMAGE) {
			configLayout(1, 1);
		} else {
			configLayout(2, 2);
		}
		
		for(int i = 0; i < DISPLAY_MODE_VALUE.getValue(mode); i++) {
			this.add(new JPanel());
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
