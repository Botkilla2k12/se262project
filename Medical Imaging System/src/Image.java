/**
 * @author Derek Leung
 */
import java.awt.image.*;
import java.io.File;

/**
 * @author derek
 *
 */
public class Image {
	private File fileName;
	
	/**
	 * Creates an Image from a given filename
	 * @param fileName the file to create the image from
	 */
	public Image(File fileName) {
		// TODO Auto-generated constructor stub
		this.fileName = fileName;
	}

	public String toString() {
		return fileName.toString();
	}
}
