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
	private int lastImageIndex;
	
	public StudySettings(File directory) {
		this.directory = directory;
		Scanner sc = null;
		try {
			File configFile =
				new File(directory.getAbsolutePath() + "\\" + CONFIG_FILE);
			
			if(!configFile.exists()) {
				configFile.createNewFile();
				
				this.displayMode = DISPLAY_MODE_VALUE.ONE_IMAGE;
				setLastImageIndex(0);
			}

			sc = new Scanner(configFile);
			if (sc.hasNextLine()){
				String displayModeVal = sc.nextLine();
			
				if(displayModeVal.equals("ONE_IMAGE")) {
					this.displayMode = DISPLAY_MODE_VALUE.ONE_IMAGE;
				} else {
					this.displayMode = DISPLAY_MODE_VALUE.FOUR_IMAGE;
				}
				
				this.lastImageIndex = Integer.parseInt(sc.nextLine());
			}
			
		} catch (NullPointerException i) {
			
		} catch(Exception e) {
			e.printStackTrace(); 
		}
		
	}
	
	public int getLastImageIndex() {
		return this.lastImageIndex;
	}

	public DISPLAY_MODE_VALUE getDisplayMode() {
		return displayMode;
	}
	
	public void setDisplayMode(DISPLAY_MODE_VALUE newDisplayMode) {
		this.displayMode = newDisplayMode;
		writeInformationToDisk(this.displayMode, this.lastImageIndex);
	}
	
	public void setLastImageIndex(int newIndex) {
		this.lastImageIndex = newIndex;
		writeInformationToDisk(this.displayMode, this.lastImageIndex);
	}
	

	private void writeInformationToDisk(DISPLAY_MODE_VALUE mode, int index) {
		PrintWriter writer = null;
		try{
			writer = new PrintWriter(
				this.directory.getAbsolutePath() + "\\" + CONFIG_FILE, "UTF-8"
			);
			writer.println(mode);
			writer.println(index);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}
}
