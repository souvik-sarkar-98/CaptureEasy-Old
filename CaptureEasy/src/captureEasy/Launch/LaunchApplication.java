package captureEasy.Launch;
import captureEasy.ActionHandeler.ActionStrokeListener;
import captureEasy.Resources.Library;
import captureEasy.Resources.Update;
import captureEasy.UI.ActionGUI;
import captureEasy.UI.PopUp;
import captureEasy.UI.SensorGUI;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jnativehook.GlobalScreen;

public class LaunchApplication extends Library{
	static boolean TempNeeded=true;
	public static void main(String args[]) throws Exception
	{
		Perform();
	}
	public static void Perform() 
	{	
		if(!(new File(createFolder(Log4jPropertyFilePath)).exists()))
		{
			setLog4jProperty();
		}

		if(!new File(createFolder(PropertyFilePath)).exists())
		{
			getProperty(TempFilePath,"TempPath");
			updateProperty(TempFilePath,"TempPath",createFolder(System.getProperty("user.dir")+"/CaptureEasy/Temp/"+new Random().nextInt(1000000000)));
			List<String> tabs=new ArrayList<String>();
			tabs.add("Settings");
			ActionGUI act=new ActionGUI(tabs);
			ActionGUI.dialog.setVisible(true);
			act.settingsPanel.chckbxUpdateDocumentDestination.setText("Set document destination folder");
			act.settingsPanel.btnUpdateFrameLocation.setText("Set frame location");
			act.settingsPanel.chckbxUpdateDocumentDestination.setSelected(true);
			act.settingsPanel.SettingsPane_DocFolderPanel_textField_DocDestFolder.setEnabled(true);
			act.settingsPanel.SettingsPane_DocFolderPanel_Chooser.setEnabled(true);
			act.settingsPanel.comboBox_CaptureKey.setSelectedIndex(0);
			act.settingsPanel.comboBox_ImageFormat.setSelectedIndex(0);
					
			do{try {Thread.sleep(100);} catch (InterruptedException e) {}}while(!ActionGUI.leaveControl);	
		}
		else if (new File(createFolder(PropertyFilePath)).exists() &&  !IsEmpty(createFolder(getProperty(TempFilePath,"TempPath"))))
		{
			List<String> tabs=new ArrayList<String>();
			tabs.add("Action");
			tabs.add("View");
			new ActionGUI(tabs);			
			ActionGUI.dialog.setVisible(true);
			do{try {Thread.sleep(100);} catch (InterruptedException e) {}}while(!ActionGUI.leaveControl);	
			TempNeeded=false;
		}



		if (new File(createFolder(PropertyFilePath)).exists()/* && IsEmpty(createFolder(TempFolder))*/)
		{
			if(TempNeeded)
			{
				updateProperty(TempFilePath,"TempPath",createFolder(System.getProperty("user.dir")+"/CaptureEasy/Temp/"+new Random().nextInt(1000000000)));
			}
			GlobalScreen.addNativeKeyListener(new ActionStrokeListener());
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
		}
	}
}