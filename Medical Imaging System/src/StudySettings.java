import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This class represents the settings associated with a particular study.
 * @author derek
 *
 */
public class StudySettings {
	private static final String CONFIG_FILE = "study.cfg";
	private File directory;
	private DISPLAY_MODE_VALUE displayMode;

	private int lastImageIndex;

	/**
	 * Initializes a StudySettings object with a given directory so that
	 * settings for a study can be properly initialized
	 * @param directory - the directory the study is located in.
	 */
	public StudySettings(File directory) {
		this.directory = directory;
		Scanner sc = null;
		if(directory != null) {
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
	}
	
	/**
	 * Gets the index of the last image that was being viewed.
	 * @return the index of the last image that was being viewed.
	 */
	public int getLastImageIndex() {
		return this.lastImageIndex;
	}

	/**
	 * Gets the stored display mode in settings
	 * @return the stored display mode in settings
	 */
	public DISPLAY_MODE_VALUE getDisplayMode() {
		return displayMode;
	}
	
	/**
	 * Sets the display mode in settings
	 * @param newDisplayMode the new display mode
	 */
	public void setDisplayMode(DISPLAY_MODE_VALUE newDisplayMode) {
		this.displayMode = newDisplayMode;
		writeInformationToDisk(this.displayMode, this.lastImageIndex);
	}
	
	/**
	 * Sets the index of the last image to be viewed.
	 * @param newIndex the index of the last image to be viewed.
	 */
	public void setLastImageIndex(int newIndex) {
		this.lastImageIndex = newIndex;
		writeInformationToDisk(this.displayMode, this.lastImageIndex);
	}
	
	/**
	 * writes a new CFG file for a study.
	 * 
	 * @param mode - the study's display mode
	 * @param index - the study's current image index
	 */
	private void writeInformationToDisk(DISPLAY_MODE_VALUE mode, int index) {
		if(this.directory != null) { 
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
}
