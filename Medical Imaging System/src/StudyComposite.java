import java.io.File;
import java.util.ArrayList;


public interface StudyComposite {
	public void open();
	public File getDirectory();
	public ArrayList<Image> getImages();
	public ArrayList<Image> getCurrentImages();
	public int getIndex();
	public int getImageWidth();
	public int getImageHeight();
	public void setIndex(int integer);
	public void setDisplayMode(DISPLAY_MODE_VALUE d);
	public StudySettings getStudySettings();
	
}
