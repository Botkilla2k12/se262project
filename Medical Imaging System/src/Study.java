import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Observable;
import java.util.Stack;

import javax.swing.JOptionPane;

/**
 * @author Derek Leung
 *
 */

public class Study extends Observable {

	private ArrayList<Image> images;
	private Stack<Memento> previousModes;

	private File directory; 
	private StudySettings studySettings;
	private int index, defaultImageHeight, defaultImageWidth;
	private boolean saved;
	
	
	public Study(File directory) {
		this.studySettings = new StudySettings(directory);
		
		this.previousModes = new Stack<Memento>();
		
		this.index = this.studySettings.getLastImageIndex();
		this.defaultImageHeight = 0;
		this.defaultImageWidth = 0;
		
		this.directory = directory;
		
		this.images = new ArrayList<Image>();
		this.saved = true;
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

	public File getDirectory(){
		return this.directory;
	}
	
	/**
	 * @return a string representation of a study (the absolute path of the
	 * study's directory)
	 */
	public String toString(){
		return this.directory == null ?  "null" :
			this.directory.getAbsolutePath();
	}
	
	/**
	 * Gets all images stored in the study
	 * @return a list of all images stored in the study
	 */
	public ArrayList<Image> getImages(){
		return this.images;
	}
	
	/**
	 * Gets the current images based on where the current index is as well as
	 * the display mode of the study
	 * @return the current images for the study
	 */
	public ArrayList<Object> getCurrentImages() {
		ArrayList<Object> currImgs = new ArrayList<Object>();
		int offset =
			this.studySettings.getDisplayMode() == DisplayMode.ONE_IMAGE ?
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

	public int getIndex(){
		return this.index;
	}
	
	/**
	 * Gets the width of one of the studies' images
	 * @return the standard image width of the study
	 */

	public int getImageWidth() {
		return this.defaultImageWidth;
	}
	
	/**
	 * Gets the height of one of the studies' images
	 * @return the standard image height of the study
	 */
	public int getImageHeight() {
		return this.defaultImageHeight;
	}
	
	/**
	 * Gets the settings associated with this study
	 * @return The StudySettings object associated with the study's settings.
	 */
	public StudySettings getStudySettings() {
		return this.studySettings;
	}
	
	/**
	 * Sets the index cursor of the study
	 * Calling this method notifies observers
	 * @param newIndex the new image position in the study
	 */
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
	public void setDisplayMode(DisplayMode mode) {
		DisplayMode currMode = studySettings.getDisplayMode();
		
		this.previousModes.push(this.saveToMemento());
		
		this.studySettings.setDisplayMode(mode);
		
		
		
		if(currMode == DisplayMode.ONE_IMAGE &&
			mode == DisplayMode.FOUR_IMAGE
		) {
			this.setIndex((4 * (int)Math.floor((this.index)/4)));
		} else if (currMode == DisplayMode.FOUR_IMAGE &&
			mode == DisplayMode.ONE_IMAGE
		) {
			this.setIndex((4 * (int)Math.floor((this.index)/4)));
		}

		super.setChanged();
		super.notifyObservers();
	}
	
	public void restoreFromMemento() {		
		try {
			Study.Memento memento = this.previousModes.pop();
			
			System.out.println(this.previousModes.size());
			
			this.studySettings.setDisplayMode(memento.getDisplayMode());
			this.images = memento.getImages();
			
			super.setChanged();
			super.notifyObservers();
		} catch (EmptyStackException e) {
			JOptionPane.showMessageDialog(null, "All states have been undone");
		}
	}
	
	public Memento saveToMemento() {
		return new Memento(
			this.studySettings.getDisplayMode(),
			this.images
		);
	}
	
	public static class Memento {
		private ArrayList<Image> images;
		private DisplayMode displayMode;
		
		public Memento(DisplayMode displayMode, ArrayList<Image> images) {
			this.displayMode = displayMode;
			this.images = images;
		}
		
		public ArrayList<Image> getImages() {
			return this.images;
		}
		
		public DisplayMode getDisplayMode() {
			return this.displayMode;
		}
	}

	public void setImages(ArrayList<Image> images) {
		this.previousModes.push(this.saveToMemento());
		
		this.images = images;
		
		super.setChanged();
		super.notifyObservers();
	}
	
	public boolean getSaved() {
		return this.saved;
	}
	
	public void setSaved(boolean newSaved) {
		this.saved = newSaved;
	}
	
	public void clearAllSavedStates() {
		this.previousModes.removeAllElements();
	}
}
