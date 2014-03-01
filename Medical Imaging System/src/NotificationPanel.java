import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JPanel;


public class NotificationPanel extends JPanel implements Observer {
	private BoxLayout layout;
	
	public NotificationPanel() {
		this.setBackground(Color.WHITE);
		
		//this.layout = new BoxLayout(
	}
	
	@Override
	public void update(Observable subject, Object data) {
		// TODO Auto-generated method stub
		
	}

}
