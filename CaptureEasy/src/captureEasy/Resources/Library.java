package captureEasy.Resources;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

import captureEasy.UI.ActionGUI;
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
	public static String getSubFolders(String basepath,String folderName)
	{
		String[] monthName = {"January", "February",
				"March", "April", "May", "June", "July",
				"August", "September", "October", "November",
		"December"};
		Calendar cal = Calendar.getInstance();
		String month = monthName[cal.get(Calendar.MONTH)];
		if("true".equalsIgnoreCase(getProperty(PropertyFilePath,"showFolderNameField")))
		{
			if("true".equalsIgnoreCase(getProperty(PropertyFilePath,"setFolderNameMandatory")))
			{
				return basepath+"\\"+folderName;
			}
			else
			{
				if(folderName.replaceAll("\\s", "").equals(""))
					return basepath+"\\"+month+" "+cal.get(Calendar.YEAR)+"\\"+month+" "+cal.get(Calendar.DAY_OF_MONTH); 
				else
					return basepath+"\\"+folderName;
			}
		}
		else
		{
			return basepath+"\\"+month+" "+cal.get(Calendar.YEAR)+"\\"+month+" "+cal.get(Calendar.DAY_OF_MONTH);
		}
	}

	/**
	 * @Type: File Processing Method
	 * @name= createSubFolders(String basepath)
	 */
	public static String createSubFolders(String basepath,String folderName)
	{
		String[] monthName = {"January", "February",
				"March", "April", "May", "June", "July",
				"August", "September", "October", "November",
		"December"};
		Calendar cal = Calendar.getInstance();
		String month = monthName[cal.get(Calendar.MONTH)];
		if("true".equalsIgnoreCase(getProperty(PropertyFilePath,"showFolderNameField")))
		{
			if("true".equalsIgnoreCase(getProperty(PropertyFilePath,"setFolderNameMandatory")))
			{
				return createFolder(basepath+"\\"+folderName);
			}
			else
			{
				if(folderName.replaceAll("\\s", "").equals(""))
					return createFolder(createFolder(basepath+"\\"+month+" "+cal.get(Calendar.YEAR))+"\\"+month+" "+cal.get(Calendar.DAY_OF_MONTH)); 
				else
					return createFolder(basepath+"\\"+folderName);
			}
		}
		else
		{
			return createFolder(createFolder(basepath+"\\"+month+" "+cal.get(Calendar.YEAR))+"\\"+month+" "+cal.get(Calendar.DAY_OF_MONTH));
		}
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
	public static int lastFileName(String dirPath){
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return 0;
		}
		int res=0;
		for (int i = 0; i < files.length; i++) {
			int in=Integer.parseInt(files[i].getName().substring(0,files[i].getName().indexOf(".")));
			if (in>res) {
				res=in;
			}
		}
		return res;
	}
	public static int c=0;
	public static boolean captureScreen() {
		String Imageformat=getProperty(PropertyFilePath,"ImageFormat").toLowerCase();
		if(Imageformat==null)
		{
			List<String> tabs=new ArrayList<String>();
			tabs.add("Settings");
			new ActionGUI(tabs);
			ActionGUI.dialog.setVisible(true);
			ActionGUI.settingsPanel.DocumentDestination.setText("Set document destination folder");
			ActionGUI.settingsPanel.btnUpdateFrameLocation.setText("Set frame location");
			ActionGUI.settingsPanel.SettingsPane_DocFolderPanel_textField_DocDestFolder.setEnabled(true);
			ActionGUI.settingsPanel.SettingsPane_DocFolderPanel_Chooser.setEnabled(true);
			ActionGUI.tagDrop=false;
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
	public static void loadImages(String Temppath,XWPFRun run,String FileName) 
	{

		File[] files = new File(Temppath).listFiles();
		SavePanel.lblUpdatingFiles.setText("Sorting files ...");
		sortFiles(files);

		for(int i=0;i<files.length;i++)
		{
			InputStream pic;
			try {
				pic = new FileInputStream(files[i].getPath());
				SavePanel.lblUpdatingFiles.setText("Storing "+files[i].getName()+" to "+FileName);
				if(comments.get(files[i].getName())!=null)
					run.setText(comments.get(files[i].getName()));
				//XWPFPicture picture=
						run.addPicture(pic, XWPFDocument.PICTURE_TYPE_PNG, files[i].getName(), Units.toEMU(470), Units.toEMU(265));
				//picture.getCTPicture().getSpPr().addNewLn().setW(Units.toEMU(1.5));			
				//picture.getCTPicture().getSpPr().getLn().addNewPrstDash();
				SharedRepository.progress=(int)Math.round(((Double.valueOf(i+1))/Double.valueOf(files.length))*100);
				pic.close();
				if(i%2==0)
					run.addBreak();
				else 
					run.addBreak(BreakType.PAGE);
			} catch (InvalidFormatException | IOException e) {
				logError(e,e.getClass().getName()+" occured while pasteing '"+files[(int) i].getName()+"'. File Path: "+files[i].getPath());
				new PopUp("ERROR","error",e.getClass().getName()+" occured while pasteing '"+files[i].getName()+"'. Visit 'Error.log for more details.","Ok, I understood","").setVisible(true);
			}

		}
	}
	public static void loadImages(String tempPath,Document document,String FileName,String msg)
	{
		SavePanel.lblUpdatingFiles.setText("Sorting "+msg+" files ...");
		File[] tempFiles = new File(tempPath).listFiles();
		sortFiles(tempFiles);
		for(int i=0;i<tempFiles.length;i++)
		{
			try {                 
				Image image = new Image(ImageDataFactory.create(tempFiles[i].getPath()));                        
				image.setAutoScale(true);
				SavePanel.lblUpdatingFiles.setText("Storing "+msg+" file "+tempFiles[i].getName()+" to "+FileName);
				if(comments.get(tempFiles[i].getName())!=null)
					document.add(new Paragraph(comments.get(tempFiles[i].getName())));
				document.add(image); 
				SharedRepository.progress=(int)Math.round(((Double.valueOf(i+1))/Double.valueOf(tempFiles.length))*100);
				if(i%2==0)
					document.add(new Paragraph("\n"));
				else 
					document.add(new AreaBreak());
				}catch(Exception e){
				SavePanel.lblUpdatingFiles.setText(e.getClass().getSimpleName()+" Occured ");
			}
		}  
	}



	public static void sortFiles(File[] files) {
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
	}

	public static void SaveAsPDF(String DocumentPath,String fileName,String foldername)
	{
		String tempPath = getProperty(TempFilePath,"TempPath");
		try {
			SavePanel.lblUpdatingFiles.setText("Creating "+fileName+".pdf");
			Document document = new Document(new PdfDocument(new PdfWriter(createSubFolders(DocumentPath,foldername)+"\\"+fileName+".pdf")));
			loadImages(tempPath,document,fileName+".pdf","");
			document.close();
			SharedRepository.progress=0;
			SavePanel.lblUpdatingFiles.setText("Saving "+fileName+".pdf");
			SavePanel.lblUpdatingFiles.setText(""+fileName+".pdf"+" is ready to use.");
			if(SavePanel.rdbtnNo.isSelected())
			{
				Library.c=0;
				updateProperty(TempFilePath,"TempPath",createFolder(System.getProperty("user.dir")+"/CaptureEasy/Temp/"+new Random().nextInt(1000000000)));
				comments.clear();
			}
		} catch (FileNotFoundException e) {
			SavePanel.lblUpdatingFiles.setText(e.getClass().getSimpleName()+" Occured ");

		}              


	}
	public static void SaveAsPDFFromWord(String filePath,String fileName)
	{
		Document document = null;
		File f = null;
		String extractedPath=createFolder(System.getProperty("user.dir")+"\\CaptureEasy\\ExtractedImages");
		String tempPath = getProperty(TempFilePath,"TempPath");
		try {
			if(SavePanel.chckbxOverwriteSelectedFile.isSelected()==true)
			{
				f=new File(filePath.replace(".docx", ".pdf"));
				document = new Document(new PdfDocument(new PdfWriter(f.getParent()+"\\"+fileName+".pdf")));
			}
			else
			{
				f=new File(filePath.replace(".docx", ".pdf"));
				document = new Document(new PdfDocument(new PdfWriter(f.getPath())));
			}
			SavePanel.lblUpdatingFiles.setText("Creating "+f.getName());
			SavePanel.lblUpdatingFiles.setText("Getting previous files ...");
			int i=1;
			try{
				
				@SuppressWarnings("resource")
				XWPFDocument docx=new XWPFDocument(new FileInputStream(filePath));
				List<XWPFPictureData> picture=docx.getAllPictures();
				Iterator<XWPFPictureData> iterator=picture.iterator();
				while(iterator.hasNext()){
					XWPFPictureData pic=iterator.next();		
					SavePanel.lblUpdatingFiles.setText("Getting previous file "+i+"."+ pic.suggestFileExtension());
					ImageIO.write(ImageIO.read(new ByteArrayInputStream(pic.getData())), pic.suggestFileExtension(), 
							new File(System.getProperty("user.dir")+"\\CaptureEasy\\ExtractedImages\\"+pic.getFileName().replace("image", "")));
					SharedRepository.progress=(int)Math.round(((Double.valueOf(i))/Double.valueOf(picture.size()))*100);
					i++;
				}
				SharedRepository.progress=0;
				SavePanel.ProgressBar.setValue(0);
				SavePanel.panel_Progress.repaint();
				SavePanel.lblUpdatingFiles.setText("Storing previous files..");
				loadImages(extractedPath,document,f.getName(),"previous");
			}catch(Exception e1){

			}
			SharedRepository.progress=0;
			SavePanel.ProgressBar.setValue(0);
			SavePanel.panel_Progress.repaint();
			SavePanel.lblUpdatingFiles.setText("Storing current files..");
			loadImages(tempPath,document,f.getName(),"current");
			document.close();
			SharedRepository.progress=0;
			SavePanel.lblUpdatingFiles.setText("Saving "+fileName+".pdf");
			SavePanel.lblUpdatingFiles.setText(""+fileName+".pdf"+" is ready to use.");

			if(SavePanel.rdbtnNo.isSelected())
			{
				Library.c=0;
				updateProperty(TempFilePath,"TempPath",createFolder(System.getProperty("user.dir")+"/CaptureEasy/Temp/"+new Random().nextInt(1000000000)));
				comments.clear();
			}
		} catch (FileNotFoundException e) {
			SavePanel.lblUpdatingFiles.setText(e.getClass().getSimpleName()+" Occured ");

		}              
		try {
			FileUtils.deleteDirectory(new File (extractedPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	/**
	 * @Type: Word Processing Method
	 * @name= createNewWord(String DocumentPath,String testName)
	 */
	public static void createNewWord(String DocumentPath,String fileName,String foldername)
	{
		try
		{
			SavePanel.lblUpdatingFiles.setText("Creating "+fileName+".docx");
			XWPFDocument createNewWordDocument =new XWPFDocument();
			XWPFRun createNewWordRun=createNewWordDocument.createParagraph().createRun();
			loadImages(getProperty(TempFilePath,"TempPath"),createNewWordRun,fileName+".docx");
			SavePanel.lblUpdatingFiles.setText("Saving "+fileName+".docx");
			FileOutputStream createNewWordOut=new FileOutputStream(createSubFolders(DocumentPath,foldername)+"\\"+fileName+".docx");
			createNewWordDocument.write(createNewWordOut);
			createNewWordOut.flush();
			createNewWordOut.close();
			createNewWordDocument.close();
			createNewWordDocument=null;
			SharedRepository.progress=0;
			SavePanel.lblUpdatingFiles.setText(""+fileName+".docx is ready to use.");
			if(SavePanel.rdbtnNo.isSelected())
			{
				Library.c=0;
				updateProperty(TempFilePath,"TempPath",createFolder(System.getProperty("user.dir")+"/CaptureEasy/Temp/"+new Random().nextInt(1000000000)));
				comments.clear();
			}
		}
		catch(Exception e)	{
			logError(e,e.getClass().getName()+" occured while createNewWord. Path :"+DocumentPath+" \nFile name: "+fileName);
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
				loadImages(getProperty(TempFilePath,"TempPath"),addToExistingWordRun,fOut);
				addToExistingWordOut = new FileOutputStream(f);
			}
			else
			{
				fOut=fileName+".docx";
				SavePanel.lblUpdatingFiles.setText("Creating new file "+fileName+".docx");
				loadImages(getProperty(TempFilePath,"TempPath"),addToExistingWordRun,fOut);
				addToExistingWordOut = new FileOutputStream(f.getParent()+"\\"+fileName+".docx");
			}
			SavePanel.lblUpdatingFiles.setText("Saving "+fOut);
			addToExistingWordDocument.write(addToExistingWordOut);
			addToExistingWordOut.flush();
			addToExistingWordOut.close();
			addToExistingWordDocument.close();
			addToExistingWordDocument=null;
			SavePanel.lblUpdatingFiles.setText(fOut+" is ready to use.");
			SharedRepository.progress=0;
			if(SavePanel.rdbtnNo.isSelected())
			{
				c=0;
				updateProperty(TempFilePath,"TempPath",createFolder(System.getProperty("user.dir")+"/CaptureEasy/Temp/"+new Random().nextInt(1000000000)));
				comments.clear();
			}
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

