package captureEasy.Screenshot.ClipboardHandeler;

import java.lang.Thread.UncaughtExceptionHandler;

import captureEasy.Library.SharedRepository;

public class Handler implements UncaughtExceptionHandler
{
	   public void uncaughtException(Thread t, Throwable e)
	   {
		    System.out.println("UncaughtExceptionHandler Launched "+(++ClipPrtSc.n)+" Times");
		    SharedRepository.ClipboardStatus="Blocked";
	        new Thread(new ClipPrtSc()).start();
	   }
}


