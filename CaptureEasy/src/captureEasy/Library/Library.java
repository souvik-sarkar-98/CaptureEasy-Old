package captureEasy.Library;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import captureEasy.Screenshot.ClipboardHandeler.DeleteScreenshots;
import captureEasy.UI.JCircularProgressBar;
import captureEasy.UI.PopUp;
import captureEasy.UI.SensorGUI;
import captureEasy.UI.ActionGUIParts.SavePanel;


public class Library extends SharedRepository
{
	/**
	 * @throws IOException 
	 * @Type: File Processing Method
	 * @name= setLog4jProperty(String Field,String value,String comment)
	 */
	public static void setLog4jProperty()
	{
		//
		Properties properties = new Properties();		
		properties.setProperty("log4j.rootLogger", "INFO, FileAppender");
		properties.setProperty("log4j.appender.FileAppender", "org.apache.log4j.FileAppender");
		properties.setProperty("log4j.appender.FileAppender.File", "${logfilename}");
		properties.setProperty("log4j.appender.FileAppender.layout", "org.apache.log4j.PatternLayout");
		properties.setProperty("log4j.appender.FileAppender.append", "true");
		properties.setProperty("log4j.appender.FileAppender.layout.ConversionPattern", "[%-5p] [%d{dd MMM yyyy HH:mm:ss}] [Class: '%C'  Method: '%M'] %n Message: '%m'%n");
		File file = new File(createFolder(Log4jPropertyFilePath));
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(file);
			properties.store(fileOut, "Log4jProperties");
			fileOut.close();
		} catch (IOException e) {}
	}
	public static void logError(String message)
	{
		System.setProperty("logfilename", LogFolderPath + "/Error.log");
		PropertyConfigurator.configure(Log4jPropertyFilePath);
		logger.error(message);
	}

	public static void logDebug(String message)
	{
		System.setProperty("logfilename", LogFolderPath + "/Debug.log");
		PropertyConfigurator.configure(Log4jPropertyFilePath);
		logger.debug(message);
	}
	/**
	 * @throws IOException 
	 * @Type: File Processing Method
	 * @name= updateProperty(String Field,String value)
	 */
	public static void updateProperty(String filePath,String Field,String Value)
	{
		//new StackInfo(Thread.currentThread().getStackTrace());
		Properties properties = new Properties();
		properties.setProperty(Field, Value);
		File file = new File(createFolder(filePath));
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(file,true);
			properties.store(fileOut, "");
			fileOut.close();
		} catch (IOException e) {
			logError(e.getClass().getName()+"  "+PropertyFilePath+" Not found");
			new PopUp("ERROR","error",e.getClass().getName()+"  "+PropertyFilePath+" Not found","Ok, I understood","");
		}
	}
	/** 
	 * @Type: File Processing Method
	 * @name= updateProperty(String Field,String value)
	 */
	public static String getProperty(String filePath,String key) 
	{
		//new StackInfo(Thread.currentThread().getStackTrace());
		String value="";
		Properties properties = new Properties();
		try {
			properties.load(new FileReader(filePath));
			//System.out.println(filePath+"   "+key);

			value=properties.getProperty(key);

		} catch (IOException e) {
			File file = new File(createFolder(filePath));
			FileOutputStream fileOut;
			try {
				//System.err.println("Creating "+filePath);

				fileOut = new FileOutputStream(file,true);
				properties.store(fileOut,"");
				fileOut.close();
			} catch (IOException l) {	System.out.println("Exception1");}
		}
		//System.out.println(value);
		return value;
	}

	/**
	 * @Type: File Processing Method
	 * @name= subFolders(String basepath)
	 */
	public static String subFolders(String basepath)
	{
		String[] monthName = {"January", "February",
				"March", "April", "May", "June", "July",
				"August", "September", "October", "November",
		"December"};
		Calendar cal = Calendar.getInstance();
		String month = monthName[cal.get(Calendar.MONTH)];
		return createFolder(createFolder(basepath+"\\"+month+" "+cal.get(Calendar.YEAR))+"\\"+month+" "+cal.get(Calendar.DAY_OF_MONTH));
		//return basepath;
	}

	/**
	 * @Type: File Processing Method
	 * @name= createFolder(String path)
	 */
	public static String createFolder(String path)
	{
		try{
			createFolder(new File(path).getParent());
			if (! new File(path).exists())
			{
				if(!path.contains("."))
					new File(path).mkdir();
			}
		}
		catch(NullPointerException e){}
		return path;
	}

	/**
	 * @Type: File Processing Method
	 * @name= IsEmpty(String Path) 
	 */

	public static boolean IsEmpty(String Path) 
	{    
		int count=0;
		boolean var=false;
		File directory = new File(Path);
		String[] a=directory.list(); 
		System.out.println(a);
		if(a!=null)
		{
		for(int i=0; i<a.length; i++)
		{
			if (a[i].toLowerCase().contains(".jpeg") || a[i].toLowerCase().contains(".jpg") || a[i].toLowerCase().contains(".png") ||a[i].toLowerCase().contains(".bmp"))
			{
				count++;
			}
		}
	}
		if (count==0)
			var= true;
		else
			var=false;
		return var ;
	}

	/**
	 * @Type: Utility Method
	 * @name= timeStamp()
	 */
	public static String timeStamp()
	{
		return new Timestamp(new Date().getTime()).toString().replaceAll(":", "-");
	}

	/**
	 * 
	 * 
	 * @Type: Screenshot Processing Method
	 * @name= resetClipboard()
	 */
	public static void resetClipboard(String text)
	{
		StringSelection stringSelection = new StringSelection(text);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents((Transferable) stringSelection, null);
	}
	public static int c=0;
	public static boolean captureScreen() {
		String Imageformat=getProperty(PropertyFilePath,"ImageFormat").toLowerCase();
		SensorGUI.frame.setLocation(10000,10000);
		try {
			BufferedImage image ;
			String screenshot_name = String.valueOf(c++) + "." + Imageformat;
			if("true".equalsIgnoreCase(getProperty(PropertyFilePath,"PrtSCSS")))
			{
				SensorGUI.frame.setVisible(false);
				Transferable content = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(DataFlavor.imageFlavor);
		        image= (BufferedImage) content;
				SensorGUI.frame.setVisible(true);
			}
			else
			{
				image = (new Robot()).createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			}
			File file = new File(String.valueOf(createFolder(TempFolder)) + "\\" + screenshot_name);
			ImageIO.write(image, Imageformat, file);
			SensorGUI.label_Count.setText(String.valueOf(new File( TempFolder).listFiles().length)); 

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Exception at captureScreen method");
			if("true".equalsIgnoreCase(getProperty(PropertyFilePath,"PrtSCSS")))
				SensorGUI.frame.setVisible(true);

		}  
		SensorGUI.frame.setLocation(Integer.parseInt(getProperty(PropertyFilePath,"Xlocation")),Integer.parseInt(getProperty(PropertyFilePath,"Ylocation")));
		SensorGUI.frame.setAlwaysOnTop(true);
		return true;
	}

	/**
	 * @Type: Word Processing Method
	 * @name= LoadImages(String path,XWPFRun run)
	 */
	public static int progress=0;
	private static JProgressBar progressBar;
	public static void LoadImages(String path,XWPFRun run) 
	{
		
		File[] files = new File(path).listFiles();
		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				int n1 = extractNumber(o1.getName());
				int n2 = extractNumber(o2.getName());
				return n1 - n2;
			}

			private int extractNumber(String name) {
				int i = 0;
				try {
					int e = name.lastIndexOf('.');
					String number = name.substring(0, e);
					i = Integer.parseInt(number);
				} catch(Exception e) {
					i = 0; 
				}
				return i;
			}
		});
		
		for(double i=0;i<files.length;i++)
		{
			InputStream pic;
			try {
				pic = new FileInputStream(files[(int) i].getPath());
				run.addBreak();
				run.addPicture(pic, XWPFDocument.PICTURE_TYPE_PNG, files[(int) i].getName(), Units.toEMU(470), Units.toEMU(265));
				//progress=(int)((Double.valueOf(i)+1)/Double.valueOf(files.length))*100;
				progressBar.setValue((int)Math.round(((i+1)/files.length)*100));
				System.err.println((int)Math.round(((i+1)/files.length)*100));
				pic.close();
			} catch (InvalidFormatException | IOException e) {
				// Add log
				e.printStackTrace();
			}

		}
	}
	/**
	 * @Type: Word Processing Method
	 * @name= createNewWord(String DocumentPath,String testName)
	 */
	public static void createNewWord(String DocumentPath,String testName)
	{
		try
		{
			progressBar = new JProgressBar();
			progressBar.setBounds(60, 175, 300, 25);
			progressBar.setForeground(Color.GREEN);
			progressBar.setVisible(true);
			SavePanel.panel_Progress.add(progressBar);
			XWPFDocument createNewWordDocument =new XWPFDocument();
			XWPFRun createNewWordRun=createNewWordDocument.createParagraph().createRun();
			LoadImages(TempFolder,createNewWordRun);
			FileOutputStream createNewWordOut=new FileOutputStream(subFolders(DocumentPath)+"\\"+testName+".docx");
			createNewWordDocument.write(createNewWordOut);
			createNewWordOut.flush();
			createNewWordOut.close();
			createNewWordDocument.close();
			createNewWordDocument=null;
			c=0;
			new Thread(new DeleteScreenshots(TempFolder)).start();
			TempFolder=createFolder(System.getProperty("user.dir")+"/CaptureEasy/Temp/")+new Random().nextInt(1000000000);		}
		catch(Exception e)	{	System.err.println("Exception at captureScreen method"); e.printStackTrace();	}
	}

	/**
	 * @Type: Word Processing Method
	 * @name= addToExistingWord(File file)
	 */
	public static void addToExistingWord(String filePath,String fileName)
	{
		FileOutputStream addToExistingWordOut;
		try
		{
			XWPFDocument addToExistingWordDocument = new XWPFDocument(new FileInputStream(filePath));
			XWPFRun addToExistingWordRun = addToExistingWordDocument.getLastParagraph().createRun();		
			LoadImages(TempFolder,addToExistingWordRun);
			if(SavePanel.chckbxOverwriteSelectedFile.isSelected()==true)
			{
				addToExistingWordOut = new FileOutputStream(new File(filePath).getParent()+"\\"+fileName+".docx");
			}
			else
			{
				addToExistingWordOut = new FileOutputStream(filePath);
			}
			addToExistingWordDocument.write(addToExistingWordOut);
			addToExistingWordOut.flush();
			addToExistingWordOut.close();
			addToExistingWordDocument.close();
			addToExistingWordDocument=null;
			c=0;
			new Thread(new DeleteScreenshots(TempFolder)).start();
			TempFolder=createFolder(System.getProperty("user.dir")+"/CaptureEasy/Temp/")+new Random().nextInt(1000000000);
		}
		catch(Exception e){

		}
	}

}

