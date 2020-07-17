package window.exploringGameEngine.simObjects.inanimates;

import java.awt.image.BufferedImage;

import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.gameEvents.SwitchRealmEvent;
import window.exploringGameEngine.realm.RealmFactory;
import window.exploringGameEngine.simObjects.hayes.Hayes;
import window.gameEvents.AddMessageImageEvent;

public class Baby extends InanimateObject {
	
    public static final BufferedImage IMAGE = PaintMachine.getImageFromName("inanimate/baby.png");
    
    public static final BufferedImage SUCCESS_MESSAGE = PaintMachine.getImageFromName("textbox/retrieved baby.png");
    
    /**
     * Construct a new Baby.
     */
    public Baby (double x, double y, int realmWidth, int realmHeight) {
        super(IMAGE, x, y, IMAGE.getWidth(), IMAGE.getHeight(), realmWidth, realmHeight);
    }

    @Override
    public void tick(double timeElapsed, ExploringGameEventQueue gameEventQueue, Hayes hayes) {
        if (touching(hayes)) {
        	if (readyToSwitch) {
        		// Switch back to Central
        		removeSelfFromRealm(gameEventQueue);
        		hayes.setRetrievedBaby(true);
        		gameEventQueue.addEvent(new SwitchRealmEvent(RealmFactory.ID_CENTRAL));
        	} else {
        		// Display message, and set readyToSwitch to true
        		gameEventQueue.addEvent(new AddMessageImageEvent(SUCCESS_MESSAGE));
        		readyToSwitch = true;
        	}
        }        
    }
    
    private boolean readyToSwitch = false;			// will be true when ready to switch Realms
    
}
