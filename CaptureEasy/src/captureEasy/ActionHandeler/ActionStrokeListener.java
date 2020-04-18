package captureEasy.ActionHandeler;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import captureEasy.Resources.Library;
import captureEasy.UI.ActionGUI;

public class ActionStrokeListener extends Library implements NativeKeyListener  {
	public void nativeKeyPressed(NativeKeyEvent e) {
		if (e.getKeyCode() == NativeKeyEvent.VC_PRINTSCREEN) {
			if(ActionGUI.leaveControl)
			{
				captureScreen();
			}
		}
	}
	public void nativeKeyReleased(NativeKeyEvent e) {}

	public void nativeKeyTyped(NativeKeyEvent e) {}

	public void nativeMouseClicked(NativeMouseEvent a) {
		System.out.println("HII "+a.getClickCount());
		System.out.println(System.getProperty("jnativehook.button.multiclick"));
		if(a.getClickCount()==2)
		{
			System.out.println("inside "+a.getClickCount());
			

		}

	}

	public void nativeMousePressed(NativeMouseEvent arg0) {
	}

	public void nativeMouseReleased(NativeMouseEvent arg0) {}
	public void nativeMopuseDragged(NativeMouseEvent arg0) {}

	public void nativeMouseMoved(NativeMouseEvent arg0) {}
	public void nativeMouseDragged(NativeMouseEvent arg0) {}
}


