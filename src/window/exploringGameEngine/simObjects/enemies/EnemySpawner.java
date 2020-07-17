package window.exploringGameEngine.simObjects.enemies;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import window.exploringGameEngine.gameEvents.AddEnemyEvent;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.simObjects.hayes.Hayes;

/**
 * A class owned by AllEnemies to determine how frequently and where a certain Enemy should be spawned during the level.
 * Each EnemySpawner instantiation can produce one type of Enemy.
 * 
 */
public class EnemySpawner {
	
	public static final Random generator = new Random();
	
	/**
	 * Constructor for Enemy Spawner with multiple spawn points.
	 * @param templateEnemy : enemy duplicated to spawn (duplicated as well when passed to Constructor)
	 * @param period : in seconds
	 * @param constantRate : if true, will spawn consistently once every period; if false, will spawn on average once every period (with a random element)
	 * @param spawnPoints : ArrayList of Point objects. Each spawn is chosen randomly from this list
	 */
	public EnemySpawner(Enemy templateEnemy, double period, boolean constantRate, ArrayList<Point> spawnPoints) {
		template = templateEnemy.duplicate();
		this.period = period;
		this.constantRate = constantRate;
		this.spawnPoints = spawnPoints;
		
		timeSinceLastSpawn = 0;
	}
	
	/**
	 * Constructor for Enemy Spawner with a single spawn point.
	 * @param templateEnemy : enemy duplicated to spawn (duplicated as well when passed to Constructor)
	 * @param period : in seconds
	 * @param constantRate : if true, will spawn consistently once every period; if false, will spawn on average once every period (with a random element)
	 * @param spawnPoint : the only Point from which this spawner spawns
	 */
	public EnemySpawner(Enemy templateEnemy, double period, boolean constantRate, Point spawnPoint) {
		template = templateEnemy.duplicate();
		this.period = period;
		this.constantRate = constantRate;
		spawnPoints = new ArrayList<Point>();
		spawnPoints.add(spawnPoint);
		
		timeSinceLastSpawn = 0;
	}
	
	/**
	 * Not from the Tickable interface, since it accepts targetHayes as a parameter.
	 * @param timeElapsed
	 * @param gameEventQueue
	 * @param targetHayes
	 */
	public void tick(double timeElapsed, ExploringGameEventQueue gameEventQueue, Hayes targetHayes) {
		// Check if the Enemy should spawn given the time and period
		if (constantRate) {
			timeSinceLastSpawn += timeElapsed;
			if (timeSinceLastSpawn >= period) {
				Enemy baby = createOne(targetHayes);	// Will not create Enemy on top of Hayes (that's just unfair)
				if (baby != null) {
					gameEventQueue.addEvent(new AddEnemyEvent(baby));
				}
				timeSinceLastSpawn = 0;
			}
		} else {
			if (generator.nextDouble() < timeElapsed / period) {
				Enemy baby = createOne(targetHayes);
				if (baby != null) {
					gameEventQueue.addEvent(new AddEnemyEvent(baby));
				}
			}
		}
	}
	
	/**
	 * Creates and returns an ArrayList of Enemy objects at the time the method is called.
	 * @param numberToSpawn
	 * @param targetHayes : if null, does not check for intersections
	 * @return a new ArrayList of Enemy objects created
	 */
	public ArrayList<Enemy> spawnNow(int numberToSpawn, Hayes targetHayes) {
		ArrayList<Enemy> babies = new ArrayList<>();
		for (int i = 0; i < numberToSpawn; ++i) {
			Enemy bby = createOne(targetHayes);
			if (bby != null) {
				babies.add(bby);
			}
		}
		return babies;
	}
	
	private Enemy createOne(Hayes targetHayes) {
		// Determine where the enemy should spawn and ensure it does not intersect Hayes
		Point spawnPt = spawnPoints.get(generator.nextInt(spawnPoints.size()));
		Enemy baby = template.duplicate();
		baby.setX(spawnPt.x);
		baby.setY(spawnPt.y);
		if (targetHayes != null) {
			if (baby.touching(targetHayes)) {
				return null;
			}
		}
		return baby;
	}
	
	private final Enemy template;					// the Enemy to be duplicated and spawned
	private final double period;					// the average time between enemy spawnings - must be > 0
	private final boolean constantRate;				// whether or not it spawns at a constant rate or with a random element
	private final ArrayList<Point> spawnPoints;		// a collection of spawning points of which any may be chosen for a given spawning
	
	private double timeSinceLastSpawn;				// NOTE: not used if !constantRate
	
}
