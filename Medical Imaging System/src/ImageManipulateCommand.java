import java.util.ArrayList;


public class ImageManipulateCommand extends UndoableCommand implements Command {
	private ArrayList<Image> imagesToManipulate;
	private int lowCutoff, highCutoff;
	
	public ImageManipulateCommand(int low, int high, ArrayList<Image> images) {
		this.lowCutoff = low;
		this.highCutoff = high;
		this.imagesToManipulate = images;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}
}
