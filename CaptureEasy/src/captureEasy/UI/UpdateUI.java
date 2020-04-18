package captureEasy.UI;

import java.io.File;

import captureEasy.Resources.Library;

public class UpdateUI extends Library implements Runnable {
	

	@Override
	public void run() {
		while (!stopThread)
		{
			try{
				int count=new File(getProperty(TempFilePath,"TempPath")).listFiles().length;
				SensorGUI.label_Count.setText(String.valueOf(count)); 
			}catch(Exception w){}

		}
	}

}
