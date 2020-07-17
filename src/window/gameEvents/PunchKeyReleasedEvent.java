package window.gameEvents;

import window.GameEngine;

public class PunchKeyReleasedEvent extends GameEvent {

	@Override
	public void execute(GameEngine gameEngine) {
		gameEngine.setPunchKey(false);
		gameEngine.pollCurrentMessageQueue();		// remove head of message queue
	}

}
