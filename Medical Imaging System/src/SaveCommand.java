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
	private String newName;
	
	public SaveCommand(Study study, String newName) {
		this.study = study;
		this.newName = newName;
		save();
	}
	
	private void save() {
		File currentFile = study.getDirectory();
		String configPath = currentFile.getAbsolutePath() + "\\study.cfg";
		File configFile = new File(configPath);
		currentFile.renameTo(new File(newName));
		String newPath = currentFile.getAbsolutePath() + "\\study.cfg";
		configFile.renameTo(new File(newPath));
	}
	
}
