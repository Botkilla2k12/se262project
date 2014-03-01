import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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
	
	public OpenCommand(File directory) throws IOException {
		this.directory = directory;
		this.images = new ArrayList<BufferedImage>();
		open(directory);
	}
	
	public File getDirectory() {
		return directory;
	}
	
	public boolean checkFileType(File f) {
		String name = f.getName().toLowerCase();
        return (name.endsWith(".jpg") || name.endsWith(".jpeg"));
	}
	
	private ArrayList<BufferedImage> open(File directory) throws IOException {
		File[] files = directory.listFiles();
		ArrayList<File> newFiles = new ArrayList<File>();
		for (int i = 0; i < files.length; i++) {
			if (checkFileType(files[i])) {
				newFiles.add(files[i]);
			}
		}
		Collections.sort(newFiles);
		
		for(int i = 0; i < newFiles.size(); i++){
			try{
				BufferedImage image = ImageIO.read(newFiles.get(i));
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
