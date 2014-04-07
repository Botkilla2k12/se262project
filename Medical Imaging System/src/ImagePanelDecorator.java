import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPanel;

public abstract class ImagePanelDecorator extends ImagePanel {
	private int progress, total, maxDim, imgIndex;
	
	public ImagePanelDecorator(DISPLAY_MODE_VALUE value, int total, int dim, int index) {
		super(value);
		
		progress = 0;
		this.total = total;
		this.maxDim = dim;
		this.imgIndex = index;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
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
