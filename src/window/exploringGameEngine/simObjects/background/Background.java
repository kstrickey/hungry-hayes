package window.exploringGameEngine.simObjects.background;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.SimObject;

/**
 * A passive, invisible SimObject that is drawn as the bottom layer on screen and contains other SimObject objects.
 *
 */
public class Background extends SimObject {
	
	public Background(int realmWidth, int realmHeight, String backgroundImageFileName, ArrayList<Block> blocks) {
		super(realmWidth, realmHeight, true, blocks);
		
		this.realmWidth = realmWidth;
		this.realmHeight = realmHeight;
		this.backgroundImage = PaintMachine.getImageFromName(backgroundImageFileName);
		this.blocks = blocks;
		
		blocks = new ArrayList<Block>();	// GET FROM FILE
	}
	
	@Override
	public void tick(double timeElapsed, ExploringGameEventQueue gameEventQueue) {
	}
	
	@Override
	public void paint(PaintMachine paintMachine) {
		paintMachine.paintImage(backgroundImage, 0, 0, realmWidth, realmHeight);
		
		for (Block block : blocks) {
			block.paint(paintMachine);
		}
	}
	
	@Override
	public void removeSelfFromRealm(ExploringGameEventQueue gameEventQueue) {
		// Will never be called
	}
	
	private final int realmWidth;			// realm dimensions used to paint background image
	private final int realmHeight;
	
	private BufferedImage backgroundImage;
	private ArrayList<Block> blocks; 	
}
