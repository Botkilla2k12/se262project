import java.io.File;

/**
 *
 *
 *@author Matthew Witte
 *
 *Version:
 *$Id$
 *
 *Revisions:
 *$Log$
 */

public class SaveCommand {

	private Study study;
	private String newFile;
	
	public SaveCommand(Study study, String newFile) {
		this.study = study;
		this.newFile = newFile;
		save();
	}
	
	private void save() {
		File currentFile = study.getDirectory();
		currentFile.renameTo(new File(newFile));
	}
	
}
