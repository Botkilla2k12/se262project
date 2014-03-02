
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
						System.out.println(study);
						study = new Study(new File(filePath));
						if(result == JOptionPane.YES_OPTION) {
							//System.out.println(study);
							settings.setDefaultStudy(study);
	
						} 
				
				
				
				}
			} else {
				//System.out.println(settings.getDefaultStudy());
				study = settings.getDefaultStudy();
			}
			//System.out.println(study);
			new ImageViewerWindow(study);
		}catch (NullPointerException i) {
			System.out.println(i.getMessage() + "NullPointerException");
		}
		catch (Exception e) {
			e.printStackTrace();
		}	

	}
}
