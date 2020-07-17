package window.gameEvents;

import java.awt.image.BufferedImage;

import window.GameEngine;

/**
 * A direct subclass of GameEvent (can be used by any GameEngine).
 * Adds a BufferedImage to the currentMessageQueue of the gameEngine.
 *
 */
public class AddMessageImageEvent extends GameEvent {
	
	public AddMessageImageEvent(BufferedImage image) {
		this.image = image;
	}
	
	@Override
	public void execute(GameEngine gameEngine) {
		gameEngine.addMessage(image);
	}
	
	private final BufferedImage image;
	
}
