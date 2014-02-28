import javax.swing.JFrame;


public class ImageViewerWindow extends JFrame {
	private ImagePanel imagePanel;
	private ImageViewerMenuBar menuBar;
	private NotificationPanel notifications;
	private Settings settings;
	//private BrowseCommand browse;

	public ImageViewerWindow() {
		menuBar = new ImageViewerMenuBar();
	}
}
