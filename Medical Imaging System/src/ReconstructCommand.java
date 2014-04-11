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
	private String type, folderPath;
	private ArrayList<BufferedImage> reconstructImages;
	
	public ReconstructCommand(String directory, String type) {
		this.directory = directory;
		this.folderPath = directory + String.format(
			"Reconstruct%Cto%C",
			type.charAt(0),
			type.charAt(1)
		);
		this.type = type;
		this.reconstructImages = new ArrayList<BufferedImage>();
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
	
	/**
	 * Checks that all image files in a study are of the same file type and
	 * throws an error if they're not. Depending on the type of reconstruction,
	 * it either loops through each row then image then column and creates a new
	 * image for each row, or it loops though each column then image then row
	 * and creates a new image for each column.
	 * @throws IOException - thrown if there's an error reading in an image
	 * @throws TypeException - thrown if multiple image file types in same study
	 */
	private void reconstruct() throws IOException, TypeException {
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
			} else if (name.endsWith(".acr")) {
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
		
		File newFile = new File(this.folderPath);
		if(!newFile.exists()) {
			newFile.mkdir();
		}
		
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

				String reconstructImagePath =
						newFile.getAbsolutePath() + "\\" + height + ".jpg";
				ImageIO.write(
					reconstructImage,
					"jpg",
					new File(reconstructImagePath)
				);
			}
		}
		
		//X to Z
		else {
			for (int width = 0; width < firstImage.getWidth(); width++) { //pixel column
				BufferedImage reconstructImage = new BufferedImage(images.size(), firstImage.getHeight(), 5);
				for (int i = 0; i < images.size(); i++) { //loop through all images
					if (images.get(i) instanceof Image) {
						Image image = (Image) images.get(i);
						BufferedImage oldImage = (BufferedImage) image.getImages().get(0);
						for (int height = 0; height < oldImage.getHeight(); height++) { //pixel row
							int rgb = oldImage.getRGB(width, height);
							reconstructImage.setRGB(i, height, rgb);
						}
					}
				}

				String reconstructImagePath =
						newFile.getAbsolutePath() + "\\" + width + ".jpg";
				ImageIO.write(
					reconstructImage,
					"jpg",
					new File(reconstructImagePath)
				);
			}
		}
	}

	public String getFolderPath() {
		return this.folderPath;
	}
}
