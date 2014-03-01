import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		try {
			SystemSettings settings = new SystemSettings();
			Study study = null;
			
			if(!settings.hasDefaultStudy()) {
				int result = JOptionPane.showOptionDialog(
					null,
					"Would you like to select a default study?",
					"Select default Study", 
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					null,
					null
				);
				
				JFileChooser chooser = new MedicalImageFileChooser();

				int returnVal = chooser.showOpenDialog(null);
				String filePath = null;
				if(returnVal==JFileChooser.APPROVE_OPTION){
					filePath = chooser.getSelectedFile().getPath();		
				}
				
				study = new Study(new File(filePath));
				
				if(result == JOptionPane.YES_OPTION) {
					settings.setDefaultStudy(study);
				}
				
				new ImageViewerWindow(study);
			} else {
				new ImageViewerWindow(
					settings.getDefaultStudy()
				);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
