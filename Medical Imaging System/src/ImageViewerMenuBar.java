import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

public class ImageViewerMenuBar extends JMenuBar {
    
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu settingsMenu;
    
    private JRadioButtonMenuItem displayMode1, displayMode4;
    private JMenuItem exitReconstructMode;
    
    private JMenu relatedStudies;
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
    
    public void setRelatedStudies(File currDir){
        
        if(this.relatedStudies!=null)
            this.fileMenu.remove(this.relatedStudies);
        
        this.relatedStudies = new JMenu("Related Studies...");
        String[] relFiles=currDir.list();
        for(String s:relFiles){
            if(!s.endsWith(".jpeg") && !s.endsWith(".acr") && !s.endsWith(".jpg") && !s.endsWith(".cfg")){
                JMenuItem tempMenuItem= new JMenuItem(s);
                this.relatedStudies.add(tempMenuItem);
                Study relStud = new Study(new File(currDir.getName()+'\\'+s));
                addRelatedListener(relStud, tempMenuItem);
                
            }
        }

        this.fileMenu.add(this.relatedStudies);
    }
    
    public void addRelatedListener(Study relStudy, JMenuItem menuItem){
        final Study relatedStudy=relStudy;
        final JMenuItem menIt=menuItem;
        menIt.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ImageViewerWindow parentWin =
                        (ImageViewerWindow) getTopLevelAncestor();
                
                parentWin.setupNewStudy(relatedStudy);
            }
        });
        
    }
    
    
    private void InitFileMenu(){
        this.fileMenu=new JMenu("File");

        JMenuItem openImage = new JMenuItem("Open...");
        JMenuItem undoOperation = new JMenuItem("Undo...");
        JMenuItem exitApp=new JMenuItem("Exit");
        openImage.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        undoOperation.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        JMenuItem saveStudy = new JMenuItem("Save Study");
        saveStudy.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveStudy.addActionListener(new SaveStudy());
        
        
        fileMenu.add(saveStudy);
        fileMenu.add(openImage);
        fileMenu.add(undoOperation);
        fileMenu.add(exitApp);

        openImage.addActionListener(new OpenFile());
        undoOperation.addActionListener(new UndoOperation());
        exitApp.addActionListener(new ExitProgram());
    }
    
    private void InitEditMenu(){
        this.editMenu=new JMenu("Edit");
        
        
        
        this.exitReconstructMode = new JMenuItem("Exit Reconstruction");
        this.exitReconstructMode.setEnabled(false);
        this.exitReconstructMode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JMenuItem source = (JMenuItem) e.getSource();
                ImageViewerWindow parentWin =
                    (ImageViewerWindow) getTopLevelAncestor();
                parentWin.setReconstructMode(false);
                source.setEnabled(false);
                
            }
        });
        this.editMenu.add(this.exitReconstructMode);
        
        JMenu windowing = new JMenu("Windowing Options...");
        JMenuItem wImage = new JMenuItem("Image");
        wImage.addActionListener(new WindowingModeImage());
        
        
        
        JMenuItem wStudy = new JMenuItem("Study");
        wStudy.addActionListener(new WindowingModeStudy());
        
        windowing.add(wImage); windowing.add(wStudy);
        windowing.addActionListener(new WindowingModeImage());
        editMenu.add(windowing);
        JMenu reconstructStudy = new JMenu("Reconstruct");

        ReconstructStudy eventListener = new ReconstructStudy();
        
        JMenuItem xz = new JMenuItem("Coronal");
        xz.addActionListener(eventListener);
        reconstructStudy.add(xz);
        
        JMenuItem yz = new JMenuItem("Sagittal");
        yz.addActionListener(eventListener);
        reconstructStudy.add(yz);
        
        this.editMenu.add(reconstructStudy);
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
        
        displayMode4 = new JRadioButtonMenuItem("Four Images");
        bGroup.add(displayMode4);
        displayMode4.addActionListener(new ToDispMode4());
        
        displayMode.add(displayMode1);
        displayMode.add(displayMode4);
        settingsMenu.add(displayMode);
    }
    
    private boolean checkInput(String low, String high){
    	if(!(isInteger(low) && isInteger(high))){
            dispError("Pleas input only numbers");
            return false;
        }
        int iLow=Integer.parseInt(low);
        if(iLow<0 || iLow>255){
            dispError("Only use numbers between 0 and 255");
            return false;
        }
        
        int iHigh = Integer.parseInt(high);
        if(iHigh<0 || iLow>255){
            dispError("Only use numbers between 0 and 255");
            return false;
        }
        
        if(!(iLow<iHigh)){
            dispError("High must be larger than Low");
            return false;
        }
        return true;
    	
    }
    
    private void dispError(String s){
        ImageViewerWindow parentWin =
                (ImageViewerWindow) getTopLevelAncestor();
        
        JOptionPane.showMessageDialog(parentWin,
                s,
                "You goofed",
                JOptionPane.ERROR_MESSAGE);
    }
    
    
    
    class ExitProgram implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    }
    
    class WindowingModeImage implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String low = JOptionPane.showInputDialog(" Input low value \n 0-255 \n");
            String high = JOptionPane.showInputDialog(" Input high value \n 0-255 \n");
            if(checkInput(low, high)){
            	//ImageWindowCommand winComm = new ImageWindowCommand(low, high, parentWin);
            	//winComm.execute();
            }
        }    
    }
    
    
    class WindowingModeStudy implements ActionListener{
        public void actionPerformed(ActionEvent e) {
        	String low = JOptionPane.showInputDialog(" Input low value \n 0-255 \n");
            String high = JOptionPane.showInputDialog(" Input high value \n 0-255 \n");
            if(checkInput(low, high)){
            	//ImageWindowCommand winComm = new ImageWindowCommand(low, high, parentWin);
            	//winComm.execute();
            }
        } 
    }
    
    class UndoOperation implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            ImageViewerWindow parentWin =
                    (ImageViewerWindow) getTopLevelAncestor();
            parentWin.undoStateChange();
        }
    }
    
    class OpenFile implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JFileChooser chooser = new MedicalImageFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            try{
                Study opStudy = chooseStudy(chooser);
                if(opStudy != null) {
                    ImageViewerWindow parentWin =
                        (ImageViewerWindow) getTopLevelAncestor();
                    parentWin.setupNewStudy(opStudy);
                }
            } catch (NullPointerException i) {
            }
        }
    }
    
    class SaveStudy implements ActionListener{
        public void actionPerformed(ActionEvent e)throws NullPointerException{
            ImageViewerWindow parentWin =
                    (ImageViewerWindow) getTopLevelAncestor();
            JFileChooser chooser = new JFileChooser(parentWin.getDirectory());
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            
            int returnVal = chooser.showSaveDialog(null);
            if(returnVal!=JFileChooser.APPROVE_OPTION){
                chooser.cancelSelection();
            }
            try{
                File chFile = chooser.getSelectedFile();
                SaveCommand save = new SaveCommand(
                    parentWin.getDirectory().getAbsolutePath(),
                    chFile.getAbsolutePath()
                );
                save.execute();
            }catch (NullPointerException i) {
                
            }
            
        }
    }
    
    class ReconstructStudy implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            ImageViewerWindow parentWin =
                 (ImageViewerWindow) getTopLevelAncestor();
            
            exitReconstructMode.setEnabled(true);
            
            String reconstructionType = "";
            
            if(((JMenuItem) e.getSource()).getText().equals("Coronal")) {
            	reconstructionType = "XZ";
            } else {
            	reconstructionType = "YZ";
            }
            
            String directory = parentWin.getDirectory().getAbsolutePath();
            
            ReconstructCommand reconstructor = new ReconstructCommand(
                directory,
                reconstructionType
            );
            reconstructor.execute();
            
            File imageDir = new File(reconstructor.getFolderPath());
            ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
            
            //read everything
            for(File f : imageDir.listFiles()) {
                try {
                    images.add(ImageIO.read(f));
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            
            parentWin.setReconstructImages(images);
            parentWin.setReconstructMode(true);
        }
    }
    
    class DefaultStudy implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            
            SystemSettings sysSettings = new SystemSettings();
            Study defStudy = chooseStudy(chooser);
            sysSettings.setDefaultStudy(defStudy);
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
    
    
    private Study chooseStudy(JFileChooser chooser)throws NullPointerException {
        int returnVal = chooser.showOpenDialog(null);

        if(returnVal == JFileChooser.APPROVE_OPTION) {
            String chPath = chooser.getSelectedFile().getAbsolutePath();
            return new Study(new File(chPath));
        } else {
            return null;
        }
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
    public boolean isInteger(String integerString){

           try{
              Integer.parseInt(integerString);
              return true;
           } catch (NumberFormatException nfe) {
              return false;
           }
        }
}
