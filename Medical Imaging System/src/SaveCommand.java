import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 *
 *
 *@author Matthew Witte
 *
 *Version:
 *$Id$
 *
 *Revisions:
 *$Log$
 */

public class SaveCommand implements Command {

	private String oldName;
	private String newName;
	private ArrayList<Image> images;
	private static final String CONFIG_FILE = "study.cfg";
	private boolean saved;
	
	/**
	 * Initializes a SaveCommand object with the old filepath
	 * and new filepath
	 * @param oldName - filepath that is being saved under a new name
	 * @param newName - new filepath name to be saved
	 */
	public SaveCommand(ArrayList<Image> images, String oldName, String newName, boolean saved) {
		this.oldName = oldName;
		this.images = images;
		this.newName = newName;
		this.saved = saved;
	}
	
	public void execute() {
		save();
	}
	
	/**
	 * Makes a new directory and copies the CFG file and all images
	 * from the old directory into the new directory.
	 */
	public void save() {
		//Makes new directory
		File newFile;
		if (saved) {
			newFile = new File(newName);
		}
		else {
			newFile = new File(newName+"Manipulated");
		}
		newFile.mkdir();
		String configPath = oldName + "\\study.cfg";
		
		//Reads the old CFG file and makes a new one with all of the
		//same contents in the new directory
		PrintWriter writer = null;
		Scanner sc = null;
		try{
			sc = new Scanner(new File(configPath));
			String[] nextLine = new String[2];
			for (int i = 0; i < 2; i++) {
				if (sc.hasNextLine()) {
					nextLine[i] = sc.nextLine();
				}
			}
			
			writer = new PrintWriter(
				newFile.getAbsolutePath() + "\\" + CONFIG_FILE, "UTF-8"
			);
			writer.println(nextLine[0]);
			writer.println(nextLine[1]);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
		
		//Loops through all images in the old directory and copies them into new directory
		try {

			for (int i = 0; i < images.size(); i++) {
				if (images.get(i) instanceof Image){
					Image image = (Image) images.get(i);
					String name = image.toString().toLowerCase();
					String newImagePath = newFile.getAbsolutePath() + "\\" + image.toString();
					
					if (name.endsWith(".jpg")) {
						ImageIO.write((BufferedImage) image.getImages().get(0), "jpg", new File(newImagePath));
					}
					else if (name.endsWith(".jpeg")) {
						ImageIO.write((BufferedImage) image.getImages().get(0), "jpeg", new File(newImagePath));
					}
					else if (name.endsWith(".acr")) {
						File file = new File(newImagePath);
						file.createNewFile();
						SaveACR save = new SaveACR((BufferedImage) image.getImages().get(0), file);
					}
				} else {
					try {
						throw new TypeException();
					} catch (TypeException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
