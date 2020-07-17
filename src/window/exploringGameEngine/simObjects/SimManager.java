package window.exploringGameEngine.simObjects;

import java.util.ArrayList;
import java.util.HashMap;

import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.enemies.Enemy;
import window.exploringGameEngine.simObjects.enemies.EnemySpawner;
import window.exploringGameEngine.simObjects.hayes.Hayes;
import window.exploringGameEngine.simObjects.inanimates.Crib;
import window.exploringGameEngine.simObjects.inanimates.InanimateObject;


/**
 * Owns references to all sim objects. Updates and ticks all of them.
 */
public class SimManager extends SimObject {
	
	public SimManager(int realmWidth, int realmHeight, 
			ArrayList<Enemy> initialEnemies, ArrayList<InanimateObject> initialInanimates,
			ArrayList<EnemySpawner> enemySpawners, HashMap<Integer, Crib> cribs) {
		super(realmWidth, realmHeight);
		this.enemySpawners = enemySpawners;
		
		// Start enemies
		enemies = new ArrayList<Enemy>();
		enemies.addAll(initialEnemies);
		
		// Start inanimates
		inanimates = new ArrayList<InanimateObject>();
		inanimates.addAll(initialInanimates);
		
		// Add Cribs
		this.cribs = cribs;			// does not addAll. maybe it should
	}
	
	@Override
	public void tick(double timeElapsed, ExploringGameEventQueue gameEventQueue) {
		// Tick Enemies
		for (Enemy enemy : enemies) {
			enemy.tick(timeElapsed, gameEventQueue, targetHayes);
			if (targetHayes.isPunchingSuccessfully(enemy)) {
				// If applicable, enemy gets punched by Hayes... let it deal with that
				enemy.punchedByHayes(gameEventQueue);
			} else if (enemy.touching(targetHayes)) {
				// Or, if applicable, enemy gets to Hayes alive... let it deal with that too
				enemy.contactsHayes(gameEventQueue);
			}
		}
		
		// Tick Inanimates
		for (InanimateObject inanimate : inanimates) {
            inanimate.tick(timeElapsed, gameEventQueue, targetHayes);
        }
		
		// Use EnemySpawners to spawn Enemies
		for (EnemySpawner spawner : enemySpawners) {
			spawner.tick(timeElapsed, gameEventQueue, targetHayes);
		}
	}
	
	@Override
	public void paint(PaintMachine paintMachine) {
		// Paint all Enemies
		for (Enemy enemy : enemies) {
			enemy.paint(paintMachine);
		}
		
		// Paint all Inanimates
		for (InanimateObject inanimate : inanimates) {
			inanimate.paint(paintMachine);
		}
		
		// Paint all Cribs
		if (cribs != null) {
			for (Crib crib : cribs.values()) {
				crib.paint(paintMachine);
			}
		}
	}
	
	@Override
	public void removeSelfFromRealm(ExploringGameEventQueue gameEventQueue) { /* will never be removed */ }
	
	public void setTargetHayes(Hayes targetHayes) {
		this.targetHayes = targetHayes;
	}
	
	/**
	 * Removes a specific Enemy from the list. Should not be called during iteration.
	 * @param enemy
	 */
	public void removeEnemy(Enemy enemy) {
		enemies.remove(enemy);
	}
	/**
	 * Add the specified enemy to the list.
	 * @param enemy
	 */
	public void addEnemy(Enemy enemy) {
	    enemies.add(enemy);
	}
	
	
	   
    /**
     * Remove the specified inanimate from the list.
     */
    public void removeInanimate(InanimateObject inanimate) {
        inanimates.remove(inanimate);
    }
    /**
     * Add the specified inanimate to the list.
     */
    public void addInanimate(InanimateObject inanimate) {
        inanimates.add(inanimate);
    }
    
    /**
     * Sets the specified crib either true or false to hasBaby.
     * @param cribRealmID
     * @param filled
     */
    public void setCribFilled(int cribRealmID, boolean filled) {
    	cribs.get(cribRealmID).setBaby(true);
    }
    
    /**
	 * Method to determine if the player has won by checking if all cribs are occupied.
	 * @return true if all cribs occupied, false otherwise
	 */
    public boolean allCribsOccupied() {
    	for (Crib crib : cribs.values()) {
    		if (!crib.isOccupied()) {
    			return false;
    		}
    	}
    	return true;
    }
    
	
	private final ArrayList<EnemySpawner> enemySpawners;
	
	private ArrayList<Enemy> enemies;
	private ArrayList<InanimateObject> inanimates;		// should NOT include Cribs
	private final HashMap<Integer, Crib> cribs;			// one Crib for each RealmID
	
	private Hayes targetHayes;
	
}
