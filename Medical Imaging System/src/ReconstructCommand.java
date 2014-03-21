import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

public class ReconstructCommand implements Command {

	private String directory;
	
	public ReconstructCommand(String directory) {
		this.directory = directory;
		execute();
	}
	
	public void execute() {
		try {
			reconstruct();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void reconstruct() throws IOException {
		File oldFile = new File(directory);
		File[] files = oldFile.listFiles();
		Arrays.sort(files);
		ArrayList<Object> images = new ArrayList<Object>();
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
		
		//Y to Z
		String newDirectoryYZ = directory + "ReconstructYtoZ";
		File newFileYZ = new File(newDirectoryYZ);
		newFileYZ.mkdir();
		for (int height = 0; height < 384; height++) { //pixel row
			BufferedImage reconstructImage = new BufferedImage(384, images.size(), 5);
			for (int i = 0; i < images.size(); i++) { //loop through all images
				if (images.get(i) instanceof Image) {
					Image image = (Image) images.get(i);
					BufferedImage oldImage = (BufferedImage) image.getImages().get(0);
					for (int width = 0; width < oldImage.getWidth(); width++) { //pixel column
						int rgb = oldImage.getRGB(width, height);
						reconstructImage.setRGB(width, i, rgb);
					}
				}
			}
			String reconstructImagePath = newFileYZ.getAbsolutePath() + "\\" + height + ".jpg";
			ImageIO.write(reconstructImage, "jpg", new File(reconstructImagePath));
		}
		
		//X to Z
				String newDirectoryXZ = directory + "ReconstructXtoZ";
				File newFileXZ = new File(newDirectoryXZ);
				newFileXZ.mkdir();
				for (int width = 0; width < 384; width++) { //pixel row
					BufferedImage reconstructImage = new BufferedImage(images.size(), 384, 5);
					for (int i = 0; i < images.size(); i++) { //loop through all images
						if (images.get(i) instanceof Image) {
							Image image = (Image) images.get(i);
							BufferedImage oldImage = (BufferedImage) image.getImages().get(0);
							for (int height = 0; height < oldImage.getHeight(); height++) { //pixel column
								int rgb = oldImage.getRGB(width, height);
								reconstructImage.setRGB(i, height, rgb);
							}
						}
					}
					String reconstructImagePath = newFileXZ.getAbsolutePath() + "\\" + width + ".jpg";
					ImageIO.write(reconstructImage, "jpg", new File(reconstructImagePath));
				}
	}

}
