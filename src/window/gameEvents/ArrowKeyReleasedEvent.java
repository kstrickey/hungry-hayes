package window.gameEvents;

import resources.classes.SquareSide;
import window.GameEngine;

public class ArrowKeyReleasedEvent extends GameEvent {
	
	public ArrowKeyReleasedEvent(SquareSide direction) {
		this.direction = direction;
	}
	
	@Override
	public void execute(GameEngine gameEngine) {
		gameEngine.setArrowKey(direction, false);
	}
	
	private final SquareSide direction;
	
}
