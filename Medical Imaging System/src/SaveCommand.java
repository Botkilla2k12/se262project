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
	private static final String CONFIG_FILE = "study.cfg";
	
	/**
	 * Initializes a SaveCommand object with the old filepath
	 * and new filepath
	 * @param oldName - filepath that is being saved under a new name
	 * @param newName - new filepath name to be saved
	 */
	public SaveCommand(String oldName, String newName) {
		this.oldName = oldName;
		this.newName = newName;
		execute();
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
		File newFile = new File(newName);
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
			ArrayList<Image> images = new ArrayList<Image>();
			File oldFile = new File(oldName);
			File[] files = oldFile.listFiles();
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
			
			/*REMOVE THESE LINES AFTER TESTING*/
			ArrayList<Image> imagesToManipulate = new ArrayList<Image>();
			for (int i = 0; i < images.size(); i++) {
				imagesToManipulate.add(images.get(i));
			}
			ImageWindowCommand iwc = new ImageWindowCommand(125, 125, imagesToManipulate);
			iwc.execute();
			ArrayList<BufferedImage> manipulatedImages = iwc.getWindowedImages();
			String testName = newName + "manipulated";
			File testFile = new File(testName);
			testFile.mkdir();
			for (int a = 0; a < manipulatedImages.size(); a++) {
				BufferedImage image = manipulatedImages.get(a);
				int b = a + 1;
				String newImagePath = testFile.getAbsolutePath() + "\\" + b + ".jpg";
				ImageIO.write(image, "jpg", new File(newImagePath));
			}
			/*END REMOVE LINES HERE*/
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
