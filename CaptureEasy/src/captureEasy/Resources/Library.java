package captureEasy.Resources;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
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

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import captureEasy.UI.PopUp;
import captureEasy.UI.SensorGUI;
import captureEasy.UI.Components.SavePanel;


public class Library extends SharedRepository
{
	/**
	 * @throws IOException 
	 * @Type: File Processing Method
	 * @name= setLog4jProperty(String Field,String value,String comment)
	 */
	public static void setLog4jProperty()
	{
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
			properties.store(fileOut, "Setup Log4jProperties");
			fileOut.close();
		} catch (IOException e) {}
	}
	public static void logError(Exception e,String message)
	{
		System.setProperty("logfilename", LogFolderPath + "/Error.log");
		PropertyConfigurator.configure(Log4jPropertyFilePath);
		String stack="\n\nStackTrace:";
		StackTraceElement[] trace=e.getStackTrace();
		for(StackTraceElement s:trace)
		{
			stack=stack+"\n\t"+s;
		}
		logger.error(message+stack+"\n\n");
		e.printStackTrace();
	}

	public static void logDebug(String message)
	{
		System.setProperty("logfilename", LogFolderPath + "/Debug.log");
		PropertyConfigurator.configure(Log4jPropertyFilePath);
		logger.info(message+"\n");
	}
	/**
	 * @throws IOException 
	 * @Type: File Processing Method
	 * @name= updateProperty(String Field,String value)
	 */
	public static void updateProperty(String filePath,String Field,String Value)
	{
		Properties properties = new Properties();
		properties.setProperty(Field, Value);
		File file = new File(createFolder(filePath));
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(file,true);
			properties.store(fileOut, "Caution !! Do not edit anything manually. Use UI instead.");
			fileOut.close();
		} catch (IOException e) {
			logError(e,e.getClass().getName()+"  "+filePath+" Not found");
			new PopUp("ERROR","error",e.getClass().getName()+"  "+file.getName()+" Not found. Visit 'Error.log' for more Information","Ok, I understood","").setVisible(true);
		}
	}
	/** 
	 * @Type: File Processing Method
	 * @name= updateProperty(String Field,String value)
	 */
	public static String getProperty(String filePath,String key) 
	{
		String value="";
		Properties properties = new Properties();
		try {
			properties.load(new FileReader(filePath));
			value=properties.getProperty(key);

		} catch (IOException e) {
			File file = new File(createFolder(filePath));
			FileOutputStream fileOut;
			try {
				fileOut = new FileOutputStream(file,true);
				properties.store(fileOut,"");
				fileOut.close();
			} catch (IOException l) {
				logError(l,l.getClass().getName()+"  "+filePath+" Not found");
				new PopUp("ERROR","error",l.getClass().getName()+"  "+file.getName()+" Not found. Visit 'Error.log' for more Information","Ok, I understood","").setVisible(true);

			}
		}
		return value;
	}

	/**
	 * @Type: File Processing Method
	 * @name= subFolders(String basepath)
	 */
	public static String subFolders(String basepath)
	{
		if(getProperty(PropertyFilePath,"ArrangeSSDatewise").equalsIgnoreCase("true"))
		{
			String[] monthName = {"January", "February",
					"March", "April", "May", "June", "July",
					"August", "September", "October", "November",
			"December"};
			Calendar cal = Calendar.getInstance();
			String month = monthName[cal.get(Calendar.MONTH)];
			return createFolder(createFolder(basepath+"\\"+month+" "+cal.get(Calendar.YEAR))+"\\"+month+" "+cal.get(Calendar.DAY_OF_MONTH));
		}
		else
			return basepath;
	}

	/**
	 * @Type: File Processing Method
	 * @name= createFolder(String path)
	 */
	public static String createFolder(String path)
	{
		try{
			if(path!=null){
				createFolder(new File(path).getParent());

				if (! new File(path).exists())
				{

					if(FilenameUtils.getExtension(path).equals(""))
					{
						new File(path).mkdir();
					}
				}
			}
		}
		catch(NullPointerException e){
			logError(e,e.getClass().getName()+" Exception occured while creating folder.\nPath: "+path);
			new PopUp("ERROR","error",e.getClass().getName()+"Exception occured while creating folder. Visit 'Error.log' for more details.","Ok, I understood","").setVisible(true);

		}
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
		if(Imageformat==null)
		{

		}
		SensorGUI.frame.setLocation(10000,10000);
		try {
			BufferedImage image ;
			String screenshot_name = String.valueOf(++c) + "." + Imageformat;
			image = (new Robot()).createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			File file = new File(String.valueOf(createFolder(getProperty(TempFilePath,"TempPath"))) + "\\" + screenshot_name);
			ImageIO.write(image, Imageformat, file);
		} catch (Exception e) 
		{
			logError(e,e.getClass().getName()+" Exception occured while taking screenshot");
			new PopUp("ERROR","error",e.getClass().getName()+"Exception occured while taking screenshot","Ok, I understood","").setVisible(true);
		}  
		SensorGUI.frame.setLocation(Integer.parseInt(getProperty(PropertyFilePath,"Xlocation")),Integer.parseInt(getProperty(PropertyFilePath,"Ylocation")));
		SensorGUI.frame.setAlwaysOnTop(true);
		return true;
	}

	/**
	 * @Type: Word Processing Method
	 * @name= LoadImages(String path,XWPFRun run)
	 */
	public static void LoadImages(String Temppath,XWPFRun run,String FileName) 
	{

		File[] files = new File(Temppath).listFiles();
		SavePanel.lblUpdatingFiles.setText("Sorting files ...");
		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				SavePanel.lblUpdatingFiles.setText("Comparing: "+o1.getName()+"  "+o2.getName());
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

		for(int i=0;i<files.length;i++)
		{
			InputStream pic;
			try {
				pic = new FileInputStream(files[i].getPath());
				SavePanel.lblUpdatingFiles.setText("Storing "+files[i].getName()+" to "+FileName);
				run.addBreak();
				run.addPicture(pic, XWPFDocument.PICTURE_TYPE_PNG, files[i].getName(), Units.toEMU(470), Units.toEMU(265));
				SharedRepository.progress=(int)Math.round(((Double.valueOf(i+1))/Double.valueOf(files.length))*100);
				//System.err.println((int)Math.round(((Double.valueOf(i+1))/Double.valueOf(files.length))*100));

				pic.close();
			} catch (InvalidFormatException | IOException e) {
				logError(e,e.getClass().getName()+" occured while pasteing '"+files[(int) i].getName()+"'. File Path: "+files[i].getPath());
				new PopUp("ERROR","error",e.getClass().getName()+" occured while pasteing '"+files[i].getName()+"'. Visit 'Error.log for more details.","Ok, I understood","").setVisible(true);
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
			SavePanel.lblUpdatingFiles.setText("Creating "+testName+".docx");
			XWPFDocument createNewWordDocument =new XWPFDocument();
			XWPFRun createNewWordRun=createNewWordDocument.createParagraph().createRun();
			LoadImages(getProperty(TempFilePath,"TempPath"),createNewWordRun,testName+".docx");
			SavePanel.lblUpdatingFiles.setText("Saving "+testName+".docx");
			FileOutputStream createNewWordOut=new FileOutputStream(subFolders(DocumentPath)+"\\"+testName+".docx");
			createNewWordDocument.write(createNewWordOut);
			createNewWordOut.flush();
			createNewWordOut.close();
			createNewWordDocument.close();
			createNewWordDocument=null;
			c=0;
			SharedRepository.progress=0;
			SavePanel.lblUpdatingFiles.setText("Saving "+testName+".docx");
			SavePanel.lblUpdatingFiles.setText(""+testName+".docx is ready to use.");
			updateProperty(TempFilePath,"TempPath",createFolder(System.getProperty("user.dir")+"/CaptureEasy/Temp/"+new Random().nextInt(1000000000)));
		}
		catch(Exception e)	{
			logError(e,e.getClass().getName()+" occured while createNewWord. Path :"+DocumentPath+" \nFile name: "+testName);
			new PopUp("ERROR","error",e.getClass().getName()+" occured while createNewWord. Visit 'Error.log for more details.","Ok, I understood","").setVisible(true);
		}
	}

	/**
	 * @Type: Word Processing Method
	 * @name= addToExistingWord(File file)
	 */
	public static void addToExistingWord(String filePath,String fileName)
	{
		FileOutputStream addToExistingWordOut;
		String fOut=null;
		try
		{
			File f=new File(filePath);
			SavePanel.lblUpdatingFiles.setText("Loading "+f.getName());
			XWPFDocument addToExistingWordDocument = new XWPFDocument(new FileInputStream(f));
			SavePanel.lblUpdatingFiles.setText("Fetching existing images");
			XWPFRun addToExistingWordRun = addToExistingWordDocument.getLastParagraph().createRun();		
			if(SavePanel.chckbxOverwriteSelectedFile.isSelected()==true)
			{
				fOut=f.getName();
				SavePanel.lblUpdatingFiles.setText("Overwriting "+fOut);
				LoadImages(getProperty(TempFilePath,"TempPath"),addToExistingWordRun,fOut);
				addToExistingWordOut = new FileOutputStream(f);
			}
			else
			{
				fOut=fileName+".docx";
				SavePanel.lblUpdatingFiles.setText("Creating new file "+fileName+".docx");
				LoadImages(getProperty(TempFilePath,"TempPath"),addToExistingWordRun,fOut);
				addToExistingWordOut = new FileOutputStream(f.getParent()+"\\"+fileName+".docx");
			}
			SavePanel.lblUpdatingFiles.setText("Saving "+fOut);
			addToExistingWordDocument.write(addToExistingWordOut);
			addToExistingWordOut.flush();
			addToExistingWordOut.close();
			addToExistingWordDocument.close();
			addToExistingWordDocument=null;
			c=0;
			SharedRepository.progress=0;
			SavePanel.lblUpdatingFiles.setText(fOut+" is ready to use.");
			updateProperty(TempFilePath,"TempPath",createFolder(System.getProperty("user.dir")+"/CaptureEasy/Temp/"+new Random().nextInt(1000000000)));
		}
		catch(Exception e){
			logError(e,e.getClass().getName()+" occured while addToExistingWord. Path :"+filePath+" \nModified File name: "+fileName);

			new PopUp("ERROR","error",e.getClass().getName()+" occured while addToExistingWord. Visit 'Error.log for more details.","Ok, I understood","").setVisible(true);

		}
	}

	public static void AlwaysClearTemp()
	{
		new Thread(new Runnable(){

			@Override
			public void run() {
				while (!stopThread)
				{
					try{
						File file=new File(createFolder(System.getProperty("user.dir")+"/CaptureEasy/Temp"));
						File[] filesdelete=file.listFiles();
						for(File f:filesdelete)
						{
							if(!f.equals(new File(getProperty(TempFilePath,"TempPath"))))
							{
								FileUtils.deleteDirectory(f);
							}
						}
						Thread.sleep(10000);
					}catch(Exception w){}
				}
			}
		}).start();	
	}
}

