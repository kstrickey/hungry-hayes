package window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import resources.classes.SquareSide;
import window.gameEvents.ArrowKeyPressedEvent;
import window.gameEvents.ArrowKeyReleasedEvent;
import window.gameEvents.GameEventQueue;
import window.gameEvents.PunchKeyPressedEvent;
import window.gameEvents.PunchKeyReleasedEvent;

/**
 * A class designed to interpret KeyEvents fired during program execution.
 *
 */
public class KeyboardInputHandler implements KeyListener {
	
	public KeyboardInputHandler() {
		gameEventQueue = null;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (gameEventQueue != null) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_D:
					gameEventQueue.addEvent(new ArrowKeyPressedEvent(SquareSide.RIGHT));
					break;
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_S:
					gameEventQueue.addEvent(new ArrowKeyPressedEvent(SquareSide.BOTTOM));
					break;
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_A:
					gameEventQueue.addEvent(new ArrowKeyPressedEvent(SquareSide.LEFT));
					break;
				case KeyEvent.VK_UP:
				case KeyEvent.VK_W:
					gameEventQueue.addEvent(new ArrowKeyPressedEvent(SquareSide.TOP));
					break;
				case KeyEvent.VK_SPACE:
					gameEventQueue.addEvent(new PunchKeyPressedEvent());
					break;
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if (gameEventQueue != null) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_D:
					gameEventQueue.addEvent(new ArrowKeyReleasedEvent(SquareSide.RIGHT));
					break;
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_S:
					gameEventQueue.addEvent(new ArrowKeyReleasedEvent(SquareSide.BOTTOM));
					break;
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_A:
					gameEventQueue.addEvent(new ArrowKeyReleasedEvent(SquareSide.LEFT));
					break;
				case KeyEvent.VK_UP:
				case KeyEvent.VK_W:
					gameEventQueue.addEvent(new ArrowKeyReleasedEvent(SquareSide.TOP));
					break;
				case KeyEvent.VK_SPACE:
					gameEventQueue.addEvent(new PunchKeyReleasedEvent());
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public void setGameEventQueue(GameEventQueue gameEventQueue) {
		this.gameEventQueue = gameEventQueue;
	}
	
	private GameEventQueue gameEventQueue;
	
}
