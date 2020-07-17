package window.exploringGameEngine.simObjects.enemies;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import resources.classes.SquareSide;
import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.background.Block;
import window.exploringGameEngine.simObjects.hayes.Hayes;

public class Skeleton extends Enemy {

    public static final BufferedImage BONES_IMAGE = PaintMachine.getImageFromName("enemy/skull crossbones.png");
    public static final BufferedImage SKELETON_IMAGE = PaintMachine.getImageFromName("enemy/skeleton brian.png");
    /**
     * Transforms into skeleton when Hayes gets within this distance of the bones pile
     */
    public static final double HAYES_DISTANCE = 280;
    public static final int HEALTH = 2, DAMAGE = 1;
    public static final double SPEED = 300;    
    /*
     * Whether is currently in skeleton form
     */
    private boolean isSkeleton = false;
    
    /**
     * Construct a skeleton. Starts with bones image.
     */
    public Skeleton (double x, double y, int realmWidth, int realmHeight, ArrayList<Block> blocks) {
        super(BONES_IMAGE, x, y, BONES_IMAGE.getWidth(), BONES_IMAGE.getHeight(), 0, 0, realmWidth, realmHeight, false, blocks, HEALTH, DAMAGE);
        //Random direction
        setDirection(Math.random() * Math.PI * 2);
    }
    
    public void tick (double timeElapsed, ExploringGameEventQueue gameEventQueue, Hayes hayes) {
        if (! isSkeleton && hayes.center().distance(this.center()) < HAYES_DISTANCE) {
            //Transform into skeleton
            setImage(SKELETON_IMAGE);
            //Position so that the two images have the same center
            setX(center().x - SKELETON_IMAGE.getWidth()/2);
            setY(center().y - SKELETON_IMAGE.getHeight()/2);
            setWidth(SKELETON_IMAGE.getWidth());
            setHeight(SKELETON_IMAGE.getHeight());
            isSkeleton = true;
        }
        super.tick(timeElapsed, gameEventQueue, hayes);
    }
    
    /**
     * Skeleton is a simple bouncing enemy that always travels at this speed
     */
    public double normalSpeed() {
        if (isSkeleton)
            return SPEED;
        return 0;
    }
    
    /**
     * Bounce in a random direction.
     */
    public void bounce(SquareSide wallSide) {
        switch (wallSide) {
        case BOTTOM: //0 to pi
            setDirection(Math.random() * Math.PI);
            break;
        case RIGHT: //pi/2 to 3pi/2
            setDirection(Math.random() * Math.PI + Math.PI/2);
            break;
        case TOP: //pi to 2pi
            setDirection(Math.random() * Math.PI + Math.PI);
            break;
        case LEFT: //3pi/2 to 5pi/2
            setDirection(Math.random() * Math.PI + 3*Math.PI/2);
            break;
        }
    }
    
    /**
     * Drop 2 electrons
     */
    @Override
    public void uponDeath (ExploringGameEventQueue queue) {
        dropElectrons(queue, 2);
    }


    public Enemy duplicate() {
        return new Skeleton(0, 0, getRealmWidth(), getRealmHeight(), getBlocks());
    }    
}
