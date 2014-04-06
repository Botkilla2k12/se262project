import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPanel;

public abstract class PanelDecorator {
	private JPanel component;
	private int progress, total, maxDim, imgIndex;
	
	public PanelDecorator(JPanel panel, int total, int dim, int index) {
		component = panel;
		progress = 0;
		this.total = total;
		this.maxDim = dim;
		this.imgIndex = index;
	}
	
	public void paint(int currIdx) {
		component.revalidate();
		component.repaint();
	}
	
	protected Graphics getGraphics() {
		return component.getGraphics();
	}
	
	public int getProgress() {
		return progress;
	}
	
	public int getTotal() {
		return total;
	}
	
	public int getMaxDim() {
		return maxDim;
	}
	
	public int getImageIndex() {
		return imgIndex;
	}
	
	public void setProgress(int current) {
		progress = current;
	}
}
