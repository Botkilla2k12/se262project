import javax.swing.JMenuBar;
import javax.swing.JMenu;

public class ImageViewerMenuBar extends JMenuBar {
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu settingsMenu;

	public ImageViewerMenuBar(){
		this.fileMenu=new JMenu("File");
		this.editMenu=new JMenu("Edit");
		this.settingsMenu=new JMenu("Settings");

		add(fileMenu);
		add(editMenu);
		add(settingsMenu);
	}
}
