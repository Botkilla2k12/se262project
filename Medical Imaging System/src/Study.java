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
	private int index, defaultImageHeight, defaultImageWidth;
	
	
	public Study(File directory) {
		this.index = 0;
		this.defaultImageHeight = 0;
		this.defaultImageWidth = 0;
		
		this.directory = directory;
		this.studySettings = new StudySettings(directory);
		this.images = new ArrayList<BufferedImage>();
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
				
				if(this.images.size() > 0) {
					this.defaultImageHeight = images.get(0).getHeight();
					this.defaultImageWidth = images.get(0).getWidth();
				}
				
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
		return this.directory == null ?  "null" :
			this.directory.getAbsolutePath();
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
	
	public int getImageWidth() {
		return this.defaultImageWidth;
	}
	
	public int getImageHeight() {
		return this.defaultImageHeight;
	}
	
	public void setIndex(int newIndex){
		this.index = newIndex;
		
		super.setChanged();
		super.notifyObservers();
	}
	
	public void setDisplayMode(DISPLAY_MODE_VALUE mode) {
		this.studySettings.setDisplayMode(mode);
		
		if(mode == DISPLAY_MODE_VALUE.FOUR_IMAGE) {
			this.setIndex(4 * (int)Math.floor((this.index - 1)/4) + 1);
		} else {
			super.setChanged();
			super.notifyObservers();
		}
	}
	
	public StudySettings getStudySettings() {
		return this.studySettings;
	}
}
