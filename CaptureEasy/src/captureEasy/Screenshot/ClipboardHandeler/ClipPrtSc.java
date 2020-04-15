package captureEasy.Screenshot.ClipboardHandeler;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Random;

import captureEasy.Library.Library;
import captureEasy.Library.SharedRepository;

public class ClipPrtSc extends Library implements Runnable
{
	public static int n=0;
	// Runnable Invoked Method
	@Override
	public void run() 
	{
		Thread.currentThread().setUncaughtExceptionHandler(new Handler());
		try {
			MakeImage();
		} catch (HeadlessException | AWTException e) { e.printStackTrace();}
	}

	//Function to convert Clipboard data to image (Loop) 
	public void MakeImage() throws HeadlessException, AWTException  
	{   
		while(! SharedRepository.stopThread)
		{
			while(SharedRepository.loopControl && ! SharedRepository.stopThread)
			{
				if (Library.c==0)
				{
					new Thread(new DeleteScreenshots(TempFolder)).start();
					TempFolder=createFolder(System.getProperty("user.dir")+"/CaptureEasy/Temp/")+new Random().nextInt(1000000000);
				}

				//if (SharedRepository.IsEmpty(SharedRepository.TempImageFolderPath) || SharedRepository.c > 0)
				{   
					String Temp = null;
					Transferable content = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null); // main exception point
					try {
						Temp=(String) content.getTransferData(DataFlavor.stringFlavor);
					} catch (UnsupportedFlavorException | IOException e1) {}
					if (content.isDataFlavorSupported(DataFlavor.imageFlavor) && !content.isDataFlavorSupported(DataFlavor.stringFlavor) )
					{
						//SharedRepository.b2.setEnabled(false);
						//SharedRepository.updateLabel(Color.RED,"BUSY");
						captureScreen();
						SharedRepository.ClipboardStatus="Useable";
						//SharedRepository.updateLabel(Library.c,"");
						resetClipboard(Temp);
						if (SharedRepository.ClipboardStatus !="Blocked")
						{
							StringSelection stringSelection1 = new StringSelection(Temp);
							Toolkit.getDefaultToolkit().getSystemClipboard().setContents((Transferable) stringSelection1, null);
						}
					}


					try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
					//SharedRepository.b2.setEnabled(true);
					//SharedRepository.updateLabel(Color.GREEN,"READY");
					if (SharedRepository.ClipboardStatus=="Useable" )
					{ 
						//SharedRepository.updateLabel(Library.c,"");
						if (Library.c!=0){
							//SharedRepository.updateLabel(Color.GREEN,"READY");
						}
					}
				}

				if (captureEasy.Library.SharedRepository.ClipboardStatus=="Blocked" )
				{
					Transferable Data=Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null );
					ImageSelection trans = new ImageSelection(new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())));
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents((Transferable) trans,null );
					//SharedRepository.updateLabel(SharedRepository.c,"Clipboard\n Unavailable");
					//SharedRepository.Restore();
					Transferable content = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
					if (content.isDataFlavorSupported(DataFlavor.imageFlavor) && !content.isDataFlavorSupported(DataFlavor.stringFlavor) )
					{
						SharedRepository.ClipboardStatus="Useable";
						//SharedRepository.updateLabel(SharedRepository.c,"");
						Toolkit.getDefaultToolkit().getSystemClipboard().setContents(Data, null);
					}
				}


				/*if(SharedRepository.IsEmpty(SharedRepository.TempImageFolderPath))
				{
					SharedRepository.b2.setEnabled(false);
				}
				else
				{
					SharedRepository.b2.setEnabled(true);
				}

				if (!SharedRepository.IsEmpty(SharedRepository.TempImageFolderPath) && SharedRepository.c > 0)
				{
					SharedRepository.updateLabel(Color.GREEN,"READY");
				}*/
			}
			/*if (SharedRepository.b1.getText().equalsIgnoreCase("RESUME"))
			{
				SharedRepository.updateLabel(Color.RED,"PAUSED");
				SharedRepository.b2.setEnabled(false);
			}
			else
			{
				SharedRepository.b2.setEnabled(true);
				SharedRepository.updateLabel(Color.RED,"BUSY");
			}*/
			try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
		}
	}

}

