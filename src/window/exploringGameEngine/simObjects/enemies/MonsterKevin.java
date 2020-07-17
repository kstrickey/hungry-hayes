package window.exploringGameEngine.simObjects.enemies;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.background.Block;
import window.exploringGameEngine.simObjects.hayes.Hayes;

public class MonsterKevin extends Enemy {
	
	public static final BufferedImage IMAGE = PaintMachine.getImageFromName("enemy/kevin monster.png");
	
	public static final double NORMAL_SPEED = 224;
	
	public MonsterKevin(double x, double y, int realmWidth, int realmHeight, ArrayList<Block> blocks) {
		super(IMAGE, x, y, IMAGE.getWidth(), IMAGE.getHeight(), 0, 0, realmWidth, realmHeight, false, blocks, 8, 1);
	}
	
	@Override
	public void tick(double timeElapsed, ExploringGameEventQueue gameEventQueue, Hayes hayes) {
		super.tick(timeElapsed, gameEventQueue, hayes);
		setDirection(generator.nextDouble() * 2*Math.PI);
	}
	
	@Override
	public double normalSpeed() {
		return NORMAL_SPEED;
	}
	
	@Override
	public Enemy duplicate() {
		return new MonsterKevin(0, 0, getRealmWidth(), getRealmHeight(), getBlocks());
	}
	
}
