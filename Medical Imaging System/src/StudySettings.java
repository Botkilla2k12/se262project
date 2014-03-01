import java.io.File;
import java.util.Scanner;

/**
 * 
 * @author derek
 *
 */
public class StudySettings {
	private static final String CONFIG_FILE = "study.cfg";
	private File directory;
	private DISPLAY_MODE_VALUE displayMode;
	
	public StudySettings(File directory) {
		this.directory = directory;
		try {
			File configFile =
				new File(directory.getAbsolutePath() + "\\" + CONFIG_FILE);
			
			Scanner sc = new Scanner(configFile);
			
			if(!configFile.exists()) {
				configFile.createNewFile();
			}
			
			String displayModeVal = sc.nextLine();
			
			if(displayModeVal.equals("ONE_IMAGE")) {
				this.displayMode = DISPLAY_MODE_VALUE.ONE_IMAGE;
			} else {
				this.displayMode = DISPLAY_MODE_VALUE.FOUR_IMAGE;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public DISPLAY_MODE_VALUE getDisplayMode() {
		return displayMode;
	}
	
	public void setDisplayMode(DISPLAY_MODE_VALUE newDisplayMode) {
		this.displayMode = newDisplayMode;
	}
}