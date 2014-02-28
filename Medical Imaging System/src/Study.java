import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;

/**
 * @author derek
 *
 */
public class Study extends Observable {
	private ArrayList<BufferedImage> images;
	private File directory; 
	
	/*
	public Study(ArrayList<Image> Images) {
		// TODO Auto-generated constructor stub
		this.images = images;
	}*/
	
	public ArrayList<BufferedImage> open() {
		OpenCommand openCommandObject = new OpenCommand(this.directory);
		this.images = openCommandObject.getImages();
	}
	
	public void sort() {
		sortCommandObject = new SortCommand(this.images);
	}
}
