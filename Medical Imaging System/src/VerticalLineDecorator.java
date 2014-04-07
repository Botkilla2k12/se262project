import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;


public class VerticalLineDecorator extends ImagePanelDecorator {
	public VerticalLineDecorator(DISPLAY_MODE_VALUE value, int total, int dim, int index) {
		super(value, total, dim, index);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		double fraction = (double) getProgress() / (double) getTotal();
		int x = (int) (fraction * getMaxDim());

		Graphics2D g2d = (Graphics2D) g;
		
		Color original = g2d.getColor();
		g2d.setColor(new Color(218, 165, 32));
		g2d.drawRect(0, 0, x, getMaxDim());
		g2d.setColor(original);
	}
}
