import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;


public class NotificationPanel extends JPanel implements Observer {
	public NotificationPanel() {
		this.setBackground(Color.WHITE);
	}
	
	@Override
	public void update(Observable subject, Object data) {
		// TODO Auto-generated method stub
		
	}

}
