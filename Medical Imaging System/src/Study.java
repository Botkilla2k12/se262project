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
	
	/**
	 * Creates a Study using a given directory.
	 * @param directory the directory containing the study.
	 */
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
	
	/**
	 * Uses the inner Memento class to restore the Study object back to a 
	 * previous state based on the data in the memento Object
	 */
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
	
	/**
	 * This method creates a Memento Object containing the Study's current
	 * state.
	 * @return The Study's current state.
	 */
	public Memento saveToMemento() {
		return new Memento(
			this.studySettings.getDisplayMode(),
			this.images
		);
	}
	
	/**
	 * This class encapsulates the state data "snapshot" using Images and 
	 * Display Mode information
	 * @author Curtis
	 */
	public static class Memento {
		private ArrayList<Image> images;
		private DisplayMode displayMode;
		
		/**
		 * Creates a Memento Snapshot using a list of Images and a DisplayMode
		 * @param displayMode the current DisplayMode to be remembered
		 * @param images the Current Images being displayed in the Study
		 */
		public Memento(DisplayMode displayMode, ArrayList<Image> images) {
			this.displayMode = displayMode;
			this.images = images;
		}
		
		/**
		 * Gets the images contained in this Memento Snapshot
		 * @return A list of the images contained in this Memento Snapshot
		 */
		public ArrayList<Image> getImages() {
			return this.images;
		}
		
		/**
		 * Gets the Display Mode Stored
		 * @return
		 */
		public DisplayMode getDisplayMode() {
			return this.displayMode;
		}
	}

	/**
	 * Sets the images that this study contains
	 * @param images the set of new images to replace the current set.
	 */
	public void setImages(ArrayList<Image> images) {
		this.previousModes.push(this.saveToMemento());
		
		this.images = images;
		
		super.setChanged();
		super.notifyObservers();
	}
	
	/**
	 * Gets the saved state of the Study
	 * @return true if the study has been saved, false otherwise.
	 */
	public boolean getSaved() {
		return this.saved;
	}
	
	/**
	 * Sets the saved state of the study
	 * @param newSaved the new saved state of the study: true if the study has 
	 * been saved, false otherwise.
	 */
	public void setSaved(boolean newSaved) {
		this.saved = newSaved;
	}
	
	/**
	 * Clears the undo stack of all previous memento snapshots.
	 * Use this method with caution
	 */
	public void clearAllSavedStates() {
		this.previousModes.removeAllElements();
	}
}
