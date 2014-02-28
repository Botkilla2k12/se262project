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
	
	/*
	public Study(ArrayList<Image> Images) {
		// TODO Auto-generated constructor stub
		this.images = images;
	}*/
	
	/**
	 * Creates an OpenCommand to open the directory. Open command returns an
	 * ArrayList of images. The study's images are then updated.
	 * 
	 * @throws IOException
	 */
	public void open() throws IOException {
		try{
			OpenCommand openCommandObject = new OpenCommand(this.directory);
			this.images = openCommandObject.getImages();
		} catch (IOException e){
			throw e;
		}
		
		
	}
	
}
