import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public interface StudyComposite {
	public void open() throws IOException;
	public File getDirectory();
	public ArrayList<Object> getImages();
	public ArrayList<Object> getCurrentImages();
	public int getIndex();
	public int getImageWidth();
	public int getImageHeight();
	public void setIndex(int integer);
	public void setDisplayMode(DISPLAY_MODE_VALUE d);
	public StudySettings getStudySettings();
	
}
