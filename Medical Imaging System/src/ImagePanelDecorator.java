import java.awt.Graphics;
import java.util.Observable;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * This is the base abstract class for any class that will decorate ImagePanel
 * @author Curtis
 */
public abstract class ImagePanelDecorator extends ImagePanel {
	private int progress, total, maxDim;
	
	/**
	 * Creates a new ImagePanel based on Parameters 
	 * @param value the initial display mode of the ImagePanel
	 * @param total The maximum possible progress through the Reconstruction.
	 * @param dim the size of the object to be decorated
	 */
	public ImagePanelDecorator(DisplayMode value, int total, int dim) {
		super(value);
		
		progress = 0;
		this.total = total;
		this.maxDim = dim;
	}
	
	/**
	 * Gets how far the user has iterated through the study
	 * @return How far the user has iterated through the study
	 */
	public int getProgress() {
		return progress;
	}
	
	/**
	 * Gets the total possible progress through a reconstruction
	 * @return The total possible progress through a reconstruction
	 */
	public int getTotal() {
		return total;
	}
	
	/**
	 * Gets the dimension (size) of the object to be decorated as an int
	 * @return The dimension (size) of the object to be decorated as an int
	 */
	public int getMaxDim() {
		return maxDim;
	}
	
	/**
	 * Changes the current progress of the line through the reconstruction.
	 * @param current the current progress the
	 */
	public void setProgress(int current) {
		progress = current;
	}
}
