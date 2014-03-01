import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;


public class ImagePanel extends JPanel implements Observer {
	private int displayMode;
	
	public ImagePanel() {
		this.setLayout(new GridLayout(2, 2, 5, 5));
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		this.add(new JLabel("Image 1"));
		this.add(new JLabel("Image 2"));
		this.add(new JLabel("Image 3"));
		this.add(new JLabel("Image 4"));
		
		//this.displayMode = settings.getDisplayMode();
	}
	
	@Override
	public void update(Observable subject, Object data) {
		// TODO Auto-generated method stub
	}
	
	public void scale() {
		
	}
}
