package window.exploringGameEngine.simObjects.enemies;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.gameEvents.AddInanimateEvent;
import window.exploringGameEngine.gameEvents.EnemyHitsHayesEvent;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.gameEvents.KnockEnemyBackEvent;
import window.exploringGameEngine.gameEvents.RemoveEnemyEvent;
import window.exploringGameEngine.simObjects.SimObject;
import window.exploringGameEngine.simObjects.background.Block;
import window.exploringGameEngine.simObjects.hayes.Hayes;
import window.exploringGameEngine.simObjects.inanimates.Electron;

public abstract class Enemy extends SimObject {
	
	/**
	 * For use by an Enemy subclasses that need any random generation.
	 */
	public static final Random generator = new Random();
	
	/**
	 * The velocity of the enemy when knocked back after being punched.
	 * Used by KnockEnemyBackEvent.
	 */
	public static final double KNOCK_BACK_SPEED = 500.0;
	
	public Enemy(BufferedImage image, double x, double y, double width, double height, 
			double speed, double direction, int realmWidth, int realmHeight, boolean passive, 
			ArrayList<Block> blocks, int punchTolerance, double hayesHealthDamageUponContact) {
		super(image, x, y, width, height, speed, direction, realmWidth, realmHeight, passive, blocks);
		
		this.realmWidth = realmWidth;
		this.realmHeight = realmHeight;
		this.blocks = blocks;
		
		remainingPunchTolerance = punchTolerance;
		this.hayesHealthDamageUponContact = hayesHealthDamageUponContact;
	}
	
	public void tick(double timeElapsed, ExploringGameEventQueue gameEventQueue, Hayes hayes) {
		super.tick(timeElapsed, gameEventQueue);
		
		// If velocity is greater than normal speed, slow it down
		if (getVelocity().magnitude > normalSpeed() * timeElapsed) {
			getVelocity().magnitude -= 10 * timeElapsed;
		}
		
		// If velocity is less than normal speed, set to normal speed
		if (getVelocity().magnitude < normalSpeed() * timeElapsed) {
			getVelocity().magnitude = normalSpeed() * timeElapsed;
		}
		
		// Keep blinking if necessary
		if (blinkTimePassed != 0) {
			if (continueBlinking(timeElapsed)) {
				blinkTimePassed = 0;
				visible = true;
			}
		}
		
		// Remove if dead
		if (blinkTimePassed == 0 && remainingPunchTolerance == 0) {
			uponDeath(gameEventQueue);
			removeSelfFromRealm(gameEventQueue);
		}
	}
	
	@Override
	public void paint(PaintMachine paintMachine) {
		if (visible) {
			super.paint(paintMachine);
		}
	}
	
	@Override
	public void removeSelfFromRealm(ExploringGameEventQueue gameEventQueue) {
		gameEventQueue.addEvent(new RemoveEnemyEvent(this));
	}
	
	/**
	 * Called upon death of the enemy, just before an event is added to the 
	 * queue to remove the enemy from the realm. Default behavior is to 
	 * drop one electron.
	 * This method DOES NOT remove the Enemy upon death (that cannot be overridden).
	 */
	public void uponDeath(ExploringGameEventQueue gameEventQueue) {
	    dropElectron(gameEventQueue);
    }
	
	/**
	 * Places one electron at the center of this enemy.
	 */
	public void dropElectron (ExploringGameEventQueue gameEventQueue) {
	    Electron e = new Electron(center().x, center().y, getRealmWidth(), getRealmHeight());
	    gameEventQueue.addEvent(new AddInanimateEvent(e));
	}
	
    /**
     * The radius of the circle in which electrons are arranged.
     */
    public static final double E_RADIUS = 30;
    
	/**
	 * Places the specified number of electrons equally spaced in a circle
	 * around the center of this enemy.
	 */
	public void dropElectrons (ExploringGameEventQueue gameEventQueue, int numElectrons) {
        double phase = generator.nextDouble() * 2*Math.PI; // random starting turn angle
        double angle;
        for (int i = 0; i < numElectrons; i++) {
            angle = phase + i * 2*Math.PI/numElectrons;
            Electron e = new Electron(center().x + E_RADIUS*Math.cos(angle), 
                    center().y + E_RADIUS*Math.sin(angle), getRealmWidth(), getRealmHeight());
            gameEventQueue.addEvent(new AddInanimateEvent(e));
        }
	}

	
	/**
	 * Defines the normal velocity of the Enemy.
	 * @return
	 */
	public abstract double normalSpeed();
	
