import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * OpenCommand.java
 *
 *@author Matthew Witte
 *
 *Version:
 *$Id$
 *
 *Revisions:
 *$Log$
 */

public class OpenCommand {

	private File directory;
	private ArrayList<BufferedImage> images;
	
	private OpenCommand(File directory) throws IOException {
		this.directory = directory;
		open(directory);
	}
	
	public File getDirectory() {
		return directory;
	}
	
	private ArrayList<BufferedImage> open(File directory) throws IOException {
		File[] files = directory.listFiles();
		
		for(int i = 0; i < files.length; i++){
			try{
				BufferedImage image = ImageIO.read(files[i]);
				images.add(image);
		    }
		    catch(IOException e) {
		    	throw e;
		    }
		}
		
		return images;
	}
	
	public ArrayList<BufferedImage> getImages() {
		return images;
	}
}
