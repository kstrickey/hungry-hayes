package window.exploringGameEngine.simObjects;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import resources.classes.EuclidianVector;
import resources.classes.SquareSide;
import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.Paintable;
import window.exploringGameEngine.Tickable;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.background.Block;

public abstract class SimObject implements Tickable, Paintable {
	
	/**
	 * Constructor initializes data as passed in (rotation initialized to 0).
	 * @param image
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param speed
	 * @param direction
	 * @param realmWidth
	 * @param realmHeight
	 * @param passive
	 * @param blocks
	 */
	public SimObject(BufferedImage image, double x, double y, double width, double height, double speed, double direction, int realmWidth, int realmHeight, boolean passive, ArrayList<Block> blocks) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		velocity = new EuclidianVector(speed, direction);
		this.realmWidth = realmWidth;
		this.realmHeight = realmHeight;
		this.passive = passive;
		this.blocks = blocks;
	}
	
	/**
	 * Constructor initializes data as passed in, and sets the field passive to false.
	 * @param image
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param speed
	 * @param direction
	 * @param realmWidth
	 * @param realmHeight
	 */
	public SimObject(BufferedImage image, double x, double y, double width, double height, double speed, double direction, int realmWidth, int realmHeight) {
		this(image, x, y, width, height, speed, direction, realmWidth, realmHeight, false, null);
	}
	
	/**
	 * Constructor for passive, visible objects. Initializes data as passed in, setting speed and direction to 0 and passive to true.
	 * @param image
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param realmWidth
	 * @param realmHeight
	 */
	public SimObject(BufferedImage image, double x, double y, double width, double height, int realmWidth, int realmHeight) {
		this(image, x, y, width, height, 0, 0, realmWidth, realmHeight, true, null);
	}
	
	/**
	 * Constructor for initially invisible objects. Sets image to null and all kinematic fields to 0.
	 * @param realmWidth
	 * @param realmHeight
	 * @param passive
	 */
	public SimObject(int realmWidth, int realmHeight, boolean passive, ArrayList<Block> blocks) {
		this(null, 0, 0, 0, 0, 0, 0, realmWidth, realmHeight, passive, blocks);
	}
	
	/**
	 * Constructor for passive, invisible objects. Sets image to null and passive to true.
	 * @param realmWidth
	 * @param realmHeight
	 */
	public SimObject(int realmWidth, int realmHeight) {
		this(null, 0, 0, 0, 0, 0, 0, realmWidth, realmHeight, true, null);
	}
	
	@Override
	public void tick(double timeElapsed, ExploringGameEventQueue gameEventQueue) {
		if (!passive) {			
			if (!withinRealm()) {
				passedRealmBoundary(gameEventQueue);
			} else if (!fullyWithinRealm()) {
				hitRealmBoundary(gameEventQueue);
			}
			
			if (blocks != null) {
				// Iterate through Blocks				// TODO maybe separate blocks in particular areas?
				for (Block block : blocks) {
					block.checkAndMoveSimObject(this, gameEventQueue);
				}
			}
			
	         x += velocity.rectangularX();
	         y += velocity.rectangularY();	//System.out.println("SimObject: " + this + "ticking w/ v=" + velocity.magnitude);
		}
	}
	
	@Override
	public void paint(PaintMachine paintMachine) {
		if (image != null) {
			paintMachine.paintImage(image, x, y, width, height);
		}
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public void setX(double x) {
		if (!passive) {
			this.x = x;
		}
	}
	
	public void setY(double y) {
		if (!passive) {
			this.y = y;
		}
	}
	
	public void setWidth(double width) {
		if (!passive) {
			this.width = width;
		}
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public void setHeight(double height) {
		if (!passive) {
			this.height = height;
		}
	}
	
	/**
	 * Resets the Realm data.
	 * Implemented for the purpose of Hayes switching Realms.
	 * Aside from Hayes, however, every SimObject should be newly constructed with each Realm.
	 * @param realmWidth
	 * @param realmHeight
	 * @param blocks
	 */
	public void resetRealm(int realmWidth, int realmHeight, ArrayList<Block> blocks) {
		this.realmWidth = realmWidth;
		this.realmHeight = realmHeight;
		this.blocks = blocks;
	}
	
	/**
	 * Creates and returns a new Rectangle with the area and dimensions of this SimObject, albeit with int precision.
	 * @return a new Rectangle
	 */
	public Rectangle rectangleArea() {
		return new Rectangle((int) x, (int) y, (int) width, (int) height);
	}
	
	/**
	 * Returns the coordinate (x or y) of the specified edge of the SimObject.
	 * Ex. passing SquareSide.BOTTOM will return the field y, while passing SquareSide.RIGHT will return the sum of fields x and width.
	 * @param edge
	 * @return
	 */
	public double getEdgeCoordinate(SquareSide edge) {
		switch (edge) {
			case RIGHT:
				return x + width;
			case BOTTOM:
				return y;
			case LEFT:
				return x;
			case TOP:
				return y + height;
		}
		return 0;			// unreachable code
	}
	
	/**
	 * Returns a pointer to the velocity EuclidianVector object.
	 * Note: this is an alias and will modify the SimObject's velocity.
	 * @return
	 */
	public EuclidianVector getVelocity() {
		return velocity;
	}
	
	/**
	 * Sets the magnitude of the EuclidianVector field velocity.
	 * @param speed
	 */
	public void setSpeed(double speed) {
		velocity.magnitude = speed;
	}
	
	/**
	 * Gets the magnitude of the EuclidianVector field velocity.
	 * @return the speed as a double
	 */
	public double getSpeed() {
		return velocity.magnitude;
	}
	
	/**
	 * Sets the direction of the EuclidianVector field velocity.
	 * @param direction
	 */
	public void setDirection(double direction) {
		velocity.direction = direction;
	}
	
	/**
	 * Gets the direction of the EuclidianVector field velocity.
	 * @return the direction as a double
	 */
	public double getDirection() {
		return velocity.direction;
	}
	
	/**
	 * Returns a new Point object created at the center of this SimObject.
	 * @return
	 */
	public Point center() {
		return new Point((int) (x + width/2.0), (int) (y + height/2.0));
	}
	
	// Realm boundary collisions =====================================================
	
	/**
	 * Method called when the SimObject comes into contact with the edge of the realm.
	 * May be overridden. Default action to bounce.
	 * @param gameEventQueue
	 */
	public void hitRealmBoundary(ExploringGameEventQueue gameEventQueue) {
		SquareSide simObjectSide = null;
		if (intersectsSideOfRealm(SquareSide.RIGHT)) {
			simObjectSide = SquareSide.RIGHT;
		} else if (intersectsSideOfRealm(SquareSide.BOTTOM)) {
			simObjectSide = SquareSide.BOTTOM;
		} else if (intersectsSideOfRealm(SquareSide.LEFT)) {
			simObjectSide = SquareSide.LEFT;
		} else if (intersectsSideOfRealm(SquareSide.TOP)) {
			simObjectSide = SquareSide.TOP;
		}
		bounce(simObjectSide);
		stayFullyInsideRealm();
	}
	
	/**
	 * Method called when the SimObject has fully passed out of the edge of the realm. Not all non-passive SimObjects leave the realm.
	 * @param gameEventQueue
	 */
	public void passedRealmBoundary(ExploringGameEventQueue gameEventQueue) {
		removeSelfFromRealm(gameEventQueue);
	}
	
	/**
	 * Determines whether the object, based on passed parameters, is at all in its realm.
	 * If the object is half in and half out, the method will still return true.
	 * @return true if any part of the object is within realm boundaries; false if no part of the rectangle is within realm bounds
	 */
	public boolean withinRealm() {
		return x + width > 0 &&
				x < realmWidth &&
				y + height > 0 &&
				y < realmHeight;
	}
	
	/**
	 * Determines whether the object, based on passed parameters, is fully in its realm.
	 * If the object is half in and half out, the method will return false.
	 * @return true if the entire rectangle falls within realm boundaries; false if any part is out of bounds
	 */
	public boolean fullyWithinRealm() {
		return x >= 0 &&
				x + width <= realmWidth &&
				y >= 0 &&
				y + height <= realmHeight;
	}
	
	/**
	 * Indicates whether or not the SimObject is in intersection with a certain side of the realm.
	 * If this method returns true, then fullyWithinRealm must return false and withinRealm must return true.
	 * @param sideOfRealm : SquareSide
	 * @return true if the SimObject intersects the indicated side of the Realm
	 */
	public boolean intersectsSideOfRealm(SquareSide sideOfRealm) {
		Rectangle2D.Double area = new Rectangle2D.Double(x, y, width, height);
		switch (sideOfRealm) {
			case RIGHT:
				return area.intersectsLine(realmWidth, 0, realmWidth, realmHeight);
			case BOTTOM:
				return area.intersectsLine(0, 0, realmWidth, 0);
			case LEFT:
				return area.intersectsLine(0, 0, 0, realmHeight);
			case TOP:
				return area.intersectsLine(0, realmHeight, realmWidth, realmHeight);
		}
		return false;			// unreachable code
	}
	
	/**
	 * A convenience method that may be called by child classes that places the SimObject in the closest location possible that is fully inside the Realm.
	 * Often implemented while overriding the hitRealmBoundary method.
	 */
	public void stayFullyInsideRealm() {
		if (!fullyWithinRealm()) {
			if (intersectsSideOfRealm(SquareSide.RIGHT)) {
				x = realmWidth - width;
			} else if (intersectsSideOfRealm(SquareSide.LEFT)) {
				x = 0;
			}
			if (intersectsSideOfRealm(SquareSide.BOTTOM)) {
				y = 0;
			} else if (intersectsSideOfRealm(SquareSide.TOP)) {
				y = realmHeight - height;
			}
		}
	}
	
	// Touching other sims ===============================================
	/**
     * Determines whether this SimObject intersects a given SimObject.
     * @param other : SimObject
     * @return true if the two do intersect; false otherwise
     */
    public boolean touching(SimObject other) {
        return other.rectangleArea().intersects(rectangleArea());
    }
	
	// Collisions with walls ======================================================
	
	/**
	 * Called by Block.checkAndMoveSimObject when a specific side of the SimObject hits a wall.
	 * May be overridden; default action is to bounce (call bounce method).
	 * @param simObjectSide : the SquareSide of the SimObject (not the wall) that has collided
	 */
	public void hasHitWall(SquareSide simObjectSide) {
		bounce(simObjectSide);
	}
	
	/**
	 * A convenience method that may be called by child classes that bounces the SimObject off any intersecting realm boundaries and calls stayFullyInsideRealm.
	 */
	public void bounce(SquareSide simObjectSideWhichHasCollided) {
		switch (simObjectSideWhichHasCollided) {
		case RIGHT:
		case LEFT:
			velocity.direction = Math.PI - velocity.direction;
			break;
		case BOTTOM:
		case TOP:
			velocity.direction = 2 * Math.PI - velocity.direction;
			break;
		}
	}
	
	/**
	 * A convenience method that may be called by child classes.
	 * Prevents the SimObject from moving in the SquareSide direction given.
	 * @param simObjectSide
	 */
	public void stopInDirection(SquareSide simObjectSide) {
		switch (simObjectSide) {
		case RIGHT:
			if (velocity.rectangularX() > 0) {
				velocity.setUsingRectangularDimensions(0, velocity.rectangularY());
			}
			break;
		case BOTTOM:
			if (velocity.rectangularY() < 0) {
				velocity.setUsingRectangularDimensions(velocity.rectangularX(), 0);
			}
			break;
		case LEFT:
			if (velocity.rectangularX() < 0) {
				velocity.setUsingRectangularDimensions(0, velocity.rectangularY());
			}
			break;
		case TOP:
			if (velocity.rectangularY() > 0) {
				velocity.setUsingRectangularDimensions(velocity.rectangularX(), 0);
			}
			break;
		}
	}
	
	// =======================================================
	
	/**
	 * Method called when the SimObject receives a call to remove itself from the game (e.g. when a Ghost leaves the realm and is no longer in play).
	 * @param gameEventQueue
	 */
	public abstract void removeSelfFromRealm(ExploringGameEventQueue gameEventQueue);
	
	/**
	 * Sets the opacity (alpha value) of the SimObject's image to the passed value.
	 * @param alpha : double value between 0.0 (complete transparency) and 1.0 (complete opacity)
	 */
	public void prescribeImageOpacity(double alpha) {
//COMMENTED TO TEST IF OPACITY STUFF CAUSES SLOWNESS//TODO
//		BufferedImage target = new BufferedImage(image.getWidth(null), image.getHeight(null), Transparency.TRANSLUCENT);
//		Graphics2D tg = target.createGraphics();
//		tg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) alpha));
//		tg.drawImage(image, 0, 0, null);
//		tg.dispose();
//		image = target;
	}
	
	private int realmWidth;
	private int realmHeight;
	private ArrayList<Block> blocks;		// a SimObject with blocks == null is not affected by Blocks
	
	private final boolean passive;			// if a SimObject is passive, its tick cycle does not use its speed or direction (i.e. it does not move) (e.g. Background, AllChildren)
	
	private BufferedImage image;			// a SimObject with image == null is invisible (e.g. AllChildren)
	
	private double x;
	private double y;
	private double width;
	private double height;
	private EuclidianVector velocity;
	
//	private double radiansRotated;
	
}
