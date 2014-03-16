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

public class Image extends Observable implements StudyComposite{

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

	@Override
	public String toString() {
		return this.name;
	}
	
	@Override
	public ArrayList<Object> getImages() {
		ArrayList<Object> images = new ArrayList<Object>();
		if (this.image instanceof BufferedImage){
			images.add(this.image);
		} 
		return images;
	}
	
	@Override
	public void open(){
		
	}
	
	@Override
	public File getDirectory(){
		return new File(this.name);
	}

	@Override
	public ArrayList<Object> getCurrentImages() {
		return this.getImages();
	}
	
	@Override
	public int getIndex(){
		return -1;
	}
	
	@Override
	public int getImageWidth(){
		return this.image.getHeight();
	}
	
	@Override
	public int getImageHeight(){
		return this.image.getWidth();
	}
	
	@Override
	public void setIndex(int integer){
	}
	
	@Override
	public void setDisplayMode(DISPLAY_MODE_VALUE d){
		
	}
	
	@Override
	public StudySettings getStudySettings(){
		return null;
	}
<<<<<<< HEAD

	@Override
	public void setImages(ArrayList<Object> images) {
		// TODO Auto-generated method stub
		
	}
=======
>>>>>>> branch 'master' of https://github.com/Botkilla2k12/se262project.git
}
