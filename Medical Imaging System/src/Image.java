/**
 * @author Derek Leung
 */
import java.awt.image.*;
import java.io.File;
import java.util.Observable;

/**
 * @author derek
 *
 */
<<<<<<< HEAD
public class Image extends Observable implements StudyComposite{
	private File fileName;
=======
public class Image {
	private String name;
	private BufferedImage image;
>>>>>>> branch 'master' of https://github.com/Botkilla2k12/se262project.git
	
	/**
	 * Creates an Image from a given filename
	 * @param fileName the file to create the image from
	 */
	public Image(String name, BufferedImage image) {
		this.name = name;
		this.image = image;
	}

	public String getName() {
		return this.name;
	}
	
	public BufferedImage getImage() {
		return this.image;
	}
}
