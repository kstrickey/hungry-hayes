package window.exploringGameEngine.simObjects.enemies;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.simObjects.background.Block;

public class UselessStudent extends UselessEnemy {
	
	/**
	 * When constructing new UselessStudent objects, keep speed between 0 and this value.
	 */
	public static final double MAX_SPEED = 250;
	
	public static final double STD_WIDTH = 50;
	public static final double STD_HEIGHT = 75;
	
	/**
	 * Constructs a new UselessStudent using the given information.
	 * Uses a random image from the list image paths.
	 * Sets the normalSpeed to the given speed paramater.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param speed	: functions as the constant speed for the UselessStudent's life
	 * @param direction
	 * @param realmWidth
	 * @param realmHeight
	 * @param blocks
	 */
	public UselessStudent(double x, double y, int realmWidth, int realmHeight, ArrayList<Block> blocks) {
		super(selectRandomImage(), x, y, STD_WIDTH, STD_HEIGHT, 
				0, generator.nextDouble() * 2 * Math.PI, 
				realmWidth, realmHeight, false, blocks, 1);
		normalSpeed = generator.nextDouble() * MAX_SPEED;
	}

	@Override
	public double normalSpeed() {
		return normalSpeed;
	}

	@Override
	public Enemy duplicate() {
		return new UselessStudent(0, 0, getRealmWidth(), getRealmHeight(), getBlocks());
	}
	
	private final double normalSpeed;
	
	
//	// Static things to create new UselessStudents ===================================
//	
//	/**
//	 * Constructs and returns a new UselessStudent object, with the given parameters as data and the rest randomized.
//	 * @param x
//	 * @param y
//	 * @param width
//	 * @param height
//	 * @param realmWidth
//	 * @param realmHeight
//	 * @param blocks
//	 * @return a new UselessStudent instance
//	 */
//	public static UselessStudent newUselessStudent(double x, double y, double width, double height, int realmWidth, int realmHeight, ArrayList<Block> blocks) {
//		BufferedImage image = PaintMachine.getImageFromName("children/" + STUDENT_NAMES[generator.nextInt(STUDENT_NAMES.length)]);
//		double speed = generator.nextDouble() * MAX_SPEED;
//		double direction = generator.nextDouble() * Math.PI * 2;
//		return new UselessStudent(image, x, y, width, height, speed, direction, realmWidth, realmHeight, blocks);
//	}
	
	
	/**
	 * Selects, creates and returns a BufferedImage randomly from the static list of image paths in STUDENT_NAMES.
	 * @return BufferedImage
	 */
	private static BufferedImage selectRandomImage() {
	    return STUDENT_IMAGES[generator.nextInt(STUDENT_IMAGES.length)];
		//return PaintMachine.getImageFromName("children/" + STUDENT_NAMES[generator.nextInt(STUDENT_NAMES.length)]);
	}
	
	private static final String[] STUDENT_NAMES = {
		"Brian Crotty.png",
		"Bryson Wilks.png",
		"Eric Zhang.png",
		"James Fox.png",
		"Joy Chiu.png",
		"Kevin Trickey.png",
		"Manik Akhand.png",
		"Matthew Wu.png",
		"Moussa Harajli.png",
		"Nathan Zieman.png",
		"Sathvik Sanagala.png",
	};
	
	private static final BufferedImage[] STUDENT_IMAGES = new BufferedImage[STUDENT_NAMES.length];
	static {
	    for (int i = 0; i < STUDENT_NAMES.length; i++) {
	        STUDENT_IMAGES[i] = PaintMachine.getImageFromName("children/" + STUDENT_NAMES[i]);
	    }
	}

}
