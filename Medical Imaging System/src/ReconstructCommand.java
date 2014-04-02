import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;

/**
 * ReconstructCommand.java
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
	private String type;
	private ArrayList<BufferedImage> reconstructImages;
	
	public ReconstructCommand(String directory, String type) {
		this.directory = directory;
		this.type = type;
		this.reconstructImages = new ArrayList<BufferedImage>();
		execute();
	}
	
	public void execute() {
		try {
			reconstruct();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TypeException t) {
			t.printStackTrace();
		}
	}
	
	private ArrayList<BufferedImage> reconstruct() throws IOException, TypeException {
		File oldFile = new File(directory);
		File[] files = oldFile.listFiles();
		Arrays.sort(files);
		ArrayList<Image> images = new ArrayList<Image>();
		
		String first;
		if (files[0].getName().toLowerCase().endsWith(".jpg")) {
			first = ".jpg";
		}
		else if (files[0].getName().toLowerCase().endsWith(".jpeg")) {
			first = ".jpeg";
		}
		else {
			first = ".acr";
		}
			
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			String name = file.getName().toLowerCase();
			if (name.endsWith(".jpg") || name.endsWith(".jpeg")) {
				if (first == ".acr") {
					throw new TypeException();
				}
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
				if (first == ".jpg" || first == ".jpeg") {
					throw new TypeException();
				}
				ShowACR acr = new ShowACR(file);
				BufferedImage image = acr.getImage();
				Image newImage = new Image(file.getName(), image);
				images.add(newImage);
			}
		}
		
		BufferedImage firstImage = (BufferedImage) images.get(0).getImages().get(0);
		
		//Y to Z
		if (type == "YZ") {
			for (int height = 0; height < firstImage.getHeight(); height++) { //pixel row
				BufferedImage reconstructImage = new BufferedImage(firstImage.getWidth(), images.size(), 5);
				for (int i = 0; i < images.size(); i++) { //loop through all images
					Image image = images.get(i);
					BufferedImage oldImage = (BufferedImage) image.getImages().get(0);
					for (int width = 0; width < oldImage.getWidth(); width++) { //pixel column
						int rgb = oldImage.getRGB(width, height);
						reconstructImage.setRGB(width, i, rgb);
					}
				}
				reconstructImages.add(reconstructImage);
			}
		}
		
		//X to Z
		else {
			for (int width = 0; width < firstImage.getWidth(); width++) { //pixel row
				BufferedImage reconstructImage = new BufferedImage(images.size(), firstImage.getHeight(), 5);
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
				reconstructImages.add(reconstructImage);
			}
		}
		
		return this.reconstructImages;
	}

}
