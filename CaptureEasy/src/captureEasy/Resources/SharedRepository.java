package captureEasy.Resources;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class SharedRepository{

	public static String PropertyFilePath=System.getProperty("user.dir")+"/CaptureEasy/Properties/AppProperty.properties";
	public static String TempFilePath=System.getProperty("user.dir")+"/CaptureEasy/Properties/Tempfolder.properties";
	public static String DataFilePath=System.getProperty("user.dir")+"/CaptureEasy/Properties/Data.properties";

	public static String Log4jPropertyFilePath=System.getProperty("user.dir")+"/CaptureEasy/Properties/Log4j.properties";
	public static String LogFolderPath=System.getProperty("user.dir")+"/CaptureEasy/Logs";

	public static Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	public static Logger logger = Logger.getLogger(SharedRepository.class);
	
	public static boolean PauseThread=false;
	public static boolean stopThread=false;
	
	public static int progress=0;
	
	public static Map<String,String> comments=new HashMap<>();

	

}
