package window.exploringGameEngine.simObjects.inanimates;

import java.awt.image.BufferedImage;

import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.gameEvents.RemoveInanimateEvent;
import window.exploringGameEngine.simObjects.SimObject;
import window.exploringGameEngine.simObjects.hayes.Hayes;

public abstract class InanimateObject extends SimObject {
    
    /**
     * InanimateObjects are passive but visible.
     */
    public InanimateObject(BufferedImage image, double x, double y,
            double width, double height, int realmWidth, int realmHeight) {
        super(image, x, y, width, height, realmWidth, realmHeight);
    }
    
    /**
     * Tick with respect to Hayes. Note that super.tick() (from SimObject) does nothing,
     * because an inanimate object is passive.
     */
    public abstract void tick (double timeElapsed, ExploringGameEventQueue gameEventQueue, Hayes hayes);

    @Override
    public void removeSelfFromRealm(ExploringGameEventQueue gameEventQueue) {
        gameEventQueue.addEvent(new RemoveInanimateEvent(this));
    }

}
