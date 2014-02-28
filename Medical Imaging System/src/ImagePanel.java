import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;


public class ImagePanel extends JPanel implements Observer {
	private GridLayout imageLayout;
	
	public ImagePanel() {
		this.imageLayout = new GridLayout(2, 2, 10, 10);
	}
	
	@Override
	public void update(Observable subject, Object data) {
		
	}
	
	public void scale() {
		
	}
}
