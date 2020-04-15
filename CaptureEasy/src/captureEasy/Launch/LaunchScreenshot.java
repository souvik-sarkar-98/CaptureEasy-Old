package captureEasy.Launch;
import captureEasy.Library.Library;
import captureEasy.Screenshot.ClipboardHandeler.ClipPrtSc;
import captureEasy.UI.ActionGUI;
import captureEasy.UI.SensorGUI;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LaunchScreenshot extends Library{
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
			List<String> tabs=new ArrayList<String>();
			tabs.add("Settings");
			ActionGUI act=new ActionGUI(tabs);
			ActionGUI.dialog.setVisible(true);
			act.settingsPanel.SettingsPane_DocFolderPanel_DocDest.setText("Set document destination folder");
			act.settingsPanel.btnUpdateFrameLocation.setText("Set frame location");
			do{try {Thread.sleep(100);} catch (InterruptedException e) {}}while(!ActionGUI.leaveControl);	
		}
		else if (new File(createFolder(PropertyFilePath)).exists() &&  !IsEmpty(createFolder(TempFolder)))
		{
			List<String> tabs=new ArrayList<String>();
			tabs.add("Action");
			tabs.add("View");
			new ActionGUI(tabs);			
			ActionGUI.dialog.setVisible(true);
			do{try {Thread.sleep(100);} catch (InterruptedException e) {}}while(!ActionGUI.leaveControl);	
			
		}
		
		System.out.println("Out");
		if (new File(createFolder(PropertyFilePath)).exists()/* && IsEmpty(createFolder(TempFolder))*/)
		{
			System.out.println("In");
			new Thread(new ClipPrtSc()).start();
			SensorGUI sn=new SensorGUI();
			SensorGUI.frame.setVisible(true);
			sn.label_Menu.setEnabled(true);
			sn.sensor_panel.setEnabled(true);
			/*while(true)
			{
				//Library.captureScreen();
				try {
					Thread.sleep(5500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
*/		}
	}
}