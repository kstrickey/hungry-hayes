package window.exploringGameEngine.simObjects.enemies;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.background.Block;

/**
 * The most basic Enemy. Moderate size and speed, takes one punch to kill.
 * Does 1 heart damage if contacts Hayes.
 *
 */
public class Sathvik extends Enemy {
	
	public static final BufferedImage image = PaintMachine.getImageFromName("children/Sathvik Sanagala.png");
	public static final double NORMAL_SPEED = 400;
	
	public Sathvik(double x, double y, double width, double height, 
			double speed, double direction, int realmWidth, int realmHeight, 
			ArrayList<Block> blocks) {
		super(image, x, y, width, height, speed, direction, realmWidth, realmHeight, false, blocks, 1, 1);
	}
	
	@Override
	public void tick(double timeElapsed, ExploringGameEventQueue gameEventQueue) {
		super.tick(timeElapsed, gameEventQueue);
	}
	
	@Override
	public double normalSpeed() {
		return NORMAL_SPEED;
	}
	
	@Override
	public Enemy duplicate() {
		return new Sathvik(0, 0, getWidth(), getHeight(), 0, 0, getRealmWidth(), getRealmHeight(), getBlocks());
	}
	
}
