package window.exploringGameEngine.simObjects.enemies;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.background.Block;

/**
 * An Enemy that does no damage to Hayes but still dies upon a punch.
 *
 */
public abstract class UselessEnemy extends Enemy {
	
	/**
	 * Initializes data as passed in, setting the hayesDamageUponContact to 0 (since it is useless).
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
	 * @param punchTolerance
	 */
	public UselessEnemy(BufferedImage image, double x, double  y, double width, double height, 
			double speed, double direction, int realmWidth, int realmHeight, 
			boolean passive, ArrayList<Block> blocks, int punchTolerance) {
		super(image, x, y, width, height, speed, direction, realmWidth, realmHeight, passive, blocks, punchTolerance, 0);
	}
	
	@Override
	public void contactsHayes(ExploringGameEventQueue exploringGameEventQueue) {
		// Do nothing
	}
	
}
