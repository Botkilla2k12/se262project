import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
<<<<<<< HEAD

=======
import java.io.File;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
>>>>>>> branch 'master' of https://github.com/Botkilla2k12/se262project.git
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
<<<<<<< HEAD
=======
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;
>>>>>>> branch 'master' of https://github.com/Botkilla2k12/se262project.git

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
		JMenuItem openImage = new JMenuItem("Open...");
		JMenuItem exitApp=new JMenuItem("Exit");
		fileMenu.add(openImage);
		fileMenu.add(exitApp);
		exitApp.addActionListener(new exitProgram());
		
		this.editMenu=new JMenu("Edit");
		JMenuItem createStudy = new JMenuItem("Create Study");
		editMenu.add(createStudy);
		
		this.settingsMenu=new JMenu("Settings");
		JMenuItem defaultStudy = new JMenuItem("Choose Default Study");
		JMenuItem displayMode = new JMenuItem("Change Display Mode");
		settingsMenu.add(defaultStudy);
		settingsMenu.add(displayMode);
<<<<<<< HEAD
=======
		ButtonGroup bGroup= new ButtonGroup();
		JMenuItem displayMode1 = new JRadioButtonMenuItem("Single Image");
		bGroup.add(displayMode1);
		displayMode1.setSelected(true);
		JMenuItem displayMode4 = new JRadioButtonMenuItem("Four Images");
		bGroup.add(displayMode4);
		ButtonGroup bGroup= new ButtonGroup();
		JMenuItem displayMode1 = new JRadioButtonMenuItem("Single Image");
		bGroup.add(displayMode1);
		displayMode1.setSelected(true);
		JMenuItem displayMode4 = new JRadioButtonMenuItem("Four Images");
		bGroup.add(displayMode4);
		displayMode.add(displayMode1);
		displayMode.add(displayMode4);
		//displayMode.addActionListener(new ChangeDisplayMode());
		
>>>>>>> branch 'master' of https://github.com/Botkilla2k12/se262project.git
		
		add(fileMenu);
		add(editMenu);
		add(settingsMenu);
	}

<<<<<<< HEAD
	static class exitProgram implements ActionListener{
=======


	static class ExitProgram implements ActionListener{
>>>>>>> branch 'master' of https://github.com/Botkilla2k12/se262project.git
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    }
<<<<<<< HEAD
=======

	static class OpenFile implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JFileChooser chooser = new MedicalImageFileChooser();

			//FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG file", ".jpg", ".jpeg");
			//chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(null);
			if(returnVal==JFileChooser.APPROVE_OPTION){
				String filePath = chooser.getSelectedFile().getPath();		
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
	
>>>>>>> branch 'master' of https://github.com/Botkilla2k12/se262project.git
}
