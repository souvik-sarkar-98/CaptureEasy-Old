package captureEasy.Resources;

import java.io.File;

import captureEasy.UI.ActionGUI;
import captureEasy.UI.SensorGUI;


public class Update extends Library implements Runnable {
	

	@Override
	public void run() {
		while (!stopThread)
		{
			try{
				int count=new File(getProperty(TempFilePath,"TempPath")).listFiles().length;
				SensorGUI.label_Count.setText(String.valueOf(count)); 
				SensorGUI.frame.setAlwaysOnTop(true);
			}catch(Exception w){}
			try{
			if(ActionGUI.dialog !=null && SensorGUI.window!=null && ActionGUI.dialog.isVisible())
			{
			
				SensorGUI.window.dispose();
				SensorGUI.window=null;
				
			}
			}catch(Exception e){e.printStackTrace();}
		}
	}

}
