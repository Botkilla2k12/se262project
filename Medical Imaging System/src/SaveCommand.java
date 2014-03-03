import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

public class SaveCommand {

	private String oldName;
	private String newName;
	private static final String CONFIG_FILE = "study.cfg";
	
	public SaveCommand(String oldName, String newName) {
		this.oldName = oldName;
		this.newName = newName;
		System.out.println(this.newName);
	}

	public void save() {
		File newFile = new File(newName);
		newFile.mkdir();
		
		String configPath = oldName + "\\study.cfg";
		//File configFile = new File(configPath);
		//String newConfigPath = newFile.getAbsolutePath() + "\\study.cfg";
		
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
		
		try {
			OpenCommand openCommand = new OpenCommand(new File(oldName));
			ArrayList<BufferedImage> images = openCommand.getImages();
			ArrayList<String> imageNames = openCommand.getImageNames();
			
			for (int i = 0; i < images.size() - 1; i++) {
				BufferedImage image = images.get(i);
				
				String newImagePath = newFile.getAbsolutePath() + "\\" + imageNames.get(i);
				ImageIO.write(image, "jpg", new File(newImagePath));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
