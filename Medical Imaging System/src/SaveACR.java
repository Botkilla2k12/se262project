import java.awt.image.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.stream.FileImageOutputStream;

public class SaveACR {
    public static final int HEADER_OFFSET = 0x2000;
    private BufferedImage image;

    /**
     * Takes in a BufferedImage and converts it to an .acr file
     * with the given file name.
     */
    public SaveACR(BufferedImage image, File file) {
    	
    	this.image = image;
    	
    	FileImageOutputStream imageFile = null;
		try {
		    imageFile = new FileImageOutputStream(file);
		    imageFile.seek(HEADER_OFFSET);
		}
		catch (FileNotFoundException e) {
		    System.err.print("Error opening file: ");
		    System.err.println(e.getMessage());
		    System.exit(2);
		}
		catch (IOException e) {
		    System.err.print("IO error on file: ");
		    System.err.println(e.getMessage());
		    System.exit(2);
		}
    	
    	for ( int i = 0; i < this.image.getHeight(); i++ ) {
		    for ( int j = 0; j < this.image.getWidth(); j++ ) {
				
		    	try {
		    		int rgb = this.image.getRGB(j, i);
			    	
			    	int pixel = rgb >> 16 | rgb >> 8 | rgb;
			    	int pixelHigh = pixel >> 4;
			    	int pixelLow = pixel << 4;
			    	imageFile.write(pixelHigh);
			    	imageFile.write(pixelLow);
		    	}
		    	catch (IOException e) {
				    System.err.print("IO error writing byte: ");
				    System.err.println(e.getMessage());
				    System.exit(2);
				}			
			
		    }
		}

    }
    
    public BufferedImage getImage() {
    	return this.image;
    }
}

