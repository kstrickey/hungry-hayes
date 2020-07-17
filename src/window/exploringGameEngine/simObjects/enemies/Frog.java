package window.exploringGameEngine.simObjects.enemies;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.background.Block;
import window.exploringGameEngine.simObjects.hayes.Hayes;

public class Frog extends Enemy {
    
    public static final BufferedImage IMAGE = PaintMachine.getImageFromName("enemy/frog manik.png");
    public static final int HEALTH = 1, DAMAGE = 1;
    //Maximum speed of frog
    public static final double MAX_SPEED = 400, 
            JUMP_PERIOD = 0.65, //Length of time that the frog is jumping
            TOTAL_PERIOD = 1, //Time between start of two consecutive jumps; jump time + rest time
            HAYES_DISTANCE = 400;
    private double timeCounter = 0;
    private double normalSpeed;
    
    public Frog (double x, double y, int realmWidth, int realmHeight, ArrayList<Block> blocks) {
        super(IMAGE, x, y, IMAGE.getWidth(), IMAGE.getHeight(), 0, 0, realmWidth, realmHeight, false, blocks, HEALTH, DAMAGE);
    }
    
            
    public void tick(double timeElapsed, ExploringGameEventQueue gameEventQueue, Hayes hayes) {
        timeCounter += timeElapsed;
        //If a period has been completed
        if (timeCounter > TOTAL_PERIOD) {
            timeCounter -= TOTAL_PERIOD;
            //If Hayes is close enough, face him
            if (hayes.center().distance(this.center()) < HAYES_DISTANCE) {
                setDirection(directionToward(hayes));                
            }
            //Else, pick random direction
            else {
                setDirection(Math.random() * 2*Math.PI);
            }
        }        
        //Jumping
        if (timeCounter <= JUMP_PERIOD) {
            /*
             * Sinusoidal function with range (0, JUMP_PERIOD) returning positive values, and amplitude MAX_SPEED,
             */
            normalSpeed = MAX_SPEED * Math.sin(Math.PI * timeCounter / JUMP_PERIOD);
        }
        //Resting
        else
            normalSpeed = 0;
        super.tick(timeElapsed, gameEventQueue, hayes);        
    }
 

    @Override
    public double normalSpeed() {
        // TODO Auto-generated method stub
        return normalSpeed;
    }

    @Override
    public Enemy duplicate() {
        return new Frog(0, 0, getRealmWidth(), getRealmHeight(), getBlocks());
    }
}
