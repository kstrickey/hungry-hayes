package window.exploringGameEngine.simObjects.inanimates;

import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.hayes.Hayes;

public class Barrier extends InanimateObject {
	
//	public static final double DAMAGE_PER_SECOND = 3;
    
    private BarrierType barrierType;
    private boolean isOn;
    
    /**
     * Construct a Barrier based on its on and off images, its position, and 
     * whether it begins on or off. Assumes the "on" and "off" images have
     * the same dimensions.
     */
    public Barrier(BarrierType barrierType, double x, double y, int realmWidth, int realmHeight, boolean isOn) {
        super(null, x, y, barrierType.getWidth(), barrierType.getHeight(), realmWidth, realmHeight);
        this.barrierType = barrierType;
        setOn(isOn);
    }
    
    /**
     * Set whether the barrier is on.
     */
    public void setOn(boolean isOn) {
        this.isOn = isOn;
        setImage(isOn ? barrierType.onImage : barrierType.offImage);
    }
    
    /**
     * If the barrier is on and it is contacting hayes, deal lethal damage
     */
    @Override
    public void tick(double timeElapsed, ExploringGameEventQueue gameEventQueue, Hayes hayes) {
        if (isOn && touching(hayes)) {
            hayes.hurtAlways(3);
            hayes.stunAndRecoilFrom(this);
        }
    }
}
