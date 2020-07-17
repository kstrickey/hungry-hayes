package window.exploringGameEngine.simObjects.enemies;

import java.util.ArrayList;

import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.background.Block;

public class BossNinjaFake extends BossNinja {
    
    public static final int HEALTH = 1, DAMAGE = 1;

    /**
     * Only BossNinjaReal should make fake boss ninjas
     */
    protected BossNinjaFake (double x, double y, int realmWidth, int realmHeight, ArrayList<Block> blocks) {
        super(x, y, realmWidth, realmHeight, blocks, HEALTH, DAMAGE);
        //Starts stunned
        stun(STANDARD_BLINK_TIME);
    }

    @Override
    public Enemy duplicate() {
        return new BossNinjaFake(0, 0, getRealmWidth(), getRealmHeight(), getBlocks());
    }
    
    @Override
    //Drop nothing
    public void uponDeath (ExploringGameEventQueue queue) {}
}
