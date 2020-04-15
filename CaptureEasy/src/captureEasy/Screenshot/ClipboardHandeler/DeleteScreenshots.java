package captureEasy.Screenshot.ClipboardHandeler;

import java.io.File;

import captureEasy.Library.Library;

public class DeleteScreenshots extends Library implements Runnable
{
	String path;
	public DeleteScreenshots(String path)
	{
		this.path=path;
	}
	@Override
	public void run() {
		
			System.out.println(path);
		/*//	do{
				try
		    	{
			    for (File file: new File(path).listFiles()) 
			    {
			    	file.delete();
			    }
		    	}catch(Exception e){e.printStackTrace();	
				System.err.println("Exception while delete");}
				System.out.println(IsEmpty(path));
			//}while(!IsEmpty(path) );
*/	    
		
	}
}
