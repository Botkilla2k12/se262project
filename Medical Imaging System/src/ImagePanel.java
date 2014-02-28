import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;


public class ImagePanel extends JPanel implements Observer {
	public ImagePanel() {
		this.setBackground(Color.BLACK);
	}
	
	@Override
	public void update(Observable subject, Object data) {
		// TODO Auto-generated method stub
	}
	
	public void scale() {
		
	}
}
