import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
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
		exitApp.addActionListener(new ExitProgram());
		openImage.addActionListener(new OpenFile());
		
		this.editMenu=new JMenu("Edit");
		JMenuItem createStudy = new JMenuItem("Create Study");
		editMenu.add(createStudy);
		createStudy.addActionListener(new CreateStudy());
		
		
		this.settingsMenu=new JMenu("Settings");
		JMenuItem defaultStudy = new JMenuItem("Choose Default Study");
		settingsMenu.add(defaultStudy);
		defaultStudy.addActionListener(new DefaultStudy());
		

		JMenu displayMode = new JMenu("Change Display Mode");
		settingsMenu.add(displayMode);
		JMenuItem displayMode1 = new JMenuItem("Single Image");
		JMenuItem displayMode4 = new JMenuItem("Four Images");
		displayMode.add(displayMode1);
		displayMode.add(displayMode4);
		displayMode.addActionListener(new ChangeDisplayMode());
		
		
		add(fileMenu);
		add(editMenu);
		add(settingsMenu);
		
		
		
		
	}
	static class ExitProgram implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    }


	static class OpenFile implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			//FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG file", ".jpg", ".jpeg");
			//chooser.setFileFilter(filter);
			chooser.setAcceptAllFileFilterUsed(false);
			int returnVal = chooser.showOpenDialog(null);
			if(returnVal==JFileChooser.APPROVE_OPTION){
				String filePath = chooser.getSelectedFile().getPath();		
			}
			else{
				System.exit(0);
			}
        }
	}
	
	static class CreateStudy implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    }
	static class DefaultStudy implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			//FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG file", ".jpg", ".jpeg");
			//chooser.setFileFilter(filter);
			chooser.setAcceptAllFileFilterUsed(false);
			
			int returnVal = chooser.showOpenDialog(null);
			if(returnVal==JFileChooser.APPROVE_OPTION){
				String dStudy = chooser.getSelectedFile().getPath();		
			}
			else{
				System.exit(0);
			}
			
		}
	}
	static class ChangeDisplayMode implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    }
	
}
