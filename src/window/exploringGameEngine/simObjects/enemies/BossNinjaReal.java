package window.exploringGameEngine.simObjects.enemies;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import window.exploringGameEngine.gameEvents.AddEnemyEvent;
import window.exploringGameEngine.gameEvents.AddInanimateEvent;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.background.Block;
import window.exploringGameEngine.simObjects.inanimates.Baby;

public class BossNinjaReal extends BossNinja {
    
    public static final int HEALTH = 3, DAMAGE = 1;
    
    private Rectangle2D.Double room;
    private Baby baby;
    
    /**
     * @param room The boss ninja needs to know the rectangle, in x-y coordinates corresponding
     * to the dimensions of the boss room, so that it can teleport and make fake duplicates of 
     * itself within the room. 
     */
    public BossNinjaReal (double x, double y, int realmWidth, int realmHeight, ArrayList<Block> blocks, Rectangle2D.Double room, Baby baby) {
        super(x, y, realmWidth, realmHeight, blocks, HEALTH, DAMAGE);
        this.room = room;
        this.baby = baby;
    }
    
    //Teleports and makes a doppelganger
    @Override
    public void punchedByHayes(ExploringGameEventQueue queue) {
        super.punchedByHayes(queue);
        Point2D.Double p1 = randomPositionInRoom();
        setX(p1.x);
        setY(p1.y);
        Point2D.Double p2 = randomPositionInRoom();
        queue.addEvent(new AddEnemyEvent(new BossNinjaFake(
                p2.x, p2.y, getRealmWidth(), getRealmHeight(), getBlocks()
                )));
    }
    
    /**
     * Returns random valid position of boss ninja in room such that he does
     * not intersect walls.
     */
    public Point2D.Double randomPositionInRoom () {
        double x = Math.random() * (room.width - IMAGE.getWidth()) + room.x;
        double y = Math.random() * (room.height - IMAGE.getHeight()) + room.y;
        return new Point2D.Double(x, y);
    }

    @Override
    public Enemy duplicate() {
        return new BossNinjaReal(0, 0, getRealmWidth(), getRealmHeight(), getBlocks(), room, baby);
    }
    
    /**
     * Drop baby
     */
    @Override
    public void uponDeath (ExploringGameEventQueue queue) {
        queue.addEvent(new AddInanimateEvent(baby));
    }
}
