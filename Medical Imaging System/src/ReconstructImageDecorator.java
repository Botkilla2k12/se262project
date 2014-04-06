import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class ReconstructImageDecorator {
	private BufferedImage image;
	
	public ReconstructImageDecorator(BufferedImage image) {
		this.image = image;
	}
	
	public void draw() {
		Graphics2D g2d = (Graphics2D) this.image.getGraphics();
		g2d.drawImage(this.image, 0, 0, null);
	}
}
