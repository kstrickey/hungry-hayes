package window.gameEvents;

import window.GameEngine;

/**
 * Event fired when the current GameEngine is finished and needs to be switched to a different one.
 *
 */
public class SwitchGameEngineEvent extends GameEvent {
	
	public SwitchGameEngineEvent(GameEngine nextGameEngine) {
		this.nextGameEngine = nextGameEngine;
	}
	
	@Override
	public void execute(GameEngine gameEngine) {
		gameEngine.markForDestruction(nextGameEngine);
	}
	
	private final GameEngine nextGameEngine;
	
}
