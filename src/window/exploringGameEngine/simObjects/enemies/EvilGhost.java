package window.exploringGameEngine.simObjects.enemies;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.background.Block;
import window.exploringGameEngine.simObjects.hayes.Hayes;
import window.exploringGameEngine.simObjects.inanimates.GhostManager;

public class EvilGhost extends Enemy {
    
    public static final BufferedImage IMAGE = PaintMachine.getImageFromName("enemy/ghost eric.png");
    public static final int HEALTH = 1, DAMAGE = 1;
    public static final double SPEED = 60; //Slow moving
    
    private GhostManager manager;
    
    /**
     * If Hayes is farther than FAR, the ghost is visible.
     * If Hayes is nearer than NEAR, the ghost is invisible.
     * If Hayes is inbetween the two distances, visible for half the time of FLICKER_SECS.
     */
    public static final double FLICKER_SECS = .7, HAYES_NEAR = 275, HAYES_FAR = 400;
    private double secsCount = 0;
    private boolean isVisible;
    
    public EvilGhost (double x, double y, int realmWidth, int realmHeight, ArrayList<Block> blocks, GhostManager manager) {
        super(IMAGE, x, y, IMAGE.getWidth(), IMAGE.getHeight(), 0, 0, realmWidth, realmHeight, false, blocks, HEALTH, DAMAGE);
        this.manager = manager;
        //Get moving at a 45 degree angle
        setDirection(Math.PI/4);
    }
    
    @Override
    public void tick(double timeElapsed, ExploringGameEventQueue gameEventQueue, Hayes hayes) {
        secsCount += timeElapsed;
        if (secsCount >= FLICKER_SECS) {
            secsCount -= FLICKER_SECS;
        }
        
        double distance = hayes.center().distance(this.center());
        if (distance <= HAYES_NEAR) {
            isVisible = false;
        } else if (distance > HAYES_FAR) {
            isVisible = true;
        } else {
            /*
             * Visible for half the time during TOTAL_FLICKER_SECS.
             */
            isVisible = secsCount < FLICKER_SECS / 2;
        }
        super.tick(timeElapsed, gameEventQueue, hayes);
    }
    
    @Override
    public double normalSpeed() {
        return SPEED;
    }

    @Override
    public Enemy duplicate() {
        return new EvilGhost(0, 0, getRealmWidth(), getRealmHeight(), getBlocks(), manager);
    }
    
    /**
     * Notify ghost manager. Do not drop any electrons.
     */
    @Override
    public void uponDeath (ExploringGameEventQueue queue) {
        manager.ghostDied(queue);
    }
    
    /**
     * Paint only if visible, or if stunned (and thus, dying)
     */
    @Override
    public void paint (PaintMachine paintMachine) {
        if (isVisible || isStunned())
            super.paint(paintMachine);        
    }   
}
