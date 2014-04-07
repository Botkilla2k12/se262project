import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;


public class HorizontalLineDecorator extends ImagePanelDecorator {
	public HorizontalLineDecorator(DisplayMode value, int total, int dim, int index) {
		super(value, total, dim, index);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(isActive()) {
			double fraction = ((double) getProgress() / getTotal());
			int y = (int) (fraction * getMaxDim());
	
			Graphics2D g2d = (Graphics2D) g;
			
			Color original = g2d.getColor();
			g2d.setColor(new Color(218, 165, 32));
			g2d.drawLine(0, y, getMaxDim(), y);
			g2d.setColor(original);
		}
	}
}
