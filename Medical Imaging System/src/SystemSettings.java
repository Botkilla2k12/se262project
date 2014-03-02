import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * 
 * @author derek
 *
 */
public class SystemSettings {
	private static final String CONFIG_FILE = "defaultstudy.cfg";
	
	private String defaultPath;
	private Study defaultStudy;
	
	public SystemSettings() {
		Scanner sc = null;
		try {
			sc = new Scanner(new File(CONFIG_FILE));
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
	
	public boolean hasDefaultStudy() {
		return this.defaultStudy != null;
	}
	
	public void setDefaultStudy(Study study) {
		this.defaultStudy = study;
		
		setDefaultPath(study.getDirectory().getPath());
	}
	
	public void setDefaultPath(String newPath) {
		this.defaultPath = newPath;
		
		//write to file
		PrintWriter writer = null;;
		try {
			writer = new PrintWriter(CONFIG_FILE, "UTF-8");
			writer.println(this.defaultPath);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}
	
	public String getDefaultPath() {
		return this.defaultPath;
	}
	
	public Study getDefaultStudy() {
		return this.defaultStudy;
		
		
	}

}
