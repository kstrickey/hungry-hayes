package window.exploringGameEngine.simObjects.inanimates;

import java.awt.image.BufferedImage;

import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.hayes.Hayes;

public class Electron extends InanimateObject {
    public static final BufferedImage IMAGE = PaintMachine.getImageFromName("inanimate/electron.png");
    public static final int HEALTH = 1;
    
    public Electron (double x, double y, int realmWidth, int realmHeight) {
        super(IMAGE, x, y, IMAGE.getWidth(), IMAGE.getHeight(), realmWidth, realmHeight);
    }
    
    /**
     * Heals Hayes for one health.
     */
    @Override
    public void tick(double timeElapsed,
            ExploringGameEventQueue gameEventQueue, Hayes hayes) {
        if (touching(hayes)) {
            hayes.heal(1);
            removeSelfFromRealm(gameEventQueue);
        }
    }

}
