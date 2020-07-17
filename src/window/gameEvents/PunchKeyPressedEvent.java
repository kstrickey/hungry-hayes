package window.gameEvents;

import window.GameEngine;

public class PunchKeyPressedEvent extends GameEvent {

	@Override
	public void execute(GameEngine gameEngine) {
		gameEngine.setPunchKey(true);
	}

}
