package window.gameEvents;

import window.GameEngine;
import window.transitionGameEngine.TransitionGameEngine;

public class SwitchEnginesUsingTransitionEvent extends GameEvent {
	
	public SwitchEnginesUsingTransitionEvent(GameEngine nextGameEngine, double transitionTime) {
		this.nextGameEngine = nextGameEngine;
		this.transitionTime = transitionTime;
	}
	
	@Override
	public void execute(GameEngine gameEngine) {
		TransitionGameEngine trans = new TransitionGameEngine(gameEngine, nextGameEngine, transitionTime);
		gameEngine.gameEventQueue().addEvent(new SwitchGameEngineEvent(trans));
	}
	
	private final GameEngine nextGameEngine;
	private final double transitionTime;
	
}
