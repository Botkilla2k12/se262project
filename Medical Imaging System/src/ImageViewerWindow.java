import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 
 * @author Curtis Cali
 * This class represents the main window for the application/
 */
public class ImageViewerWindow extends JFrame {
	private ImagePanel imagePanel;
	private ImageViewerMenuBar menuBar;
	private JButton prevButton, nextButton;
	private NumberLabel numberLabel;
	private BrowseCommand browseCommand;
	private Study studyModel;
	private Stack<Study.Memento> previousModes;

	/**
	 * This initializes the window as well as any views/controllers using the
	 * Study as the model
	 * @param studyModel the model to initialize the window with
	 */
	public ImageViewerWindow(Study studyModel) {
		this.studyModel = studyModel;
		this.previousModes = new Stack<Study.Memento>();
		this.menuBar = new ImageViewerMenuBar();
		this.imagePanel = new ImagePanel(
			studyModel.getStudySettings().getDisplayMode()
		);

		this.numberLabel = new NumberLabel();
		
		setupNewStudy(studyModel);
		setPanelDisplayMode(studyModel.getStudySettings().getDisplayMode());
		//Get rid of the initial state to avoid duplication when exhausting
		//undo operations.
		this.previousModes.pop();
		
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
					browseCommand.setIndex(studyModel.getIndex());
					browseCommand.prev();
				} catch(IndexOutOfBoundsException ex) {
					JOptionPane.showMessageDialog(null, "First image!");
				}
			} else if(e.getSource() == nextButton) {
				try {
					browseCommand.setIndex(studyModel.getIndex());
					browseCommand.next();
				} catch(IndexOutOfBoundsException ex) {
					JOptionPane.showMessageDialog(null, "Last image!");
				}
			}
		}
		
	}

	/**
	 * This class reinitializes the study model in this window.
	 * @param study the study to be initializes
	 */
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
		) {
			super.setSize(600, 600);
		} else {
			super.setSize(
				(this.studyModel.getImageWidth() * 2) + 20,
				(this.studyModel.getImageHeight() * 2) + 80
			);
		}
		
		this.browseCommand = new BrowseCommand(this.studyModel);
	}
	
	/**
	 * This method allows for the display mode of the model to be dynamically
	 * changed
	 * @param mode the new display mode for the model.
	 */
	public void setPanelDisplayMode(DISPLAY_MODE_VALUE mode) {
		this.previousModes.push(this.studyModel.saveToMemento());
		this.studyModel.setDisplayMode(mode);
		this.browseCommand.setDisplayMode(mode);
	}

	public void undoStateChange() {
		try {
			Study.Memento previousState = this.previousModes.pop();
			this.studyModel.restoreFromMemento(previousState);
			this.browseCommand.setDisplayMode(previousState.getDisplayMode());
		} catch (EmptyStackException e) {
			JOptionPane.showMessageDialog(this, "All states have been undone");
		}
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

	/**
	 * This method gets the current display mode for the underlying model
	 * @return the current display mode for the underlying model
	 */
	public DISPLAY_MODE_VALUE getPanelDisplayMode() {
		return this.studyModel.getStudySettings().getDisplayMode();
	}

	/**
	 * Fetches the directory that the current study is located in
	 * @return the directory that the current study is located in
	 */
	public File getDirectory(){
		return this.studyModel.getDirectory();
	}
}
