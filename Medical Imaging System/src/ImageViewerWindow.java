import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class ImageViewerWindow extends JFrame {
	private ImagePanel imagePanel;
	private ImageViewerMenuBar menuBar;
	private NotificationPanel notifications;
	private JButton prevButton, nextButton;
	//private Settings settings;
	//private BrowseCommand browse;

	public ImageViewerWindow() {
		this.menuBar = new ImageViewerMenuBar();
		this.imagePanel = new ImagePanel();
		this.notifications = new NotificationPanel();
		this.prevButton = new JButton("Previous");
		this.nextButton = new JButton("Next");
		
		this.setJMenuBar(this.menuBar);
		
		this.setLayout(new BorderLayout());

		this.add(this.imagePanel, BorderLayout.CENTER);
		this.add(this.notifications, BorderLayout.EAST);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		
		buttonPanel.add(this.prevButton, BorderLayout.WEST);
		buttonPanel.add(this.nextButton, BorderLayout.EAST);
		
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		this.setVisible(true);
		this.setSize(800, 600);
		this.setTitle("Medical Image Viewing System");
	}
}
