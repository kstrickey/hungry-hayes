package window.exploringGameEngine.simObjects.enemies;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.gameEvents.AddEnemyEvent;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.background.Block;
import window.exploringGameEngine.simObjects.hayes.Hayes;

public class Ninja extends Enemy {
    
    public static final BufferedImage IMAGE = PaintMachine.getImageFromName("enemy/ninja bryson.png");
    public static final int HEALTH = 1, DAMAGE = 1;
    public static final double SPEED = 150,
            //Throws a shuriken every SHURIKEN_SECS
            SHURIKEN_SECS = 4.5,
            //Only throws if Hayes is within this distance
            HAYES_DISTANCE = 400;
    
    public Ninja (double x, double y, int realmWidth, int realmHeight, ArrayList<Block> blocks) {
        super(IMAGE, x, y, IMAGE.getWidth(), IMAGE.getHeight(), 0, 0, realmWidth, realmHeight, false, blocks, HEALTH, DAMAGE);
        //Random of pi/4, 3pi/4, 5pi/4, 7pi/4
        setDirection((int) (4*Math.random()) * Math.PI/2 + Math.PI/4);
    }
    
    private double secsCount = 0;
    public void tick(double timeElapsed, ExploringGameEventQueue gameEventQueue, Hayes hayes) {
        if (! isStunned()) {
            secsCount += timeElapsed;
        }
        if (secsCount >= SHURIKEN_SECS) { //Reset secs and bat spawn count
            secsCount -= SHURIKEN_SECS;
            //If close enough
            if (this.center().distance(hayes.center()) < HAYES_DISTANCE) {
                //Fire shuriken in direction of Hayes
                gameEventQueue.addEvent(new AddEnemyEvent(new Shuriken(
                        center().x - Shuriken.IMAGE.getWidth()/2, center().y - Shuriken.IMAGE.getHeight()/2, 
                        directionToward(hayes), getRealmWidth(), getRealmHeight(), getBlocks()
                        )));
            }
        }
        super.tick(timeElapsed, gameEventQueue, hayes);
    } 
    
    //Be nice...drop 2 electrons
    @Override
    public void uponDeath (ExploringGameEventQueue gameEventQueue) {
        dropElectrons(gameEventQueue, 2);
    }

    @Override
    public double normalSpeed() {
        return SPEED;
    }

    @Override
    public Enemy duplicate() {
        return new Ninja(0, 0, getRealmWidth(), getRealmHeight(), getBlocks());
    }    
}
