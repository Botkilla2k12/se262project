import java.awt.Graphics;
import java.util.Observable;

import javax.swing.JComponent;
import javax.swing.JPanel;

public abstract class ImagePanelDecorator extends ImagePanel {
	private int progress, total, maxDim, imgIndex;
	
	public ImagePanelDecorator(DisplayMode value, int total, int dim, int index) {
		super(value);
		
		progress = 0;
		this.total = total;
		this.maxDim = dim;
		this.imgIndex = index;
	}
	
	@Override
	public void update(Observable subject, Object data) {
		Study study = (Study) subject;
		
		super.update(subject, data);
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
