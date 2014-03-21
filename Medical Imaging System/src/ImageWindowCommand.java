import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class ImageWindowCommand extends UndoableCommand implements Command {
	private ArrayList<Image> imagesToManipulate;
	private int lowCutoff, highCutoff;
	
	public ImageWindowCommand(int low, int high, ArrayList<Image> images) {
		this.lowCutoff = low;
		this.highCutoff = high;
		this.imagesToManipulate = images;
	}
	
	@Override
	public void execute() {
		for (Image img : this.imagesToManipulate) {
			BufferedImage imgData = (BufferedImage) img.getImages().get(0);
			
			for(int x = 0; x < imgData.getWidth(); x++) {
				for(int y = 0; y < imgData.getHeight(); y++) {
					Color color = new Color(imgData.getRGB(x, y));
					
					int intensity = 0; //TODO: Calculate actual pixel intensity
					
					if(intensity < this.lowCutoff) {
						imgData.setRGB(x, y, 0);
					} else if(intensity > this.highCutoff) {
						int white = 255;
						white = (white << 8) + 255;
						white = (white << 8) + 255;

						imgData.setRGB(x, y, white);
					} else {
						//do a linear scaling
						//Slope = 255/(hi - lo)
						
					}
				}
			}
		}
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}
	
	public ArrayList<Image> getWindowedImages() {
		return this.imagesToManipulate;
	}
}
