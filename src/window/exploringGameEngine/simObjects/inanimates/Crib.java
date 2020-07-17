package window.exploringGameEngine.simObjects.inanimates;

import java.awt.image.BufferedImage;

import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.hayes.Hayes;

public class Crib extends InanimateObject {
    
    public static final BufferedImage CRIB_EMPTY_IMAGE = 
            PaintMachine.getImageFromName("inanimate/crib empty.png"),
            CRIB_BABY_IMAGE = 
            PaintMachine.getImageFromName("inanimate/crib baby.png");
    
    private boolean occupied;
    
    /**
     * Constructs an empty crib.
     */
    public Crib (double x, double y, int realmWidth, int realmHeight) {
        super(CRIB_EMPTY_IMAGE, x, y, CRIB_EMPTY_IMAGE.getWidth(), 
                CRIB_EMPTY_IMAGE.getHeight(), realmWidth, realmHeight);
        occupied = false;
    }
    
    // Does nothing
    @Override
    public void tick(double timeElapsed, ExploringGameEventQueue gameEventQueue, Hayes hayes) {        
    }
    
    public boolean isOccupied() {
    	return occupied;
    }
    
    /**
     * Set whether this crib contains the baby.
     */
    public void setBaby(boolean hasBaby) {
        this.occupied = hasBaby;
        if (hasBaby) {
            this.setImage(CRIB_BABY_IMAGE);
        } else {
            this.setImage(CRIB_EMPTY_IMAGE);
        }
    }

}
