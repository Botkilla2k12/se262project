
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

				if(result == JOptionPane.YES_OPTION) {
					JFileChooser chooser = new MedicalImageFileChooser();
					
					int retval = chooser.showOpenDialog(null);
					
					if(retval == JFileChooser.APPROVE_OPTION) {
						//set default study
						String path = chooser.getSelectedFile().getAbsolutePath();
						study = new Study(new File(path));
						settings.setDefaultStudy(study);
					} else {
						retval = chooser.showOpenDialog(null);
						
						if(retval == JFileChooser.APPROVE_OPTION) {
							String path = chooser.getSelectedFile().getAbsolutePath();
							study = new Study(new File(path));
						} else {
							study = new Study(null);
						}
					}
				} else {
					JFileChooser chooser = new MedicalImageFileChooser();
					
					int retval = chooser.showOpenDialog(null);
					
					if(retval == JFileChooser.APPROVE_OPTION) {
						//set default study
						String path = chooser.getSelectedFile().getAbsolutePath();
						study = new Study(new File(path));
					} else {
						study = new Study(null);
					}
				}
			} else {
				study = settings.getDefaultStudy();
			}
	
			new ImageViewerWindow(study);
		}catch (NullPointerException i) {
			System.out.println(i.getMessage() + "NullPointerException");
		}
		catch (Exception e) {
			e.printStackTrace();
		}	

	}
}
