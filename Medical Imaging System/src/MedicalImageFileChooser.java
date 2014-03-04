import javax.swing.JFileChooser;

/**
 * A simple File Chooser subclass created with the sole purpose of refactoring
 * File Chooser spawning code.
 * @author Curtis Cali
 *
 */
public class MedicalImageFileChooser extends JFileChooser {
	/**
	 * Default and only constructor for MedicalImageFileChooser
	 * Creates a filechooser that selects directories only and doesn't have an
	 * all files filter
	 */
	public MedicalImageFileChooser() {
		this.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		this.setAcceptAllFileFilterUsed(false);
	}
}
