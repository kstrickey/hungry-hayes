package window.exploringGameEngine;

import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;

public interface Tickable {
	
	public void tick(double timeElapsed, ExploringGameEventQueue gameEventQueue);
	
}
