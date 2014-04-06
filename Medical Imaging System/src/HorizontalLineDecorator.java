import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;


public class HorizontalLineDecorator extends PanelDecorator {
	public HorizontalLineDecorator(JPanel panel, int total, int dim, int index) {
		super(panel, total, dim, index);
	}

	@Override
	public void paint(int currIdx) {
		if(currIdx == this.getImageIndex()) {
			double fraction = (double) getProgress() / (double) getTotal();
			int y = (int) (fraction * getMaxDim());
	
			Graphics2D g2d = (Graphics2D) getGraphics();
			
			Color original = g2d.getColor();
			g2d.setColor(new Color(218, 165, 32));
			g2d.drawLine(0, y, getMaxDim(), y);
			g2d.setColor(original);
		}
		
		super.paint(currIdx);	
	}
}
