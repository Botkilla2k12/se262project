import java.io.File;
import java.io.PrintWriter;
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
		Scanner sc = null;
		try {
			File configFile =
				new File(directory.getAbsolutePath() + "\\" + CONFIG_FILE);
			
			if(!configFile.exists()) {
				configFile.createNewFile();
				
				setDisplayMode(DISPLAY_MODE_VALUE.ONE_IMAGE);
			}
			
			sc = new Scanner(configFile);
			String displayModeVal = sc.nextLine();
			
			if(displayModeVal.equals("ONE_IMAGE")) {
				this.displayMode = DISPLAY_MODE_VALUE.ONE_IMAGE;
			} else {
				this.displayMode = DISPLAY_MODE_VALUE.FOUR_IMAGE;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}
	}

	public DISPLAY_MODE_VALUE getDisplayMode() {
		return displayMode;
	}
	
	public void setDisplayMode(DISPLAY_MODE_VALUE newDisplayMode) {
		this.displayMode = newDisplayMode;
		
		PrintWriter writer = null;;
		try {
			writer = new PrintWriter(
				directory.getAbsolutePath() + "\\" +  CONFIG_FILE, "UTF-8"
			);
			
			if(displayMode == DISPLAY_MODE_VALUE.ONE_IMAGE) {
				writer.println("ONE_IMAGE");
			} else {
				writer.println("FOUR_IMAGE");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}
}