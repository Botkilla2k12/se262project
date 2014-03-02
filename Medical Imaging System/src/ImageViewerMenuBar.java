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
    
    private JRadioButtonMenuItem displayMode1, displayMode4;
    
    public ImageViewerMenuBar(){
        InitFileMenu();
        InitEditMenu();
        InitSettingsMenu();
        
        add(fileMenu);
        add(editMenu);
        add(settingsMenu);
    }

    public void activateRadioButtonFromDisplayMode(DISPLAY_MODE_VALUE mode) {
    	if(mode == DISPLAY_MODE_VALUE.ONE_IMAGE) {
    		this.displayMode1.setSelected(true);
    		this.displayMode4.setSelected(false);
    	} else {
    		this.displayMode4.setSelected(true);
    		this.displayMode1.setSelected(false);
    	}
    }
    
    private void InitFileMenu(){
        this.fileMenu=new JMenu("File");
        JMenuItem openImage = new JMenuItem("Open...");
        JMenuItem exitApp=new JMenuItem("Exit");
        fileMenu.add(openImage);
        fileMenu.add(exitApp);
        openImage.addActionListener(new OpenFile());
        exitApp.addActionListener(new ExitProgram());
    }
    
    private void InitEditMenu(){
        this.editMenu=new JMenu("Edit");
        JMenuItem saveStudy = new JMenuItem("Save Study");
        saveStudy.addActionListener(new SaveStudy());
        editMenu.add(saveStudy);
    }
    
    private void InitSettingsMenu(){
        this.settingsMenu=new JMenu("Settings");
        JMenuItem defaultStudy = new JMenuItem("Choose Default Study");
        JMenu displayMode = new JMenu("Change Display Mode");
        settingsMenu.add(defaultStudy);
        defaultStudy.addActionListener(new DefaultStudy());
        
        ButtonGroup bGroup= new ButtonGroup();
        displayMode1 = new JRadioButtonMenuItem("Single Image");
        bGroup.add(displayMode1);
        displayMode1.addActionListener(new ToDispMode1());
        displayMode1.setSelected(true);
        
        displayMode4 = new JRadioButtonMenuItem("Four Images");
        bGroup.add(displayMode4);
        displayMode4.addActionListener(new ToDispMode4());
        
        displayMode.add(displayMode1);
        displayMode.add(displayMode4);
        settingsMenu.add(displayMode);
    }
    
    class ExitProgram implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    }

    class OpenFile implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JFileChooser chooser = new MedicalImageFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            try{
            	Study opStudy = chooseStudy(chooser);
                
                ImageViewerWindow parentWin =
                	(ImageViewerWindow) getTopLevelAncestor();
                
                parentWin.setupNewStudy(opStudy);	
            } catch (NullPointerException i) {
            }
            
        }
    }
    
    class SaveStudy implements ActionListener{
        public void actionPerformed(ActionEvent e)throws NullPointerException{
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            
            int returnVal = chooser.showSaveDialog(null);
            if(returnVal!=JFileChooser.APPROVE_OPTION){
                chooser.cancelSelection();
            }
            try{
            	File chFile = chooser.getSelectedFile();
                Study saveStudy= new Study(chFile);
                SaveCommand save = new SaveCommand(saveStudy, chFile.getName());
                save.save();	
            }catch (NullPointerException i) {
            	
            }
            
        }
    }
    
    
    class DefaultStudy implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            
            SystemSettings sysSettings = new SystemSettings();
            try{
            	Study defStudy = chooseStudy(chooser);
            	sysSettings.setDefaultStudy(defStudy);
            } catch (NullPointerException i){
            }
            	

        }
    }
    
    class ToDispMode1 implements ActionListener{
        public void actionPerformed(ActionEvent e){
        	ImageViewerWindow parentWin =
            	(ImageViewerWindow) getTopLevelAncestor();
            
            parentWin.setPanelDisplayMode(DISPLAY_MODE_VALUE.ONE_IMAGE);
        }
    }
    
    class ToDispMode4 implements ActionListener{
        public void actionPerformed(ActionEvent e){
        	ImageViewerWindow parentWin =
            	(ImageViewerWindow) getTopLevelAncestor();
            
            parentWin.setPanelDisplayMode(DISPLAY_MODE_VALUE.FOUR_IMAGE);
        }
    }
    
    
    private Study chooseStudy(JFileChooser chooser)throws NullPointerException{
        int returnVal = chooser.showOpenDialog(null);

        if(returnVal!=JFileChooser.APPROVE_OPTION){
            chooser.cancelSelection();
        }

    	String chPath = chooser.getSelectedFile().getAbsolutePath();
        Study chStudy= new Study(new File(chPath));
        return chStudy;
    }
    
    static Study SaveNewStudy(JFileChooser chooser) throws NullPointerException{
        int returnVal = chooser.showSaveDialog(null);
        if(returnVal!=JFileChooser.APPROVE_OPTION){
            chooser.cancelSelection();
        }
        File chFile = chooser.getSelectedFile();
        Study chStudy= new Study(chFile);
        return chStudy;    
    }
}
