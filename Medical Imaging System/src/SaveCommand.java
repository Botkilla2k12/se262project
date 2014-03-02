import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
	
	public SaveCommand(String oldName, String newName) {
		this.oldName = oldName;
		this.newName = newName;
	}

	public void save() {
		File newFile = new File(newName);
		newFile.mkdir();
		
		String configPath = oldName + "\\study.cfg";
		File configFile = new File(configPath);
		String newConfigPath = newFile.getAbsolutePath() + "\\study.cfg";
		configFile.renameTo(new File(newConfigPath));
		
		try {
			OpenCommand openCommand = new OpenCommand(new File(oldName));
			ArrayList<BufferedImage> images = openCommand.getImages();
			
			for (int i = 0; i < images.size() - 1; i++) {
				BufferedImage image = images.get(i);
				
				String newImagePath = newFile.getAbsolutePath() + "\\" + i + ".jpg";
				ImageIO.write(image, "jpg", new File(newImagePath));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
