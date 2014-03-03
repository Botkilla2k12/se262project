import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;

/**
 * @author Derek Leung
 *
 * Concrete model of a Study.
 */


public class Study extends Observable {
	private ArrayList<BufferedImage> images;
	private File directory; 
	private StudySettings studySettings;
	private int index, defaultImageHeight, defaultImageWidth;
	
	/**
	 * Default Constructor.
	 * 
	 * @param directory
	 */
	public Study(File directory) {
		this.studySettings = new StudySettings(directory);
		
		this.index = this.studySettings.getLastImageIndex();
		this.defaultImageHeight = 0;
		this.defaultImageWidth = 0;
		
		this.directory = directory;
		
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
	
	/**
	 * returns the Study's current directory
	 * 
	 * @return
	 */
	public File getDirectory(){
		return this.directory;
	}
	
	/**
	 * Override toString() to return the absolute file path of the Study
	 * 
	 * @return
	 */
	public String toString(){
		return this.directory == null ?  "null" :
			this.directory.getAbsolutePath();
	}
	
	/**
	 * returns this Study's images as an ArrayList of buffered images
	 * 
	 * @return
	 */
	public ArrayList<BufferedImage> getImages(){
		return this.images;
	}
	
	/**
	 * Returns the images for the current index based on display mode.
	 * 
	 * @return
	 */
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
	
	/**
	 * returns the current index of the study
	 * 
	 * @return
	 */
	public int getIndex(){
		return this.index;
	}
	
	/**
	 * returns the image width
	 * 
	 * @return
	 */
	public int getImageWidth() {
		return this.defaultImageWidth;
	}
	
	/**
	 * returns the image height
	 * 
	 * @return
	 */
	public int getImageHeight() {
		return this.defaultImageHeight;
	}
	
	/**
	 * sets the Study's index to the given index
	 * 
	 * @param newIndex
	 */
	public void setIndex(int newIndex){
		this.index = newIndex;
		
		this.studySettings.setLastImageIndex(newIndex);
		
		super.setChanged();
		super.notifyObservers();
	}
	
	/**
	 *Sets the study's display mode as the given display mode
	 * 
	 * @param mode
	 */
	public void setDisplayMode(DISPLAY_MODE_VALUE mode) {
		DISPLAY_MODE_VALUE currMode = studySettings.getDisplayMode();
		this.studySettings.setDisplayMode(mode);
		
		if(currMode == DISPLAY_MODE_VALUE.ONE_IMAGE &&
			mode == DISPLAY_MODE_VALUE.FOUR_IMAGE
		) {
			this.setIndex((4 * (int)Math.floor((this.index)/4)));
			System.out.println((4 * (int)Math.floor((this.index)/4)));
		} else if (currMode == DISPLAY_MODE_VALUE.FOUR_IMAGE &&
			mode == DISPLAY_MODE_VALUE.ONE_IMAGE
		) {
			this.setIndex((4 * (int)Math.floor((this.index)/4)));
			System.out.println((4 * (int)Math.floor((this.index)/4)));
		}
		else {
			super.setChanged();
			super.notifyObservers();
		}
	}
	
	/**
	 * returns this study's study settings
	 * 
	 * @return
	 */
	public StudySettings getStudySettings() {
		return this.studySettings;
	}
}
