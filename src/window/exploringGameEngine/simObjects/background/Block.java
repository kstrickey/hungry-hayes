package window.exploringGameEngine.simObjects.background;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import resources.classes.SquareSide;
import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.Paintable;
import window.exploringGameEngine.simObjects.SimObject;
import window.gameEvents.GameEventQueue;

/**
 * Represents a section of the Realm that can be blocked out from SimObject entry.
 * NOTE: Block objects with either width or height less than 4 may cause unpredictable collision detection.
 *
 */
public class Block implements Paintable {
	
	public Block(int x, int y, int width, int height) {
		area = new Rectangle(x, y, width, height);
		image = PaintMachine.getImageFromName("transparentbox.png");//("blackbox.png");//use blackbox for testing
	}
	
	@Override
	public void paint(PaintMachine paintMachine) {
		paintMachine.paintImage(image, area.x, area.y, area.width, area.height);
	}
	
	public Rectangle getArea() {
		return area;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	/**
	 * Determines which sides, if any, of a parameter SimObject intersects the Block, then moves the SimObject to adjust.
	 * Does not use the GameEventQueue parameter in base implementation (may use in overriding implementations).
	 * @param simObject
	 * @param gameEventQueue
	 */
	public void checkAndMoveSimObject(SimObject simObject, GameEventQueue gameEventQueue) {
		if (!area.intersects(simObject.getEdgeCoordinate(SquareSide.LEFT), simObject.getEdgeCoordinate(SquareSide.BOTTOM), simObject.getWidth(), simObject.getHeight())) {
			return;		// no moving to do here!
		}

		// Retrieve rectangle intersection
		Rectangle intersection = area.intersection(simObject.rectangleArea());
		if (intersection.isEmpty()) {
			return;				// no moving to do here!!!
		}
		
		if (intersection.width > intersection.height) {			// SO moving majority up or down
			if (intersection.y <= simObject.getEdgeCoordinate(SquareSide.BOTTOM) + 1) {		// SO moving down
				simObject.hasHitWall(SquareSide.BOTTOM);
			} else {					// SO moving up
				simObject.hasHitWall(SquareSide.TOP);
			}
		} else {												// SO moving majority left or right
			if (intersection.x <= simObject.getEdgeCoordinate(SquareSide.LEFT) + 1) {		// SO moving left
				simObject.hasHitWall(SquareSide.LEFT);
			} else {					// SO moving right
				simObject.hasHitWall(SquareSide.RIGHT);
			}
		}
	}
	
	private final Rectangle area;
	private BufferedImage image;
	
}