	public double getHayesHealthDamageUponContact() {
		return hayesHealthDamageUponContact;
	}
	
	/**
	 * Called by AllEnemies when the Enemy comes into contact with Hayes and is still intact (not dead).
	 * Will most often be used to kill Hayes.
	 * @param exploringGameEventQueue
	 */
	public void contactsHayes(ExploringGameEventQueue exploringGameEventQueue) {
		if (!isStunned() && remainingPunchTolerance > 0) {
			exploringGameEventQueue.addEvent(new EnemyHitsHayesEvent(this));
		}
	}
	
	/**
	 * Called by AllEnemies when the Enemy is punched by Hayes.
	 */
	public void punchedByHayes(ExploringGameEventQueue exploringGameEventQueue) {
		if (blinkTimePassed == 0) {
			--remainingPunchTolerance;
			stun(STANDARD_BLINK_TIME);
			
			exploringGameEventQueue.addEvent(new KnockEnemyBackEvent(this));
		}
	}
	
	// Stun sequence ===================================
	/**
	 * Length in time of one blink.
	 */
	public static final double BLINK_LENGTH = .10;
	/**
	 * Total time the Enemy is stunned.
	 */
	public static final double STANDARD_BLINK_TIME = 1.5;
	
	/**
	 * Starts blinking, stuns; knocks back.
	 * @param totalBlinkTime
	 */
	public void stun(double totalBlinkTime) {
		this.totalBlinkTime = totalBlinkTime;
		if (blinkTimePassed == 0) {
			blinkTimePassed = .01;
		}
	}
	
	/**
	 * Returns true if the blinkTimePassed > 0.
	 * @return
	 */
	public boolean isStunned() {	//System.out.println("Enemy.java: " + (blinkTimePassed > 0));
		return blinkTimePassed > 0;
	}
	
	/**
	 * Returns true if the blinking cycle has finished.
	 * @param timeElapsed
	 * @return
	 */
	private boolean continueBlinking(double timeElapsed) {
		blinkTimePassed += timeElapsed;
		visible = (int) (blinkTimePassed / BLINK_LENGTH) % 2 == 0;
		return blinkTimePassed >= totalBlinkTime;
	}
	
	private double blinkTimePassed = 0;
	private double totalBlinkTime;
	private boolean visible = true;
	
	// Convenience methods for direction 
	/**
	 * Returns one of pi/4, 3pi/4, 5pi/4, or 7pi/4 randomly.
	 */
    public static double random45 () {
        return (int) (4*Math.random()) * Math.PI/2 + Math.PI/4;
    }
    
    /**
     * Returns the direction in which this Enemy would face the
     * specified other sim object.
     */
    public double directionToward (SimObject other) {
        return Math.atan2(other.center().y - this.center().y, other.center().x - this.center().x);                
    }

	
	// =================================================
	
	/**
	 * Similar to clone. Instantiates and returns a new Enemy object of identical type. For use by EnemySpawner objects.
	 * May set arbitrary values for x, y, speed, and direction - these will be reset by EnemySpawners.
	 * @return a new Enemy instantiation with identical traits (except the four listed above)
	 */
	public abstract Enemy duplicate();
	
	public int getRealmWidth() {
		return realmWidth;
	}
	
	public int getRealmHeight() {
		return realmHeight;
	}
	
	public ArrayList<Block> getBlocks() {
		return blocks;
	}
	
	/**
	 * The # of successful punches by Hayes capable of being sustained before dying.
	 * If this is 0, the Enemy will die.
	 */
	private int remainingPunchTolerance;
	
	/**
	 * The amount of health taken from Hayes when the enemy contacts him.
	 */
	private double hayesHealthDamageUponContact;
	
	
	// Variables needed by subclasses for duplicate method
	private final int realmWidth, realmHeight;
	private final ArrayList<Block> blocks;
	
}
