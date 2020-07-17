package window.exploringGameEngine.simObjects.enemies;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.gameEvents.AddEnemyEvent;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.background.Block;
import window.exploringGameEngine.simObjects.hayes.Hayes;

public abstract class BossNinja extends Enemy {
    
    public static final BufferedImage IMAGE = PaintMachine.getImageFromName("enemy/boss ninja matt.png");
    public static final double SPEED = 200,
            //Throws a shuriken every SHURIKEN_SECS
            SHURIKEN_SECS = 4;

    public BossNinja (double x, double y, int realmWidth, int realmHeight, ArrayList<Block> blocks, int health, int damage) {
        super(IMAGE, x, y, IMAGE.getWidth(), IMAGE.getHeight(), 0, random45(), realmWidth, realmHeight, false, blocks, health, damage);
    }
    
    private double secsCount = 0;
    @Override
    public void tick(double timeElapsed, ExploringGameEventQueue gameEventQueue, Hayes hayes) {
        if (! isStunned()) {
            secsCount += timeElapsed;
        }
        if (secsCount >= SHURIKEN_SECS) { //Reset secs
            secsCount -= SHURIKEN_SECS;
            //Fire shuriken in direction of Hayes
            gameEventQueue.addEvent(new AddEnemyEvent(new Shuriken(
                    center().x - Shuriken.IMAGE.getWidth()/2, center().y - Shuriken.IMAGE.getHeight()/2, 
                    directionToward(hayes), getRealmWidth(), getRealmHeight(), getBlocks()
                    )));
        }
        super.tick(timeElapsed, gameEventQueue, hayes);
    } 
    
    @Override
    public double normalSpeed() {
        return SPEED;
    }
}
