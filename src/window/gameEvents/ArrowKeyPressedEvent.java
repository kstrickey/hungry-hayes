package window.gameEvents;

import resources.classes.SquareSide;
import window.GameEngine;

public class ArrowKeyPressedEvent extends GameEvent {
	
	public ArrowKeyPressedEvent(SquareSide direction) {
		this.direction = direction;
	}
	
	@Override
	public void execute(GameEngine gameEngine) {
		gameEngine.setArrowKey(direction, true);
	}
	
	private final SquareSide direction;
	
}
