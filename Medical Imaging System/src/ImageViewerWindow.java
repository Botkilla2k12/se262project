import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class ImageViewerWindow extends JFrame {
	private ImagePanel imagePanel;
	private ImageViewerMenuBar menuBar;
	private JButton prevButton, nextButton;
	private BrowseCommand browseCommand;
	//private Settings settings;

	public ImageViewerWindow(Study studyModel) {
		this.menuBar = new ImageViewerMenuBar();
		this.imagePanel = new ImagePanel(
			studyModel.getStudySettings().getDisplayMode()
		);

		this.prevButton = new JButton("Previous");
		this.prevButton.addActionListener(new ButtonListener());
		
		this.nextButton = new JButton("Next");
		this.nextButton.addActionListener(new ButtonListener());
		
		studyModel.addObserver(this.imagePanel);
		
		this.setJMenuBar(this.menuBar);
		
		this.setLayout(new BorderLayout());

		this.add(this.imagePanel, BorderLayout.CENTER);		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		
		buttonPanel.add(this.prevButton, BorderLayout.WEST);
		buttonPanel.add(this.nextButton, BorderLayout.EAST);
		
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		try {
			studyModel.open();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.browseCommand = new BrowseCommand(studyModel);
		
		this.setVisible(true);
		this.setSize(600, 600);
		this.setTitle("Medical Image Viewing System");
	}
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == prevButton) {
				try {
					browseCommand.prev();
				} catch(IndexOutOfBoundsException ex) {
					JOptionPane.showMessageDialog(null, "First image!");
				}
			} else if(e.getSource() == nextButton) {
				try {
					browseCommand.next();
				} catch(IndexOutOfBoundsException ex) {
					JOptionPane.showMessageDialog(null, "Last image!");
				}
			}
		}
		
	}
}
