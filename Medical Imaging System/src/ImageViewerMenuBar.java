import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
//import java.util.Arrays;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
//import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageViewerMenuBar extends JMenuBar {
	
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu settingsMenu;
	private static Study currentStudy;
	
	public ImageViewerMenuBar(){
		
		InitFileMenu();
		InitEditMenu();
		InitSettingsMenu();
		
		add(fileMenu);
		add(editMenu);
		add(settingsMenu);
	}


	public void InitFileMenu(){
		this.fileMenu=new JMenu("File");
		JMenuItem openImage = new JMenuItem("Open...");
		JMenuItem exitApp=new JMenuItem("Exit");
		fileMenu.add(openImage);
		fileMenu.add(exitApp);
		openImage.addActionListener(new OpenFile());
		exitApp.addActionListener(new ExitProgram());
	}
	
	public void InitEditMenu(){
		this.editMenu=new JMenu("Edit");
		JMenuItem saveStudy = new JMenuItem("Save Study");
		saveStudy.addActionListener(new SaveStudy());
		editMenu.add(saveStudy);
	}
	public static void setCurrentStudy(Study st){
		currentStudy=st;
	}
	
	public void InitSettingsMenu(){
		this.settingsMenu=new JMenu("Settings");
		JMenuItem defaultStudy = new JMenuItem("Choose Default Study");
		JMenu displayMode = new JMenu("Change Display Mode");
		settingsMenu.add(defaultStudy);
		defaultStudy.addActionListener(new DefaultStudy());
		
		ButtonGroup bGroup= new ButtonGroup();
		JMenuItem displayMode1 = new JRadioButtonMenuItem("Single Image");
		bGroup.add(displayMode1);
		displayMode1.addActionListener(new ToDispMode1());
		displayMode1.setSelected(true);
		
		JMenuItem displayMode4 = new JRadioButtonMenuItem("Four Images");
		bGroup.add(displayMode4);
		displayMode4.addActionListener(new ToDispMode4());
		
		displayMode.add(displayMode1);
		displayMode.add(displayMode4);
		settingsMenu.add(displayMode);
	}
	
	static class ExitProgram implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    }

	static class OpenFile implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JFileChooser chooser = new MedicalImageFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			Study opStudy = chooseStudy(chooser);
			try {
				//System.out.println(opStudy.toString());
				setCurrentStudy(opStudy);
				opStudy.open();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
	}
	
	static class SaveStudy implements ActionListener{
        public void actionPerformed(ActionEvent e){
        	JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);
			int returnVal = chooser.showSaveDialog(null);
			if(returnVal!=JFileChooser.APPROVE_OPTION){
				chooser.cancelSelection();
			}
			File chFile = chooser.getSelectedFile();
			Study saveStudy= new Study(chFile);
			//Study saveStudy = chooseStudy(chooser);
			SaveCommand save = new SaveCommand(saveStudy, chFile.getName());
        }
    }
	
	
	static class DefaultStudy implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);
			Study defStudy = chooseStudy(chooser);
		}
	}
	
	static class ToDispMode1 implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			currentStudy.getStudySettings().setDisplayMode(DISPLAY_MODE_VALUE.ONE_IMAGE);
		}
	}
	
	static class ToDispMode4 implements ActionListener{
		public void actionPerformed(ActionEvent e){
			currentStudy.getStudySettings().setDisplayMode(DISPLAY_MODE_VALUE.FOUR_IMAGE);
		}
	}
	
	
	static Study chooseStudy(JFileChooser chooser){
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal!=JFileChooser.APPROVE_OPTION){
			chooser.cancelSelection();
		}
		String chPath = chooser.getSelectedFile().getAbsolutePath();
		Study chStudy= new Study(new File(chPath));
		return chStudy;	
	}
	
	static Study SaveNewStudy(JFileChooser chooser){
		int returnVal = chooser.showSaveDialog(null);
		if(returnVal!=JFileChooser.APPROVE_OPTION){
			chooser.cancelSelection();
		}
		File chFile = chooser.getSelectedFile();
		Study chStudy= new Study(chFile);
		return chStudy;	
	}
}
