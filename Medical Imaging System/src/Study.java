import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;

/**
 * @author derek
 *
 */
public class Study extends Observable {
	private ArrayList<Image> Images;
	private File directory; 
	
	public Study(ArrayList<Image> Images) {
		// TODO Auto-generated constructor stub
		this.Images = Images;
	}
	
	public Study open() {
		openCommandObject = new OpenCommand(this.directory);
		
	}
	

}
