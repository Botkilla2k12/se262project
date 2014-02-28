/**
 * 
 * @author derek
 *
 */
public class Settings {
	public static final int ONE_IMAGE = 1, FOUR_IMAGES = 4;
	private int displayMode;
	private Study defaultStudy;
	
	public int getDisplayMode() {
		return displayMode;
	}
	
	public void setDefaultStudy(Study study) {
		this.defaultStudy = study;
	}
	
	public void setDisplayMode(int newDisplayMode) {
		this.displayMode = newDisplayMode;
	}
}
