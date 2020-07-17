package window.exploringGameEngine.simObjects.background;

import java.awt.Rectangle;

import window.exploringGameEngine.gameEvents.SwitchRealmEvent;
import window.exploringGameEngine.simObjects.SimObject;
import window.exploringGameEngine.simObjects.hayes.Hayes;
import window.gameEvents.GameEventQueue;

/**
 * A Block that, if touched by Hayes, will transport him to a different GameEngine.
 * The next GameEngine is determined by final field destinationGameEngine.
 * If destinationGameEngine == null, then it will act as the homeGameEngine in the WindowEngine.
 *
 */
public class Portal extends Block {

	public Portal(int x, int y, int width, int height, int destinationRealmID) {
		super(x, y, width, height);
		this.destinationRealmID = destinationRealmID;
	}
	
	@Override
	public void checkAndMoveSimObject(SimObject simObject, GameEventQueue gameEventQueue) {
		if (simObject instanceof Hayes && !((Hayes) simObject).isStunned()) {
			Rectangle intersection = getArea().intersection(simObject.rectangleArea());
			if (intersection.contains(simObject.center())) {
				// If Hayes's center is inside the portal, fire event to switch GameEngines
				gameEventQueue.addEvent(new SwitchRealmEvent(destinationRealmID));
//				gameEventQueue.addEvent(new SwitchGameEngineEvent(destinationGameEngine));
			}
		} else {
			// Acts as a regular Block toward all non-Hayes SimObjects and toward stunned Hayes
			super.checkAndMoveSimObject(simObject, gameEventQueue);
		}
	}
	
	private final int destinationRealmID;
	
}
