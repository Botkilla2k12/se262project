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
	private File newFile;
	
	public SaveCommand(Study study, File newFile) {
		this.study = study;
		this.newFile = newFile;
		save();
	}
	
	private void save() {
		File currentFile = study.getDirectory();
		currentFile.renameTo(newFile);
	}
	
}
