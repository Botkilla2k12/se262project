import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * This class Decorates an ImagePanel with a Goldenrod Horizontal line that
 * displays based on where the user is in the study.
 * @author Curtis
 */
public class HorizontalLineDecorator extends ImagePanelDecorator {
	/**
	 * Creates a new HorizontalLineDecorator based on parameters
	 * @param value the initial display mode of the ImagePanel
	 * @param total The maximum possible progress through the Reconstruction.
	 * @param dim the size of the object to be decorated
	 */
	public HorizontalLineDecorator(DisplayMode value, int total, int dim) {
		super(value, total, dim);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		double fraction = ((double) getProgress() / getTotal());
		int y = (int) (fraction * getMaxDim());

		Graphics2D g2d = (Graphics2D) g;
		
		Color original = g2d.getColor();
		g2d.setColor(new Color(218, 165, 32));
		g2d.drawLine(0, y, getMaxDim(), y);
		g2d.setColor(original);
	}
}
