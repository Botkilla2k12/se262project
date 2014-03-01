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

	private Study study;
	private ArrayList<BufferedImage> images;
	private DISPLAY_MODE_VALUE displayMode;
	private int index;
	
	public BrowseCommand(Study study) {
		this.study = study;
		this.images = study.getImages();
		this.index = study.getIndex();
		this.displayMode = study.getStudySettings().getDisplayMode();
	}
	
	public BufferedImage first() {
		return images.get(0);
	}
	
	public boolean isEnd() { //moving to right
		if (displayMode == DISPLAY_MODE_VALUE.ONE_IMAGE) {
			return (index >= images.size() - 1) ;
		}
		else {
			return (index + 4 >= images.size());
		}
	}
	
	public boolean isBeginning() { //moving to left
		if (displayMode == DISPLAY_MODE_VALUE.ONE_IMAGE) {
			return (index <= 0);
		}
		else {
			return (index - 4 <= 0);
		}
	}
	
	public BufferedImage currentItem() {
		return images.get(index);
	}
	
	public BufferedImage next() throws IndexOutOfBoundsException { //moving to right
		if (displayMode == DISPLAY_MODE_VALUE.ONE_IMAGE && !isEnd()) {
			BufferedImage newImage = images.get(index + 1);
			study.setIndex(++index);
			return newImage;
		}
		else if (displayMode == DISPLAY_MODE_VALUE.FOUR_IMAGE && !isEnd()) {
			BufferedImage newImage = images.get(index + 4);
			study.setIndex(index += 4);
			return newImage;
		}
		throw new IndexOutOfBoundsException();
	}
	
	public BufferedImage prev() throws IndexOutOfBoundsException { //moving to left
		if (displayMode == DISPLAY_MODE_VALUE.ONE_IMAGE && !isBeginning()) {
			BufferedImage newImage = images.get(index - 1);
			study.setIndex(--index);
			return newImage;
		}
		else if (displayMode == DISPLAY_MODE_VALUE.FOUR_IMAGE && !isBeginning()) {
			BufferedImage newImage = images.get(index - 4);
			study.setIndex(index -= 4);
			return newImage;
		}
		throw new IndexOutOfBoundsException();
	}
	
}
