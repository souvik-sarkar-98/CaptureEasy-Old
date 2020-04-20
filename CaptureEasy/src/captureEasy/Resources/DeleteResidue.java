package captureEasy.Resources;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class DeleteResidue extends Library implements Runnable {

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

}
