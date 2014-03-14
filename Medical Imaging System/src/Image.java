import java.awt.image.*;

/**
 * @author derek
 *
 */
public class Image {
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
	
	public String getName() {
		return this.name;
	}
	
	public BufferedImage getImage() {
		return this.image;
	}
}
