package window.exploringGameEngine.simObjects.enemies;

import java.awt.image.BufferedImage;

import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.hayes.Hayes;

/**
 * Pretty much the thrown weapons of the boss ghost.
 * Also passes through walls.
 */
public class Bat extends Enemy {
    
    public static final BufferedImage IMAGE = PaintMachine.getImageFromName("enemy/bat.png");
    public static final int HEALTH = 1, DAMAGE = 1;
    public static final double SPEED = 800;
    /**
     * The bat pauses for this amount of time before shooting in Hayes' direction.
     */
    public static final double PAUSE_SECS = 0.5;
    
    public Bat (double x, double y, double direction, int realmWidth, int realmHeight) {
        super(IMAGE, x, y, IMAGE.getWidth(), IMAGE.getHeight(), 0, direction, realmWidth, realmHeight, false, null, HEALTH, DAMAGE);
    }
    
    private double secsCount = 0;
    @Override
    public void tick(double timeElapsed, ExploringGameEventQueue gameEventQueue, Hayes hayes) {
        secsCount += timeElapsed;
        super.tick(timeElapsed, gameEventQueue, hayes);
    }

    public double normalSpeed() {
        if (secsCount < PAUSE_SECS)
            return 0;
        return SPEED;
    }

    @Override
    public Enemy duplicate() {
        return null;
    }
    
    /**
     * Bats are removed upon hitting realm edges
     */
    public void hitRealmBoundary(ExploringGameEventQueue gameEventQueue) {
        removeSelfFromRealm(gameEventQueue);
    }
}
