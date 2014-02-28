import java.awt.BorderLayout;

import javax.swing.JFrame;


public class ImageViewerWindow extends JFrame {
	private ImagePanel imagePanel;
	private ImageViewerMenuBar menuBar;
	//private NotificationPanel notifications;
	//private Settings settings;
	//private BrowseCommand browse;

	public ImageViewerWindow() {
		this.menuBar = new ImageViewerMenuBar();
		this.imagePanel = new ImagePanel();
		
		this.setJMenuBar(this.menuBar);
		
		this.setLayout(new BorderLayout());

		this.add(imagePanel, BorderLayout.CENTER);
		
		this.setVisible(true);
		this.setSize(800, 600);
		this.setTitle("Medical Image Viewing System");
	}
}
