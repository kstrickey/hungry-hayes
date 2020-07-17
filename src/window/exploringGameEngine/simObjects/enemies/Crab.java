package window.exploringGameEngine.simObjects.enemies;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import resources.classes.ImageTools;
import resources.classes.SquareSide;
import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.background.Block;
import window.exploringGameEngine.simObjects.hayes.Hayes;

/**
 * A rather sporadic aquatic enemy that walks along the walls.
 *
 */
public class Crab extends Enemy {
	
	// Various images, since the Crab can be facing any direction
    public static final BufferedImage UNROTATED_IMAGE = PaintMachine.getImageFromName("enemy/crab jimmy.png");
    public static final BufferedImage ROTATED_RIGHT_IMG = ImageTools.rotate(UNROTATED_IMAGE, 90),
    		ROTATED_LEFT_IMG = ImageTools.rotate(UNROTATED_IMAGE, 270),
    		ROTATED_180_IMG = ImageTools.rotate(UNROTATED_IMAGE, 180);
    
    /**
     * How often, on average, the crab randomly changes direction.
     */
    public static final double DIRECTION_CHANGE_PERIOD = 4;
    
    public Crab (double x, double y, int realmWidth, int realmHeight, ArrayList<Block> blocks) {
        super(UNROTATED_IMAGE, x, y, UNROTATED_IMAGE.getWidth(), UNROTATED_IMAGE.getHeight(), 0, 0, realmWidth, realmHeight, false, blocks, 1, 1);
        
        SPEED = 30 + generator.nextDouble() * 50;
    }
    
    @Override
    public void tick(double timeElapsed, ExploringGameEventQueue gameEventQueue, Hayes hayes) {
    	super.tick(timeElapsed, gameEventQueue, hayes);
    	// Sometimes change direction
    	if (generator.nextDouble() * DIRECTION_CHANGE_PERIOD < timeElapsed) {
    		// Change direction by 180 degrees
    		setDirection(getDirection() + Math.PI);
    		if (getDirection() >= Math.PI*2) {
    			setDirection(getDirection() - 2*Math.PI);
    		}
    	}
    }
    
    @Override
    public double normalSpeed() {
    	return SPEED;
    }
    
    @Override
    public void hasHitWall(SquareSide simObjectSide) {
    	// Instead of bouncing, a Crab climbs along the wall
    	switch (simObjectSide) {
    	case RIGHT:
    		setX(getEdgeCoordinate(SquareSide.LEFT) - 1);	// bump back
    		setImage(ROTATED_LEFT_IMG);						// set correct rotated image
    		setDirection(generator.nextBoolean() ? Math.PI / 2 : Math.PI * 3/2);	// choose which direction to go
    		break;
    	case BOTTOM:
    		setY(getEdgeCoordinate(SquareSide.BOTTOM) + 1);
    		setImage(UNROTATED_IMAGE);
    		setDirection(generator.nextBoolean() ? 0 : Math.PI);
    		break;
    	case LEFT:
    		setX(getEdgeCoordinate(SquareSide.LEFT) + 1);
    		setImage(ROTATED_RIGHT_IMG);
    		setDirection(generator.nextBoolean() ? Math.PI / 2 : Math.PI * 3/2);
    		break;
    	case TOP:
    		setY(getEdgeCoordinate(SquareSide.BOTTOM) - 1);
    		setImage(ROTATED_180_IMG);
    		setDirection(generator.nextBoolean() ? 0 : Math.PI);
    		break;
    	}
    }
    
    @Override
    public void hitRealmBoundary(ExploringGameEventQueue gameEventQueue) {
    	// Is allowed to leave Realm if it happens to
    	/* Do nothing */
    }
    
    @Override
    public Enemy duplicate() {
        return new Crab(0, 0, getRealmWidth(), getRealmHeight(), getBlocks());
    }
    
    private final double SPEED;
    
}
