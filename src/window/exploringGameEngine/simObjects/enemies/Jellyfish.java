package window.exploringGameEngine.simObjects.enemies;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.background.Block;

/**
 * A floating, aquatic Enemy that really does nothing except bounce.
 * Exceeded in stupidity only by UselessStudent.
 *
 */
public class Jellyfish extends Enemy {
	
	public static final BufferedImage IMAGE = PaintMachine.getImageFromName("enemy/jellyfish sathvik.png");
	
	public static final double MAX_SPEED = 65;
	
	public Jellyfish(double x, double y, int realmWidth, int realmHeight, ArrayList<Block> blocks) {
		super(IMAGE, x, y, IMAGE.getWidth(), IMAGE.getHeight(), 0, generator.nextDouble() * 2*Math.PI, 
				realmWidth, realmHeight, false, blocks, 1, 1);
		
		normalSpeed = generator.nextDouble() * MAX_SPEED;
	}
	
	@Override
	public double normalSpeed() {
		return normalSpeed;
	}
	
	@Override
	public void uponDeath(ExploringGameEventQueue gameEventQueue) {
		// Drop 2 electrons
		dropElectrons(gameEventQueue, 2);
	}
	
	@Override
	public Enemy duplicate() {
		return new Jellyfish(0, 0, getRealmWidth(), getRealmHeight(), getBlocks());
	}
	
	private final double normalSpeed;
	
}
