import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageViewerMenuBar extends JMenuBar {
	
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu settingsMenu;
	
	
	public ImageViewerMenuBar(){
		this.fileMenu=new JMenu("File");
		JMenuItem openImage = new JMenuItem("Open...");
		JMenuItem exitApp=new JMenuItem("Exit");
		fileMenu.add(openImage);
		fileMenu.add(exitApp);
		exitApp.addActionListener(new exitProgram());
		openImage.addActionListener(new openFile());
		
		this.editMenu=new JMenu("Edit");
		JMenuItem createStudy = new JMenuItem("Create Study");
		editMenu.add(createStudy);
		
		this.settingsMenu=new JMenu("Settings");
		JMenuItem defaultStudy = new JMenuItem("Choose Default Study");
		JMenuItem displayMode = new JMenuItem("Change Display Mode");
		settingsMenu.add(defaultStudy);
		settingsMenu.add(displayMode);
		
		add(fileMenu);
		add(editMenu);
		add(settingsMenu);
		
		
		
		
	}
	static class exitProgram implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    }


	static class openFile implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg");
			chooser.addChoosableFileFilter(filter);
			int returnVal = chooser.showOpenDialog(null);
			if(returnVal==JFileChooser.APPROVE_OPTION){
				File file = chooser.getSelectedFile();
			}
			else{
				System.exit(0);
			}
        }
		
	}
}
