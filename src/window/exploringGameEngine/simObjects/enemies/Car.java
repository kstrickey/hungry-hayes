package window.exploringGameEngine.simObjects.enemies;

import java.awt.image.BufferedImage;

import resources.classes.SquareSide;
import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
 
public class Car extends Enemy {
	
    public static final BufferedImage IMAGE = PaintMachine.getImageFromName("enemy/leftCar.png");
    public static final int WIDTH = 302, HEIGHT = 152;
    public static final int HEALTH = 500, DAMAGE = 2;
    public static final double SPEED = 270;
     
    public Car(double x, double y, int realmWidth, int realmHeight) {
        super(IMAGE, x, y, IMAGE.getWidth(), IMAGE.getHeight(), SPEED * .005, Math.PI,
            realmWidth, realmHeight, false, null, HEALTH, DAMAGE);
    }
    
    @Override
    public double normalSpeed() {
    	return SPEED;
    }
     
    @Override
    public Enemy duplicate() {
       return new Car(0, 0, getRealmWidth(), getRealmHeight());
    }
    
    @Override
    public void hitRealmBoundary(ExploringGameEventQueue gameEventQueue) {
    	/* Do nothing - just keep going */
    }
    
    @Override
    public void punchedByHayes(ExploringGameEventQueue exploringGameEventQueue) {
    	/* Do nothing - not affected by punches */
    }
    
    @Override
    public boolean withinRealm() {
    	/*
    	 * If not within the Realm according to the superclass's definition, 
    	 * it might be just outside the right side, waiting to enter.
    	 * Give it a 10-pixel buffer.
    	 */
    	if (!super.withinRealm()) {
    		if (getEdgeCoordinate(SquareSide.LEFT) >= getRealmWidth() && getEdgeCoordinate(SquareSide.LEFT) < getRealmWidth() + 10 &&
    				getEdgeCoordinate(SquareSide.TOP) >= 0 && getEdgeCoordinate(SquareSide.BOTTOM) <= getRealmHeight()) {
    			return true;
    		}
    	}
    	return super.withinRealm();
    }
    
}