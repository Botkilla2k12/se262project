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
	private int displayMode;
	
	public BrowseCommand(boolean direction, Study study) {
		this.direction = direction;
		this.study = study;
		this.images = study.getImages();
		this.displayMode = study.studySettings.displayMode;
	}
	
	public BufferedImage first() {
		return images.get(0);
	}
	
	public boolean isDone() {
		return (study.index == images.size() - 1);
	}
	
	public BufferedImage currentItem() {
		return images.get(index);
	}
	
	public BufferedImage next() {
		if (displayMode == ONE_IMAGE) {
			if (direction) {
				BufferedImage newImage = images.get(index +1);
				index++;
				return newImage;
			}
			else {
				BufferedImage newImage = images.get(index - 1);
				index--;
				return newImage;
			}
		}
		elif (displayMode == FOUR_IMAGES) {
			if (direction) {
				BufferedImage newImage = images.get(index + 4);
				index += 4;
				return newImage;
			}
			else {
				BufferedImage newImage = images.get(index - 4);
				index -= 4;
				return newImage;
			}
		}
	}
	
}
