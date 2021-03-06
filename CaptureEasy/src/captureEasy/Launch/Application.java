package captureEasy.Launch;
import captureEasy.Resources.DetectKeypress;
import captureEasy.Resources.Library;
import captureEasy.Resources.SharedRepository;
import captureEasy.Resources.Update;
import captureEasy.UI.ActionGUI;
import captureEasy.UI.PopUp;
import captureEasy.UI.SensorGUI;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jnativehook.GlobalScreen;

public class Application extends Library{
	static boolean TempNeeded=true;
	public static void main(String args[]) throws Exception
	{
		launch();
	}
	public static void launch() 
	{	
		if(!(new File(createFolder(Log4jPropertyFilePath)).exists()) || getProperty(Log4jPropertyFilePath,"log4j.rootLogger")==null || getProperty(Log4jPropertyFilePath,"log4j.rootLogger").replaceAll("\\s", "")=="")
		{
			setLog4jProperty();
		}

		if(!new File(createFolder(PropertyFilePath)).exists() || getProperty(PropertyFilePath,"DocPath")==null || getProperty(PropertyFilePath,"DocPath").replaceAll("\\s", "")=="")
		{
			getProperty(TempFilePath,"TempPath");
			updateProperty(TempFilePath,"TempPath",createFolder(System.getProperty("user.dir")+"/CaptureEasy/Temp/"+new Random().nextInt(1000000000)));
			List<String> tabs=new ArrayList<String>();
			tabs.add("Settings");
			new ActionGUI(tabs);
			ActionGUI.dialog.setVisible(true);
			ActionGUI.settingsPanel.DocumentDestination.setText("Set document destination folder");
			ActionGUI.settingsPanel.btnUpdateFrameLocation.setText("Set frame location");
			ActionGUI.settingsPanel.SettingsPane_DocFolderPanel_textField_DocDestFolder.setEnabled(true);
			ActionGUI.settingsPanel.SettingsPane_DocFolderPanel_Chooser.setEnabled(true);
			ActionGUI.tagDrop=false;
			ActionGUI.settingsPanel.comboBox_CaptureKey.setSelectedIndex(0);
			ActionGUI.settingsPanel.comboBox_ImageFormat.setSelectedIndex(0);

			do{try {Thread.sleep(100);} catch (InterruptedException e) {}}while(!ActionGUI.leaveControl);	
		}
		else if (new File(createFolder(PropertyFilePath)).exists() &&  !IsEmpty(createFolder(getProperty(TempFilePath,"TempPath"))))
		{
			Library.c=lastFileName(getProperty(TempFilePath,"TempPath"));
			try{
				if(getProperty(DataFilePath,"TempCode").equals(new File(getProperty(TempFilePath,"TempPath")).getName()))
				{
					String[] datas=getProperty(DataFilePath,"Comments").split("_");
					for(int i=0;i<datas.length;i++)
					{
						if(datas[i]!=null && !datas[i].equals("") && datas[i].split("->").length==2)
							SharedRepository.comments.put(datas[i].split("->")[0], datas[i].split("->")[1]);
					}
				}
			}catch(Exception w){}
			List<String> tabs=new ArrayList<String>();
			tabs.add("Action");
			tabs.add("View");

			ActionGUI act=new ActionGUI(tabs);			
			ActionGUI.dialog.setVisible(true);
			act.viewPanel.lblExit.setEnabled(false);
			ActionGUI.actionPanel.rdbtnSavePreviousWork.setEnabled(false);
			do{try {Thread.sleep(100);} catch (InterruptedException e) {}}while(!ActionGUI.leaveControl);	
			TempNeeded=false;

		}



		if (new File(createFolder(PropertyFilePath)).exists()/* && IsEmpty(createFolder(TempFolder))*/)
		{
			if(TempNeeded)
			{
				updateProperty(TempFilePath,"TempPath",createFolder(System.getProperty("user.dir")+"/CaptureEasy/Temp/"+new Random().nextInt(1000000000)));
			}
			GlobalScreen.addNativeKeyListener(new DetectKeypress());
			try {
				GlobalScreen.registerNativeHook();
			} catch (Exception e) {
				PopUp p=new PopUp("ERROR","Error","Exception occured while registering the native hook. As a result system will not able to take screenshots using keys.","Ok, I understoood","Exit Application");
				p.setVisible(true);
				logError(e,"Exception occured while registering the native hook. As a result system will not able to take screenshots using keys.");
			}

			SensorGUI sn=new SensorGUI();
			SensorGUI.frame.setVisible(true);
			sn.label_Menu.setEnabled(true);
			sn.sensor_panel.setEnabled(true);
			new Thread(new Update()).start();
			AlwaysClearTemp();

		}
	}
}