import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * This class encapsulates all system-wide settings, which for now is only the
 * default study.
 * @author derek
 *
 */
public class SystemSettings {
	private static final String CONFIG_FILE = "defaultstudy.cfg";
	
	private String defaultPath;
	private Study defaultStudy;
	
	/**
	 * Constructs a SystemSettings object, no args necessary
	 */
	public SystemSettings() {
		Scanner sc = null;
		try {
			File configFile = new File(CONFIG_FILE);
			
			if(!configFile.exists()) {
				configFile.createNewFile();

				setDefaultPath("null");
			}
			
			sc = new Scanner(configFile);
			if (sc.hasNextLine()){
				this.defaultPath = sc.nextLine();
				
				if(defaultPath.equals("null")) {
					this.defaultStudy = null;
				} else {
					this.defaultStudy = new Study(new File(this.defaultPath));
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return true if a default study is initialized, false otherwise.
	 */
	public boolean hasDefaultStudy() {
		return this.defaultStudy != null;
	}
	
	/**
	 * Sets the default study for the system
	 * @param study the study to be set as default
	 */
	public void setDefaultStudy(Study study) {
		this.defaultStudy = study;
		try{
			setDefaultPath(study.getDirectory().getPath());
		} catch(NullPointerException i){
			
		}
	}
	
	/**
	 * Sets the directory for the default study.
	 * @param newPath the new path for the study
	 */
	public void setDefaultPath(String newPath) {
		this.defaultPath = newPath;
		
		//write to file
		PrintWriter writer = null;;
		try {
			writer = new PrintWriter(CONFIG_FILE, "UTF-8");
			writer.println(this.defaultPath);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}
	
	/**
	 * Gets the default study's path
	 * @return the default study's path
	 */
	public String getDefaultPath() {
		return this.defaultPath;
	}
	
	/**
	 * gets a referenece to the default study
	 * @return a referenece to the default study
	 */
	public Study getDefaultStudy() {
		return this.defaultStudy;
	}
}
