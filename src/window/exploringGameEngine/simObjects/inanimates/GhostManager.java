package window.exploringGameEngine.simObjects.inanimates;

import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.gameEvents.AddEnemyEvent;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.enemies.BossGhost;
import window.exploringGameEngine.simObjects.hayes.Hayes;
import window.gameEvents.AddMessageImageEvent;

public class GhostManager extends InanimateObject {
    private int numGhosts;
    private BossGhost bossGhost;
    
    /**
     * Invisible inanimate sim object.
     * When the specified number of evil ghosts die, the GhostManager
     * will place the specified boss ghost.
     */
    public GhostManager (int numGhosts, BossGhost bossGhost) {
        super(null, 0, 0, 0, 0, 0, 0);
        this.numGhosts = numGhosts;
        this.bossGhost = bossGhost;
    }
    
    public void ghostDied (ExploringGameEventQueue queue) {
        numGhosts--;
        if (numGhosts == 0) {
            queue.addEvent(new AddMessageImageEvent(
                    PaintMachine.getImageFromName("textbox/boss ghost.png")
                    ));
            queue.addEvent(new AddEnemyEvent(bossGhost));
        }
    }
    
    boolean isFirstFrame = true;
    /**
     * If first frame, display "kill all evil ghosts" message
     */
    @Override
    public void tick(double timeElapsed,
            ExploringGameEventQueue gameEventQueue, Hayes hayes) {
        if (isFirstFrame) {
            gameEventQueue.addEvent(new AddMessageImageEvent(
                    PaintMachine.getImageFromName("textbox/evil ghosts.png")
                    ));
            isFirstFrame = false;
        }
    }
}
