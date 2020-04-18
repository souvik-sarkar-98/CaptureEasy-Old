package captureEasy.ActionHandeler;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import captureEasy.Resources.Library;

public class ActionStrokeListener extends Library implements NativeKeyListener,NativeMouseInputListener  {
	public void nativeKeyPressed(NativeKeyEvent e) {
		if (e.getKeyCode() == NativeKeyEvent.VC_PRINTSCREEN) {
			captureScreen();
		}
	}
	public void nativeKeyReleased(NativeKeyEvent e) {}

	public void nativeKeyTyped(NativeKeyEvent e) {}

	@Override
	public void nativeMouseClicked(NativeMouseEvent arg0) {
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent arg0) {
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent arg0) {}
	public void nativeMopuseDragged(NativeMouseEvent arg0) {}

	@Override
	public void nativeMouseMoved(NativeMouseEvent arg0) {}
	@Override
	public void nativeMouseDragged(NativeMouseEvent arg0) {}
}