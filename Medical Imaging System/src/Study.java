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
	private StudySettings studySettings;
	private int index;
	
	public Study(File directory) {
		// TODO Auto-generated constructor stub
		this.index = 0;
		this.directory = directory;
		this.studySettings = new StudySettings();
	}
	
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
	
	public File getDirectory(){
		return this.directory;
	}
	
	public String toString(){
		return this.toString();
	}
	
	public ArrayList<BufferedImage> getImages(){
		return this.images;
	}

}
