package window.exploringGameEngine.gameEvents;

import window.GameEngine;
import window.exploringGameEngine.ExploringGameEngine;
import window.exploringGameEngine.realm.RealmFactory;
import window.gameEvents.SwitchEnginesUsingTransitionEvent;
import window.transitionGameEngine.TransitionGameEngine;

public class SwitchRealmEvent extends ExploringGameEvent {
	
	/**
	 * Constructs a new SwitchRealmEvent.
	 * @param nextRealmID
	 */
	public SwitchRealmEvent(int nextRealmID) {
		this.nextRealmID = nextRealmID;
	}
	
	@Override
	public void exploringExecute(ExploringGameEngine exploringGameEngine) {
		GameEngine ultimateGameEngine = null;
		if (nextRealmID != RealmFactory.ID_CENTRAL) {
			ultimateGameEngine = new ExploringGameEngine(nextRealmID);
		}
		exploringGameEngine.gameEventQueue().addEvent(new SwitchEnginesUsingTransitionEvent(ultimateGameEngine, TransitionGameEngine.DEFAULT_TRANSITION_TIME));
	}
	
	private final int nextRealmID;

}
