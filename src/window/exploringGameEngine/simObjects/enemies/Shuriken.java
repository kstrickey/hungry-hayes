package window.exploringGameEngine.simObjects.enemies;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import resources.classes.SquareSide;
import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.background.Block;
import window.exploringGameEngine.simObjects.hayes.Hayes;

/**
 * Thrown weapon of ninjas
 */
public class Shuriken extends Enemy {
    public static final BufferedImage IMAGE = PaintMachine.getImageFromName("enemy/shuriken.png");
    public static final int HEALTH = 1, DAMAGE = 1;
    public static final double SPEED = 600;
    private boolean toBeRemoved = false;
    
    //Number of seconds the shuriken pauses before firing
    public static final double PAUSE_SECS = .2;
    
    public Shuriken (double x, double y, double direction, int realmWidth, int realmHeight, ArrayList<Block> blocks) {
        super(IMAGE, x, y, IMAGE.getWidth(), IMAGE.getHeight(), 0, direction, realmWidth, realmHeight, false, blocks, HEALTH, DAMAGE);
    }
    
    private double secsCount = 0;
    @Override
    public void tick(double timeElapsed, ExploringGameEventQueue gameEventQueue, Hayes hayes) {
        if (toBeRemoved) {
            removeSelfFromRealm(gameEventQueue);
        } else {
            secsCount += timeElapsed;
            super.tick(timeElapsed, gameEventQueue, hayes);
        }
    }

    public double normalSpeed() {
        if (secsCount < PAUSE_SECS)
            return 0;
        return SPEED;
    }
    @Override
    public Enemy duplicate() {
        return new Shuriken(0, 0, 0, getRealmWidth(), getRealmHeight(), getBlocks());
    }
    
    //Can't be punched
    @Override
    public void punchedByHayes(ExploringGameEventQueue exploringGameEventQueue) {}
    
    /*
     * Shuriken are removed upon hitting walls or realm edges
     */
    @Override
    public void hitRealmBoundary(ExploringGameEventQueue gameEventQueue) {
        removeSelfFromRealm(gameEventQueue);
    }
    @Override
    public void hasHitWall(SquareSide side) {
        toBeRemoved = true;
    }
}
