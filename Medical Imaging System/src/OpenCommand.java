import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


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

public class OpenCommand implements Command{

	private File directory;
	private ArrayList<Object> images;
	private Study study;
	/**
	 * Initializes an OpenCommand object with a given directory so all
	 * JPEG files in the study can be opened for viewing
	 * @param directory - directory the study is located in
	 * @throws IOException
	 */
	public OpenCommand(Study study) throws IOException {
		this.study = study;
		this.directory = study.getDirectory();
		this.images = new ArrayList<Object>();
		open(directory);
	}
	
	/**
	 * Gets the directory of the study being opened
	 * @return the directory of the study being opened
	 */
	/*
	public File getDirectory() {
		return directory;
	}*/
	
	/**
	 * Gets all files in the given directory, weeds out the .jpg and .jpeg
	 * files, and puts them in a list. Sorts the files by name then opens each
	 * file as a BufferedImage and puts in an ArrayList that it returns.
	 * @param directory - directory in which to open all .jpg and .jpeg files
	 * @return ArrayList of BufferedImage for each .jpg/.jpeg file
	 * @throws IOException
	 */
	private void open(File directory) throws IOException {
		File[] files = directory.listFiles();
		Arrays.sort(files);
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			String name = file.getName().toLowerCase();
			if (name.endsWith(".jpg") || name.endsWith(".jpeg")) {
				try {
					BufferedImage image = ImageIO.read(file);
					Image newImage = new Image(file.getName(), image);
					images.add(newImage);
			    }
			    catch(IOException e) {
			    	throw e;
			    }
			}
			else if (name.endsWith(".acr")) {
				ShowACR acr = new ShowACR(file);
				BufferedImage image = acr.getImage();
				Image newImage = new Image(file.getName(), image);
				images.add(newImage);
			}
		}
		
		study.setImages(images);
	}
	
	/**
	 * Gets the list of all images in this directory
	 * @return ArrayList of all images in this directory
	 */
	/*
	public ArrayList<Object> getImages() {
		return images;
	}*/

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		try {
			this.open(this.directory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
