import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class ImageViewerMenuBar extends JMenuBar {
	
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu settingsMenu;
	//private JMenuItem open;
	//private JMenuItem exit;
	//private JMenuItem createStudy;
	//private JMenuItem settingPlaceholder;
	
	
	
	public ImageViewerMenuBar(){
		this.fileMenu=new JMenu("File");
		fileMenu.add(new JMenuItem("Open..."));
		fileMenu.add(new JMenuItem("Exit"));
		this.editMenu=new JMenu("Edit");
		editMenu.add(new JMenuItem("Create Study"));
		this.settingsMenu=new JMenu("Settings");
		settingsMenu.add(new JMenuItem("Choose Default Study"));
		settingsMenu.add(new JMenuItem("Change Display Mode"));
		add(fileMenu);
		add(editMenu);
		add(settingsMenu);
	}
}
