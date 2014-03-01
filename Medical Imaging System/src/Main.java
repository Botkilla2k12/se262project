import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class Main {
	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(new File("defaultstudy.cfg"));

			String defaultPath = sc.nextLine();
			SystemSettings settings = new SystemSettings();
			
			if(defaultPath.equals("null")) {
				//prompt
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
					//show file chooser and select directory
				} else {
					settings.setDefaultStudy(new Study(null));
				}
			} else {
				settings.setDefaultStudy(new Study(new File(defaultPath)));
			}
			
			ImageViewerWindow win = new ImageViewerWindow(
				settings.getDefaultStudy()
			);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
