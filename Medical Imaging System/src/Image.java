/**
 * @author Derek Leung
 */
import java.awt.image.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;

/**
 * @author derek
 *
 */

public class Image extends Observable {

	private String name;
	private BufferedImage image;

	
	/**
	 * Creates an Image from a given filename
	 * @param fileName the file to create the image from
	 */
	public Image(String name, BufferedImage image) {
		this.name = name;
		this.image = image;
	}

	public String toString() {
		return this.name;
	}
	
	public ArrayList<Object> getImages() {
		ArrayList<Object> images = new ArrayList<Object>();
		if (this.image instanceof BufferedImage){
			images.add(this.image);
		} 
		return images;
	}
	
	public void open(){
		
	}
	
	public File getDirectory(){
		return new File(this.name);
	}

	public ArrayList<Object> getCurrentImages() {
		return this.getImages();
	}
	
	public int getIndex(){
		return -1;
	}
	
	public int getImageWidth(){
		return this.image.getHeight();
	}
	
	public int getImageHeight(){
		return this.image.getWidth();
	}
	
	public void setIndex(int integer){
	}
	
	public void setDisplayMode(DisplayMode d){
		
	}
	
	public SystemSettings getStudySettings(){
		return null;
	}

	public void setImages(ArrayList<Object> images) {
		// TODO Auto-generated method stub
	}
}
