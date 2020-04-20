package captureEasy.Resources;

import java.awt.Dimension;
import java.awt.Toolkit;
import org.apache.log4j.Logger;

public class SharedRepository{

	public static String PropertyFilePath=System.getProperty("user.dir")+"/CaptureEasy/Properties/Settings.properties";
	public static String TempFilePath=System.getProperty("user.dir")+"/CaptureEasy/Properties/Tempfolder.properties";
	public static String ClipBoardDataFilePath=System.getProperty("user.dir")+"/CaptureEasy/Properties/ClipboardData.properties";

	public static String Log4jPropertyFilePath=System.getProperty("user.dir")+"/CaptureEasy/Properties/Log4j.properties";
	public static String LogFolderPath=System.getProperty("user.dir")+"/CaptureEasy/Logs";

	public static Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	public static Logger logger = Logger.getLogger(SharedRepository.class);
	
	public static boolean PauseThread=false;
	public static boolean stopThread=false;
	
	public static int progress=0;

	

}
