import java.util.ArrayList;

//TODO: Make browse command's index dependent on Study instead keeping it's
// own index

/**
 * BrowseCommand.java
 *
 *@author Matthew Witte
 *
 *Version:
 *$Id$
 *
 *Revisions:
 *$Log$
 */

public class StudyIterator {

	private Study study;
	private ArrayList<Image> images;
	private DisplayMode displayMode;
	private int index;
	
	/**
	 * Initializes a BrowseCommand object with the given study
	 * @param study - the current study the user is looking at
	 */
	public StudyIterator(Study study) {
		this.study = study;
		this.images = study.getImages();
		this.index = study.getIndex();
		this.displayMode = study.getStudySettings().getDisplayMode();
	}
	
	/**
	 * Gets the first image of the study as a Image
	 * @return the first image of the study as a Image
	 */
	public Image first() {
		if (images.get(0) instanceof Image){
			return (Image)images.get(0);
		} else {
			try {
				throw new TypeException();
			} catch (TypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
	}
	
	/**
	 * Checks if the user can't view anymore images moving to the right
	 * @return true if the user can't view anymore images to the right,
	 * 	false if they can
	 */
	public boolean isEnd() { //moving to right
		if (displayMode == DisplayMode.ONE_IMAGE) {
			return (index >= images.size() - 1) ;
		}
		else {
			return (index + 4 > images.size());
		}
	}
	
	/**
	 * Checks if the user can't view anymore images moving to the left
	 * @return true if the user can't view anymore images to the left,
	 * 	false if they can
	 */
	public boolean isBeginning() { //moving to left
		if (displayMode == DisplayMode.ONE_IMAGE) {
			return (index <= 0);
		}
		else {
			return (index - 4 < 0);
		}
	}
	
	/**
	 * Gets the image at the current index
	 * @return the image at the current index
	 */
	public Image currentItem() {
		if (images.get(index) instanceof Image){
			return (Image)images.get(index);
		} else {
			try {
				throw new TypeException();
			} catch (TypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
	}
	
	/**
	 * Moves to the right one or four images, depending on the display
	 * mode, and after checking that there are more images to be shown
	 * @return the image at the updated index
	 * @throws IndexOutOfBoundsException
	 */
	public Image next() throws IndexOutOfBoundsException { //moving to right
		if (displayMode == DisplayMode.ONE_IMAGE && !isEnd()) {
			if(images.get(index + 1) instanceof Image){
				this.index = study.getIndex();
				Image newImage = (Image)images.get(index + 1);
				study.setIndex(++index);
				return newImage;
			} else {
				try {
					throw new TypeException();
				} catch (TypeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
		}
		else if (displayMode == DisplayMode.FOUR_IMAGE && !isEnd()) {
			if (images.get(index + 4) instanceof Image){
				this.index = study.getIndex();
				Image newImage = (Image)images.get(index + 4);
				study.setIndex(index += 4);
				return newImage;
			} else {
				try {
					throw new TypeException();
				} catch (TypeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
		}
		throw new IndexOutOfBoundsException();
	}
	
	/**
	 * Moves to the left one or four images, depending on the display
	 * mode, and after checking that there are more images to be shown
	 * @return the image at the updated index
	 * @throws IndexOutOfBoundsException
	 */
	public Image prev() throws IndexOutOfBoundsException { //moving to left
		if (displayMode == DisplayMode.ONE_IMAGE && !isBeginning()) {
			if (images.get(index - 1) instanceof Image){
				this.index = study.getIndex();
				Image newImage = (Image)images.get(index - 1);
				study.setIndex(--index);
				return newImage;
			} else {
				try {
					throw new TypeException();
				} catch (TypeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
		}
		else if (displayMode == DisplayMode.FOUR_IMAGE && !isBeginning()) {
			if (images.get(index -4) instanceof Image){
				this.index = study.getIndex();
				Image newImage = (Image)images.get(index - 4);
				study.setIndex(index -= 4);
				return newImage;
			} else {
				try {
					throw new TypeException();
				} catch (TypeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		
		}
		throw new IndexOutOfBoundsException();
	}
	
	/**
	 * Sets the display mode that the user is viewing in
	 * @param mode - the display mode that the user is viewing in
	 */
	public void setDisplayMode(DisplayMode mode) {
		this.displayMode = mode;
	}
}
