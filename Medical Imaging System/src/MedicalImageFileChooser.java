import javax.swing.JFileChooser;


public class MedicalImageFileChooser extends JFileChooser {
	public MedicalImageFileChooser() {
		this.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		this.setAcceptAllFileFilterUsed(false);
	}
}
