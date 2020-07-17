package window.exploringGameEngine.simObjects.inanimates;

import java.awt.image.BufferedImage;

import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.hayes.Hayes;
import window.gameEvents.AddMessageImageEvent;

/**
 * Simulates Hayes running out of breath.
 */
public class Breath extends InanimateObject {
    
    /**
     * Hayes loses one health every BREATH_SECS seconds.
     */
    public static final double BREATH_SECS = 7;
    
    /**
     * Construct a new Breath: an invisible, inanimate sim object
     * that takes up no space and is not rendered.
     */
    public Breath() {
        super(null, 0, 0, 0, 0, 0, 0);
        warnedUser = false;
    }

    double timeCounter = 0;    
    public void tick(double timeElapsed, ExploringGameEventQueue gameEventQueue, Hayes hayes) {
        if (!warnedUser) {
        	gameEventQueue.addEvent(new AddMessageImageEvent(poolWarningMessage));
        	warnedUser = true;
        }
    	timeCounter += timeElapsed;
        if (timeCounter > BREATH_SECS) {
            timeCounter -= BREATH_SECS;
            hayes.hurtAlways(1);
        }
    }
    
    private boolean warnedUser;
    
    private static final BufferedImage poolWarningMessage = PaintMachine.getImageFromName("textbox/pool warning.png");

}
