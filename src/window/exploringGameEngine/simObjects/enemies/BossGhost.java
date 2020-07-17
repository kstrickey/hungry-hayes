package window.exploringGameEngine.simObjects.enemies;

import java.awt.image.BufferedImage;

import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.gameEvents.AddEnemyEvent;
import window.exploringGameEngine.gameEvents.AddInanimateEvent;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.hayes.Hayes;
import window.exploringGameEngine.simObjects.inanimates.Baby;

public class BossGhost extends Enemy {

    public static final BufferedImage IMAGE = PaintMachine.getImageFromName("enemy/boss ghost joy.png");
    public static final int HEALTH = 4, DAMAGE = 1;
    public static final double SPEED = 100;
    private Baby baby;
    
    /**
     * Constructs a new BossGhost. Does not take blocks because BossGhost passes through walls.
     * The specified baby will be added to the realm when this boss ghost dies. 
     */
    public BossGhost (double x, double y, int realmWidth, int realmHeight, Baby baby) {
        super(IMAGE, x, y, IMAGE.getWidth(), IMAGE.getHeight(), 0, 0, realmWidth, realmHeight, false, null, HEALTH, DAMAGE);
        this.baby = baby;
    }
    
    /**
     * Boss ghost rests for REST_SECS and then spawns NUM_BATS at 
     * BAT_INTERVAL_SECS intervals.
     */
    public static final int NUM_BATS = 3;
    public static final double REST_SECS = 3, BAT_INTERVAL_SECS = .5, 
            TOTAL_SECS = REST_SECS + NUM_BATS * BAT_INTERVAL_SECS;
    int batsSpawned = 0;
    double secsCount = 0;    
    public void tick(double timeElapsed, ExploringGameEventQueue gameEventQueue, Hayes hayes) {
        //Always chase Hayes
        setDirection(directionToward(hayes));     
        
        //Throw bats only if not stunned
        if (! isStunned()) {
            secsCount += timeElapsed;
        }
        if (secsCount >= TOTAL_SECS) { //Reset secs and bat spawn count
            secsCount -= TOTAL_SECS;
            batsSpawned = 0;
        }
        if (secsCount > REST_SECS) {
            if (Math.ceil((secsCount - REST_SECS) / BAT_INTERVAL_SECS) > batsSpawned) {
                gameEventQueue.addEvent(new AddEnemyEvent(new Bat(
                        center().x - Bat.IMAGE.getWidth()/2, center().y - Bat.IMAGE.getHeight()/2, 
                        getDirection(), getRealmWidth(), getRealmHeight()
                        )));
                batsSpawned++;
            }
        }
        super.tick(timeElapsed, gameEventQueue, hayes);
    }
    
    //Stay fully inside realm
    @Override
    public void hitRealmBoundary(ExploringGameEventQueue gameEventQueue) {
        stayFullyInsideRealm();
    }

    @Override
    public double normalSpeed() {
        return SPEED;
    }

    @Override
    public Enemy duplicate() {
        return new BossGhost(0, 0, getRealmWidth(), getRealmHeight(), baby);
    }
    
    /**
     * Drop baby
     */
    @Override
    public void uponDeath (ExploringGameEventQueue queue) {
        queue.addEvent(new AddInanimateEvent(baby));
    }

}
