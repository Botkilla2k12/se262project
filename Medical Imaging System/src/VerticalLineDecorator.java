import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * This class Decorates an ImagePanel with a Goldenrod Vertical line that
 * displays based on where the user is in the study.
 * @author Curtis
 */
public class VerticalLineDecorator extends ImagePanelDecorator {
	/**
	 * Creates a new VerticalLineDecorator based on parameters
	 * @param value the initial display mode of the ImagePanel
	 * @param total The maximum possible progress through the Reconstruction.
	 * @param dim the size of the object to be decorated
	 */
	public VerticalLineDecorator(DisplayMode value, int total, int dim) {
		super(value, total, dim);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		double fraction = (double) getProgress() / (double) getTotal();
		int x = (int) (fraction * getMaxDim());

		Graphics2D g2d = (Graphics2D) g;
		
		Color original = g2d.getColor();
		g2d.setColor(new Color(218, 165, 32));
		g2d.drawRect(0, 0, x, getMaxDim());
		g2d.setColor(original);
	}
}
