import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 * 
 * @author Curtis Cali
 * This class represents the main window for the application/
 */
public class ImageViewerWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	private ImagePanel imagePanel;
	private JPanel mainPanel, reconstructionPanel, reconstructButtonPanel;
	private ImageViewerMenuBar menuBar;
	private NumberLabel numberLabel;
	private ListIterator<BufferedImage> reconstructionIterator;
	private StudyIterator studyIterator;
	private Study studyModel;
	private Stack<Study.Memento> previousModes;
	private boolean inReconstructMode;

	/**
	 * This initializes the window as well as any views/controllers using the
	 * Study as the model
	 * @param studyModel the model to initialize the window with
	 */
	public ImageViewerWindow(Study studyModel) {
		this.studyModel = studyModel;
		this.reconstructionIterator = null;
		this.previousModes = new Stack<Study.Memento>();
		this.menuBar = new ImageViewerMenuBar();

		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new GridLayout(1, 1));
		this.mainPanel.setBackground(Color.WHITE);

		this.reconstructionPanel = new JPanel();
		this.reconstructionPanel.setBackground(Color.WHITE);
		
		this.imagePanel = new ImagePanel(
			studyModel.getStudySettings().getDisplayMode()
		);
		
		this.mainPanel.add(imagePanel);
		this.inReconstructMode = false;

		this.numberLabel = new NumberLabel();
		
		JButton prevButton = new JButton("Previous");
		prevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					studyIterator.prev();
				} catch(IndexOutOfBoundsException ex) {
					JOptionPane.showMessageDialog(null, "First image!");
				}
			}
		});
		
		JButton nextButton = new JButton("Next");
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					studyIterator.next();
				} catch(IndexOutOfBoundsException ex) {
					JOptionPane.showMessageDialog(null, "Last image!");
				}
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.setBorder(
			BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)
		);
		buttonPanel.add(prevButton, BorderLayout.WEST);
		buttonPanel.add(this.numberLabel, BorderLayout.CENTER);
		buttonPanel.add(nextButton, BorderLayout.EAST);
		
		JPanel southernPanel = new JPanel();
		southernPanel.setLayout(new BorderLayout());
			
		southernPanel.add(buttonPanel, BorderLayout.NORTH);		
		
		this.reconstructButtonPanel = new JPanel();
		this.reconstructButtonPanel.setLayout(new BorderLayout());
		this.reconstructButtonPanel.setBorder(
			BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)
		);
		
		JButton reconstructPrevBtn = new JButton("Previous");
		reconstructPrevBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					setReconstructionPanelImage(reconstructionIterator.previous());
				} catch(NoSuchElementException ex) {
					JOptionPane.showMessageDialog(null, "First image!");
				}
			}
		});
		this.reconstructButtonPanel.add(reconstructPrevBtn, BorderLayout.WEST);
		
		this.reconstructButtonPanel.add(
			new JLabel("Reconstruction Scrolling", JLabel.CENTER),
			BorderLayout.CENTER
		);
		
		JButton reconstructNextBtn = new JButton("Next"); 
		reconstructNextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					setReconstructionPanelImage(reconstructionIterator.next());
				} catch(NoSuchElementException ex) {
					JOptionPane.showMessageDialog(null, "First image!");
				}
			}
		});
		this.reconstructButtonPanel.add(reconstructNextBtn, BorderLayout.EAST);
		
		southernPanel.add(this.reconstructButtonPanel, BorderLayout.SOUTH);
		this.reconstructButtonPanel.setVisible(false);
		
		super.setJMenuBar(this.menuBar);
		super.setLayout(new BorderLayout());
		super.add(this.mainPanel, BorderLayout.CENTER);
		super.add(southernPanel, BorderLayout.SOUTH);
		
		setupNewStudy(studyModel);
		setPanelDisplayMode(studyModel.getStudySettings().getDisplayMode());
		//Get rid of the initial state to avoid duplication when exhausting
		//undo operations.
		this.previousModes.pop();
		
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Medical Image Viewing System");
		super.setVisible(true);
	}

	/**
	 * This class reinitializes the study model in this window.
	 * @param study the study to be initializes
	 */
	public void setupNewStudy(Study study) {
		this.setReconstructMode(false, "");
		
		this.previousModes.removeAllElements();
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

		if(this.studyModel.getDirectory() != null) {
			this.menuBar.setRelatedStudies(this.studyModel.getDirectory());
		}

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
		
		this.studyIterator = new StudyIterator(this.studyModel);
	}
	
	/**
	 * This method allows for the display mode of the model to be dynamically
	 * changed
	 * @param mode the new display mode for the model.
	 */
	public void setPanelDisplayMode(DISPLAY_MODE_VALUE mode) {
		this.previousModes.push(this.studyModel.saveToMemento());
		this.studyModel.setDisplayMode(mode);
		this.studyIterator.setDisplayMode(mode);
	}

	public void undoStateChange() {
		try {
			Study.Memento previousState = this.previousModes.pop();
			this.studyModel.restoreFromMemento(previousState);
			this.studyIterator.setDisplayMode(previousState.getDisplayMode());
			this.menuBar.activateRadioButtonFromDisplayMode(previousState.getDisplayMode());
		} catch (EmptyStackException e) {
			JOptionPane.showMessageDialog(this, "All states have been undone");
		}
	}
	
	/**
	 * This method gets the current display mode for the underlying model
	 * @return the current display mode for the underlying model
	 */
	public DISPLAY_MODE_VALUE getPanelDisplayMode() {
		return this.studyModel.getStudySettings().getDisplayMode();
	}

	public void setReconstructMode(boolean isInReconstruction, String mode) {
		this.inReconstructMode = isInReconstruction;
		
		this.mainPanel.removeAll();
		
		if(this.inReconstructMode) {
			//Change window layout
			this.mainPanel.setLayout(new GridLayout(2, 2));
			//Put image panel in upper left hand corner
			this.mainPanel.add(imagePanel);
			
			//this.mainPanel.add(this.imagePanel);
			if(mode.equals("XZ")) {
				this.mainPanel.add(new JLabel());
				setReconstructionPanelImage(this.reconstructionIterator.next());
				this.mainPanel.add(reconstructionPanel);
				this.mainPanel.add(new JLabel());
			} else {
				setReconstructionPanelImage(this.reconstructionIterator.next());
				this.mainPanel.add(reconstructionPanel);
				this.mainPanel.add(new JLabel());
				this.mainPanel.add(new JLabel());
			}
			
			this.reconstructionPanel.revalidate();
			this.reconstructionPanel.repaint();
			this.reconstructButtonPanel.setVisible(true);
		} else {
			this.mainPanel.setLayout(new GridLayout(1, 1));
			this.mainPanel.add(this.imagePanel);
			
			this.numberLabel.update(this.studyModel, null);
			
			this.reconstructButtonPanel.setVisible(false);
		}
		
		this.mainPanel.revalidate();
		this.mainPanel.repaint();
	}
	
	private void setReconstructionPanelImage(BufferedImage img) {		
		this.reconstructionPanel.removeAll();
		
		this.reconstructionPanel.add(new JLabel(new ImageIcon(img)));
		
		this.reconstructionPanel.revalidate();
		this.reconstructionPanel.repaint();
	}
	
	public void setReconstructImages(ArrayList<BufferedImage> images) {
		this.reconstructionIterator = images.listIterator();
	}
	
	/**
	 * Fetches the directory that the current study is located in
	 * @return the directory that the current study is located in
	 */
	public File getDirectory(){
		return this.studyModel.getDirectory();
	}
	
	public int getIndex() {
		return this.studyModel.getIndex();
	}
	
	private static class NumberLabel extends JLabel implements Observer {
		private static final long serialVersionUID = 1L;

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
