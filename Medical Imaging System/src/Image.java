/**
 * 
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
	 * 
	 */
	public Image(File fileName) {
		// TODO Auto-generated constructor stub
		this.fileName = fileName;
	}

	public String toString() {
		return fileName.toString();
	}
}