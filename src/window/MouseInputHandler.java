package window;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import window.gameEvents.GameEventQueue;
import window.gameEvents.MousePressedOrReleasedEvent;
import window.gameEvents.SetMousePointEvent;

/**
 * A class designed to interpret MouseEvents.
 *
 */
public class MouseInputHandler extends MouseInputAdapter {
	
	public MouseInputHandler() {
		gameEventQueue = null;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (gameEventQueue != null) {
			gameEventQueue.addEvent(new MousePressedOrReleasedEvent(true));
			gameEventQueue.addEvent(new SetMousePointEvent(e.getPoint()));
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (gameEventQueue != null) {
			gameEventQueue.addEvent(new MousePressedOrReleasedEvent(false));
			gameEventQueue.addEvent(new SetMousePointEvent(e.getPoint()));
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if (gameEventQueue != null) {
			gameEventQueue.addEvent(new SetMousePointEvent(e.getPoint()));
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (gameEventQueue != null) {
			gameEventQueue.addEvent(new SetMousePointEvent(e.getPoint()));
		}
	}
	
	public void setGameEventQueue(GameEventQueue gameEventQueue) {
		this.gameEventQueue = gameEventQueue;
	}
	
	private GameEventQueue gameEventQueue;
	
}
