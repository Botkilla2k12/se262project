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
	private Study studyModel;

	public ImageViewerWindow(Study studyModel) {
		this.menuBar = new ImageViewerMenuBar();
		this.imagePanel = new ImagePanel(
			studyModel.getStudySettings().getDisplayMode()
		);

		setupNewStudy(studyModel);
		
		this.prevButton = new JButton("Previous");
		this.prevButton.addActionListener(new ButtonListener());
		
		this.nextButton = new JButton("Next");
		this.nextButton.addActionListener(new ButtonListener());
				
		this.setJMenuBar(this.menuBar);
		
		this.setLayout(new BorderLayout());

		this.add(this.imagePanel, BorderLayout.CENTER);		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		
		buttonPanel.add(this.prevButton, BorderLayout.WEST);
		buttonPanel.add(this.nextButton, BorderLayout.EAST);
		
		this.add(buttonPanel, BorderLayout.SOUTH);
		
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

	public void setupNewStudy(Study study) {
		this.studyModel = study;
		
		this.studyModel.deleteObserver(imagePanel);
		
		this.studyModel.addObserver(imagePanel);
		
		try {
			this.studyModel.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.browseCommand = new BrowseCommand(this.studyModel);
	}
	
	public void setPanelDisplayMode(DISPLAY_MODE_VALUE mode) {
		this.studyModel.setDisplayMode(mode);
	}
}
