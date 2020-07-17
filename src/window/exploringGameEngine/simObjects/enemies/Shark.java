package window.exploringGameEngine.simObjects.enemies;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.background.Block;
import window.exploringGameEngine.simObjects.hayes.Hayes;

/**
 * An Enemy for the sea. Punch tolerance of 3, deals 1 unit of damage.
 * Always floats toward Hayes.
 *
 */
public class Shark extends Enemy {
	
    public static final BufferedImage IMAGE = PaintMachine.getImageFromName("enemy/shark nathan.png");
    
    public static final double INTERESTED_SPEED = 120;
    public static final double DISINTERESTED_SPEED = 60;
    
    /**
     * The amount of time (in seconds), on average, that the Shark will remain disinterested before becoming interested in Hayes.
     */
    public static final double BECOME_INTERESTED_AFTER = 5;
    
    public Shark (double x, double y, int realmWidth, int realmHeight, ArrayList<Block> blocks) {
        super(IMAGE, x, y, IMAGE.getWidth(), IMAGE.getHeight(), 0, 0, realmWidth, realmHeight, false, blocks, 3, 1);
        
        interested = false;
    }
    
    @Override
    public void tick(double timeElapsed, ExploringGameEventQueue gameEventQueue, Hayes hayes) {
    	super.tick(timeElapsed, gameEventQueue, hayes);
    	
    	if (interested) {
	    	// Always turn toward Hayes
	    	setDirection(directionToward(hayes));
	//    	double speed = getSpeed();			// hold speed while we manipulate direction
	//    	double dx = hayes.center().x - center().x;
	//    	double dy = hayes.center().y - center().y;
	//    	getVelocity().setUsingRectangularDimensions(dx, dy);	// now direction is correct
	//    	setSpeed(speed);				// and now speed is correct too
    	} else {
    		// Perhaps become interested? (random)
    		interested = generator.nextDouble() * BECOME_INTERESTED_AFTER < timeElapsed;
    	}
    }
    
    @Override
    public void contactsHayes(ExploringGameEventQueue exploringGameEventQueue) {
    	super.contactsHayes(exploringGameEventQueue);
    	
    	interested = false;
    }
    
    @Override
    public void punchedByHayes(ExploringGameEventQueue exploringGameEventQueue) {
    	super.punchedByHayes(exploringGameEventQueue);
    	
    	interested = false;
    }
    
    @Override
    public double normalSpeed() {
    	return interested ? INTERESTED_SPEED : DISINTERESTED_SPEED;
    }
    
    @Override
    public void uponDeath(ExploringGameEventQueue gameEventQueue) {
    	dropElectrons(gameEventQueue, 2);
    }
    
    public Enemy duplicate() {
        return new Shark(0, 0, getRealmWidth(), getRealmHeight(), getBlocks());
    }
    
    private boolean interested;		// whether or not the Shark is going toward Hayes
    
}
