package window.exploringGameEngine.gameEvents;

import window.GameEngine;
import window.exploringGameEngine.ExploringGameEngine;
import window.gameEvents.GameEvent;

public abstract class ExploringGameEvent extends GameEvent {
	
	@Override
	public void execute(GameEngine exploringGameEngine) {
		exploringExecute((ExploringGameEngine) exploringGameEngine);
	}
	
	public abstract void exploringExecute(ExploringGameEngine exploringGameEngine);
	
}
