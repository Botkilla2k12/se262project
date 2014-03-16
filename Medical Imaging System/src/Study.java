import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.ArrayList;

/**
 * @author Derek Leung
 *
 */

public class Study extends Observable implements StudyComposite {

	private ArrayList<Object> images;

	private File directory; 
	private StudySettings studySettings;
	private int index, defaultImageHeight, defaultImageWidth;
	
	
	public Study(File directory) {
		this.studySettings = new StudySettings(directory);
		
		this.index = this.studySettings.getLastImageIndex();
		this.defaultImageHeight = 0;
		this.defaultImageWidth = 0;
		
		this.directory = directory;
		
		this.images = new ArrayList<Object>();
	}
	
	/**
	 * Creates an OpenCommand to open the directory. Open command returns an
	 * ArrayList of images. The study's images are then updated.
	 * 
	 * @throws IOException
	 */
	@Override
	public void open() throws IOException {
		if(this.directory != null) {
			try{
				OpenCommand openCommandObject = new OpenCommand(this.directory);
				this.images = openCommandObject.getImages();
				
				if(this.images.size() > 0) {
					BufferedImage firstImage = (BufferedImage) (((Image) images.get(0)).getImages().get(0));
					this.defaultImageHeight = firstImage.getHeight();
					this.defaultImageWidth = firstImage.getWidth();
				}
				
				super.setChanged();
				super.notifyObservers();
			} catch (IOException e){
				throw e;
			}
		}
	}
	
	/**
	 * Gets the directory the study is located in
	 * @return the directory location of the study
	 */
	@Override
	public File getDirectory(){
		return this.directory;
	}
	
	/**
	 * @return a string representation of a study (the absolute path of the
	 * study's directory)
	 */
	@Override
	public String toString(){
		return this.directory == null ?  "null" :
			this.directory.getAbsolutePath();
	}
	
	/**
	 * Gets all images stored in the study
	 * @return a list of all images stored in the study
	 */
	@Override
	public ArrayList<Object> getImages(){
		return this.images;
	}
	
	/**
	 * Gets the current images based on where the current index is as well as
	 * the display mode of the study
	 * @return the current images for the study
	 */
	@Override
	public ArrayList<Object> getCurrentImages() {
		ArrayList<Object> currImgs = new ArrayList<Object>();
		int offset =
			this.studySettings.getDisplayMode() == DISPLAY_MODE_VALUE.ONE_IMAGE ?
			1 : 4;
		
		for(int i = index; i < this.images.size() && i < index + offset; i++) {
			currImgs.add(this.images.get(i));
		}
		
		return currImgs;
	}
	
	/**
	 * And index representing the currently viewed image(s) in the study
	 * @return
	 */
	@Override
	public int getIndex(){
		return this.index;
	}
	
	/**
	 * Gets the width of one of the studies' images
	 * @return the standard image width of the study
	 */
	@Override
	public int getImageWidth() {
		return this.defaultImageWidth;
	}
	
	/**
	 * Gets the height of one of the studies' images
	 * @return the standard image height of the study
	 */
	@Override
	public int getImageHeight() {
		return this.defaultImageHeight;
	}
	
	/**
	 * Gets the settings associated with this study
	 * @return The StudySettings object associated with the study's settings.
	 */
	@Override
	public StudySettings getStudySettings() {
		return this.studySettings;
	}
	
	/**
	 * Sets the index cursor of the study
	 * Calling this method notifies observers
	 * @param newIndex the new image position in the study
	 */
	@Override
	public void setIndex(int newIndex){
		this.index = newIndex;
		
		this.studySettings.setLastImageIndex(newIndex);
		
		super.setChanged();
		super.notifyObservers();
	}
	
	/**
	 * Changes the display mode of the study
	 * @param mode the new display mode for the study
	 */
	@Override
	public void setDisplayMode(DISPLAY_MODE_VALUE mode) {
		DISPLAY_MODE_VALUE currMode = studySettings.getDisplayMode();
		this.studySettings.setDisplayMode(mode);
		
		if(currMode == DISPLAY_MODE_VALUE.ONE_IMAGE &&
			mode == DISPLAY_MODE_VALUE.FOUR_IMAGE
		) {
			this.setIndex((4 * (int)Math.floor((this.index)/4)));
		} else if (currMode == DISPLAY_MODE_VALUE.FOUR_IMAGE &&
			mode == DISPLAY_MODE_VALUE.ONE_IMAGE
		) {
			this.setIndex((4 * (int)Math.floor((this.index)/4)));
		}

		super.setChanged();
		super.notifyObservers();
	}
	
	public void restoreFromMemento(Memento memento) {
		this.setDisplayMode(memento.getDisplayMode());
	}
	
	public Memento saveToMemento() {
		return new Memento(this.studySettings.getDisplayMode());
	}
	
	public static class Memento {
		private DISPLAY_MODE_VALUE displayMode;
		
		public Memento(DISPLAY_MODE_VALUE displayMode) {
			this.displayMode = displayMode;
		}
		
		public DISPLAY_MODE_VALUE getDisplayMode() {
			return this.displayMode;
		}
	}

	@Override
	public void setImages(ArrayList<Object> images) {
		// TODO Auto-generated method stub
		if (images.get(0) instanceof Image){
			this.images = images;
		} else {
			try {
				throw new TypeException();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
