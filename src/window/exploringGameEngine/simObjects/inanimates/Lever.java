package window.exploringGameEngine.simObjects.inanimates;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.hayes.Hayes;

public class Lever extends InanimateObject {
    
    public static final BufferedImage NOT_SWITCHED_IMAGE = 
            PaintMachine.getImageFromName("inanimate/lever not switched.png"),
            SWITCHED_IMAGE = 
            PaintMachine.getImageFromName("inanimate/lever switched.png");
    
    private boolean isSwitched;
    private ArrayList<Barrier> barriers;
            
    /**
     * Constructs a lever. Begins not switched. Assumes the barrier is on; when
     * the lever is switched, the barrier will be turned off.
     */
    public Lever (double x, double y, int realmWidth, int realmHeight, Barrier barrier) {
        super(NOT_SWITCHED_IMAGE, x, y, NOT_SWITCHED_IMAGE.getWidth(), 
                NOT_SWITCHED_IMAGE.getHeight(), realmWidth, realmHeight);
        barriers = new ArrayList<Barrier>();
        barriers.add(barrier);
        isSwitched = false;
    }
    
    /**
     * Constructs a Lever that controls multiple Barriers.
     * @param x
     * @param y
     * @param realmWidth
     * @param realmHeight
     * @param barriers
     */
    public Lever(double x, double y, int realmWidth, int realmHeight, ArrayList<Barrier> barriers) {
    	super(NOT_SWITCHED_IMAGE, x, y, NOT_SWITCHED_IMAGE.getWidth(), 
                NOT_SWITCHED_IMAGE.getHeight(), realmWidth, realmHeight);
        this.barriers = barriers;
        isSwitched = false;
    }

    @Override
    public void tick(double timeElapsed,
            ExploringGameEventQueue gameEventQueue, Hayes hayes) {
        if (!isSwitched && touching(hayes)) {
            switchLever();
        }
    }   
    
    public void switchLever () {
        isSwitched = true;
        setImage(SWITCHED_IMAGE);
        for (Barrier barrier : barriers) {
        	barrier.setOn(false);
        }
    }
}
