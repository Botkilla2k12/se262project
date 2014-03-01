import java.awt.image.BufferedImage;
import java.util.ArrayList;

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

public class BrowseCommand {

	private boolean direction; //true = right
	private Study study;
	private ArrayList<BufferedImage> images;
	private DISPLAY_MODE_VALUE displayMode;
	private int index;
	
	public BrowseCommand(boolean direction, Study study) {
		this.direction = direction;
		this.study = study;
		this.index = study.getIndex();
		this.images = study.getImages();
		this.displayMode = study.getStudySettings().getDisplayMode();
	}
	
	public BufferedImage first() {
		return images.get(0);
	}
	
	public boolean isDone() {
		if (displayMode == DISPLAY_MODE_VALUE.ONE_IMAGE) {
			if (direction) { //moving to right by one image
				return (index >= images.size() - 1) ;
			}
			else { //moving to left by one image
				return (index <= 0);
			}
		}
		else {
			if (direction) { //moving to right by four images
				return (index + 4 >= images.size());
			}
			else { //moving to left by four images
				return (index - 4 <= 0);
			}
		}
	}
	
	public BufferedImage currentItems() {		
		return images.get(index);
	}
	
	public BufferedImage next() throws IndexOutOfBoundsException{
		if (displayMode == DISPLAY_MODE_VALUE.ONE_IMAGE) {
			if (direction && !isDone()) { //moving to right by one image
				BufferedImage newImage = images.get(index + 1);
				study.setIndex(index + 1);
				index++;
				
				return newImage;
			}
			else if (!direction && !isDone()){ //moving to left by one image
				BufferedImage newImage = images.get(index - 1);
				study.setIndex(index - 1);
				index--;
				
				return newImage;
			}
		}
		else {
			if (direction && !isDone()) { //moving to right by four images
				BufferedImage newImage = images.get(index + 4);
				study.setIndex(index + 4);
				index += 4;
				
				return newImage;
			}
			else if (!direction && !isDone()) { //moving to left by four images
				BufferedImage newImage = images.get(index - 4);
				study.setIndex(index - 4);
				index -= 4;
				
				return newImage;
			}
		}
		throw new IndexOutOfBoundsException();
	}
	
}
