import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ReconstructionWindow extends JFrame {
	public ReconstructionWindow(ArrayList<BufferedImage> reconstructedImages) {
		this.setLayout(new BorderLayout());
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		
		JButton prevButton = new JButton("Previous"),
				nextButton = new JButton("Next");
		
		buttonPanel.add(prevButton, BorderLayout.WEST);
		buttonPanel.add(nextButton, BorderLayout.EAST);
		
		this.add(buttonPanel);
	}
}
	