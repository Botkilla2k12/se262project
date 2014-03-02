import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;

/**
 * @author Derek Leung
 *
 */
public class Study extends Observable {
	private ArrayList<BufferedImage> images;
	private File directory; 
	private StudySettings studySettings;
	private int index;
	
	
	public Study(File directory) {
		this.index = 0;
		this.directory = directory;
		this.studySettings = new StudySettings(directory);
	}
	
	/**
	 * Creates an OpenCommand to open the directory. Open command returns an
	 * ArrayList of images. The study's images are then updated.
	 * 
	 * @throws IOException
	 */
	public void open() throws IOException {
		if(this.directory != null) {
			try{
				OpenCommand openCommandObject = new OpenCommand(this.directory);
				this.images = openCommandObject.getImages();
				
				super.setChanged();
				super.notifyObservers();
			} catch (IOException e){
				throw e;
			}
		}
	}
	
	public File getDirectory(){
		return this.directory;
	}
	
	public String toString(){
		return this.directory.getAbsolutePath();
	}
	
	public ArrayList<BufferedImage> getImages(){
		return this.images;
	}
	
	public ArrayList<BufferedImage> getCurrentImages() {
		ArrayList<BufferedImage> currImgs = new ArrayList<BufferedImage>();
		int offset =
			this.studySettings.getDisplayMode() == DISPLAY_MODE_VALUE.ONE_IMAGE ?
			1 : 4;
		
		for(int i = index; i < this.images.size() && i < index + offset; i++) {
			currImgs.add(this.images.get(i));
		}
		
		return currImgs;
	}
	
	public int getIndex(){
		return this.index;
	}
	
	public void setIndex(int newIndex){
		this.index = newIndex;
		
		super.setChanged();
		super.notifyObservers();
	}
	
	public void setDisplayMode(DISPLAY_MODE_VALUE mode) {
		this.studySettings.setDisplayMode(mode);
		
		super.setChanged();
		super.notifyObservers();
	}
	
	public StudySettings getStudySettings() {
		return this.studySettings;
	}

	public static class Metadata {
		public String changeType;
		
		public Metadata(String changeType) {
			this.changeType = changeType;
		}
	}
}
