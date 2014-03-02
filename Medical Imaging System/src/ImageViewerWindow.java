	import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class ImageViewerWindow extends JFrame {
	private ImagePanel imagePanel;
	private ImageViewerMenuBar menuBar;
	private JButton prevButton, nextButton;
	private NumberLabel numberLabel;
	private BrowseCommand browseCommand;
	private Study studyModel;

	public ImageViewerWindow(Study studyModel) {
		this.studyModel = studyModel;
		this.menuBar = new ImageViewerMenuBar();
		this.imagePanel = new ImagePanel(
			studyModel.getStudySettings().getDisplayMode()
		);

		this.numberLabel = new NumberLabel();
		
		setupNewStudy(studyModel);
		setPanelDisplayMode(studyModel.getStudySettings().getDisplayMode());
		
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
		buttonPanel.add(this.numberLabel, BorderLayout.CENTER);
		buttonPanel.add(this.nextButton, BorderLayout.EAST);
		
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		super.setVisible(true);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setTitle("Medical Image Viewing System");
	}
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
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
		if(this.studyModel != null) {
			this.studyModel.deleteObserver(imagePanel);
			this.studyModel.deleteObserver(numberLabel);
		}
		
		this.studyModel = study;
		
		this.studyModel.addObserver(imagePanel);
		this.studyModel.addObserver(numberLabel);
		
		this.menuBar.activateRadioButtonFromDisplayMode(
			study.getStudySettings().getDisplayMode()
		);
		
		try {
			this.studyModel.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(this.studyModel.getImageWidth() == 0 ||
			this.studyModel.getImageHeight() == 0
		){
			super.setSize(600, 600);
		} else {
			super.setSize(
				(this.studyModel.getImageWidth() * 2) + 20,
				(this.studyModel.getImageHeight() * 2) + 80
			);
		}
		
		this.browseCommand = new BrowseCommand(this.studyModel);
	}
	
	public void setPanelDisplayMode(DISPLAY_MODE_VALUE mode) {
		this.studyModel.setDisplayMode(mode);
		this.browseCommand.setDisplayMode(mode);
	}
	
	private static class NumberLabel extends JLabel implements Observer {
		public NumberLabel() {
			super.setHorizontalAlignment(JLabel.CENTER);
		}

		@Override
		public void update(Observable subj, Object data) {
			Study study = (Study) subj;
			
			DISPLAY_MODE_VALUE mode = study.getStudySettings().getDisplayMode();
			
			if(mode == DISPLAY_MODE_VALUE.ONE_IMAGE) {
				super.setText(
					String.format("Current Image: %d", study.getIndex() + 1)
				);
				
			} else {
				super.setText(
					String.format(
						"Current Images: %d - %d",
						study.getIndex() + 1,
						study.getIndex() + study.getCurrentImages().size()
					)
				);
			}
		}
	}
}
