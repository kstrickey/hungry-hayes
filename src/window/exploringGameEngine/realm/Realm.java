package window.exploringGameEngine.realm;

import java.util.ArrayList;
 


import java.util.HashMap;

import window.exploringGameEngine.simObjects.SimManager;
import window.exploringGameEngine.simObjects.background.Background;
import window.exploringGameEngine.simObjects.background.Block;
import window.exploringGameEngine.simObjects.enemies.Enemy;
import window.exploringGameEngine.simObjects.enemies.EnemySpawner;
import window.exploringGameEngine.simObjects.hayes.Hayes;
import window.exploringGameEngine.simObjects.hayes.HayesEnvironment;
import window.exploringGameEngine.simObjects.inanimates.Crib;
import window.exploringGameEngine.simObjects.inanimates.InanimateObject;
import window.screen.Screen;
 
public class Realm {
    
    /**
     * A constructor used in RealmFactory.
     * Use for Realms with a different HayesEnvironment than default.
     * If initialEnemies or initialInanimates or enemySpawners is null, it will be converted to an empty ArrayList of their respective types.
     * @param backgroundImageFileName : a String, omitting the prefix "/resources/images/"
     *              ex: "default background.png"
     * @param realmWidth : the int width of the Realm
     * @param realmHeight : the int height of the Realm
     * @param hayesEnvironment : the HayesEnvironment of this realm
     * @param blocks : an ArrayList of Block objects, including Portal objects (since they are subclasses of Block)
     * @param initialEnemies : an ArrayList of initial Enemy objects present when the Realm starts
     * @param initialInanimates: an ArrayList of initial inanimate objects 
     * @param enemySpawners : an ArrayList of EnemySpawner objects owned by the Realm
     * @param cribs : null if not Central
     * @param initialX : the x coordinate of Hayes' starting position (overridden if Realm IS Central)
     * @param initialY : the y coordinate of Hayes' starting position (overridden if Realm IS Central)
     * @param initialDirection : between 0 and 2pi (overridden if Realm IS Central)
     * @param xUponReturnToCentral : the x-position, in Central, of Hayes when he returns from the Realm (initialX if the Realm IS Central)
     * @param yUponReturnToCentral : the y-position, in Central, of Hayes when he returns from the Realm (initialY if the Realm IS Central)
     * @param directionUponReturnToCentral : the initial direction of Hayes when he returns to Central from the Realm (initialDirection if the Realm IS Central)
     */
    public Realm(String backgroundImageFileName, int realmWidth, int realmHeight, 
            HayesEnvironment hayesEnvironment, ArrayList<Block> blocks, ArrayList<Enemy> initialEnemies, 
            ArrayList<InanimateObject> initialInanimates, ArrayList<EnemySpawner> enemySpawners,
            HashMap<Integer, Crib> cribs,
            int initialX, int initialY, double initialDirection, 
            int xUponReturnToCentral, int yUponReturnToCentral, double directionUponReturnToCentral) {
    	
    	if (initialEnemies == null) {
    		initialEnemies = new ArrayList<Enemy>();
    	}
    	if (initialInanimates == null) {
    		initialInanimates = new ArrayList<InanimateObject>();
    	}
    	if (enemySpawners == null) {
    		enemySpawners = new ArrayList<EnemySpawner>();
    	}
    	
        this.backgroundImageFileName = backgroundImageFileName;
        this.width = realmWidth;
        this.height = realmHeight;
        this.hayesEnvironment = hayesEnvironment;
        this.blocks = blocks;
        this.initialEnemies = initialEnemies;
        this.initialInanimates = initialInanimates;
        this.enemySpawners = enemySpawners;
        this.cribs = cribs;
        this.initialX = initialX;
        this.initialY = initialY;
        this.initialDirection = initialDirection;
        this.xUponReturnToCentral = xUponReturnToCentral;
        this.yUponReturnToCentral = yUponReturnToCentral;
        this.directionUponReturnToCentral = directionUponReturnToCentral;
    }
    
    /**
     * A constructor used in RealmFactory.
     * Use for Realms with the default HayesEnvironment and no Cribs.
     * If initialEnemies or initialInanimates or enemySpawners is null, it will be converted to an empty ArrayList of their respective types.
     * @param backgroundImageFileName : a String, omitting the prefix "/resources/images/"
     *              ex: "default background.png"
     * @param realmWidth : the int width of the Realm
     * @param realmHeight : the int height of the Realm
     * @param blocks : an ArrayList of Block objects, including Portal objects (since they are subclasses of Block)
     * @param initialEnemies : an ArrayList of initial Enemy objects present when the Realm starts
     * @param initialInanimates: an ArrayList of initial inanimate objects 
     * @param enemySpawners : an ArrayList of EnemySpawner objects owned by the Realm
     * @param initialX : the x coordinate of Hayes' starting position (overridden if Realm IS Central)
     * @param initialY : the y coordinate of Hayes' starting position (overridden if Realm IS Central)
     * @param initialDirection : between 0 and 2pi (overridden if Realm IS Central)
     * @param xUponReturnToCentral : the x-position, in Central, of Hayes when he returns from the Realm (initialX if the Realm IS Central)
     * @param yUponReturnToCentral : the y-position, in Central, of Hayes when he returns from the Realm (initialY if the Realm IS Central)
     * @param directionUponReturnToCentral : the initial direction of Hayes when he returns to Central from the Realm (initialDirection if the Realm IS Central)
     */
    public Realm(String backgroundImageFileName, int realmWidth, int realmHeight, 
    		ArrayList<Block> blocks, ArrayList<Enemy> initialEnemies, 
    		ArrayList<InanimateObject> initialInanimates, ArrayList<EnemySpawner> enemySpawners, 
    		int initialX, int initialY, double initialDirection, 
    		int xUponReturnToCentral, int yUponReturnToCentral, double directionUponReturnToCentral) {
    	this(backgroundImageFileName, realmWidth, realmHeight, 
    			HayesEnvironment.DEFAULT, blocks, initialEnemies, 
    			initialInanimates, enemySpawners, null, 
    			initialX, initialY, initialDirection, 
    			xUponReturnToCentral, yUponReturnToCentral, directionUponReturnToCentral);
	}
    
    public int getXUponReturnToCentral() {
    	return xUponReturnToCentral;
    }
    
    public int getYUponReturnToCentral() {
    	return yUponReturnToCentral;
    }
    
    public double getDirectionUponReturnToCentral() {
    	return directionUponReturnToCentral;
    }
     
    public void applyRealmDimensions(Screen screen) {
        screen.setRealmWidth(width);
        screen.setRealmHeight(height);
    }
     
    /**
     * Constructs and returns a new Hayes object for gameplay.
     * @return
     */
    public Hayes newHayes() {                                           // TODO
        return new Hayes(initialX, initialY, width, height, blocks);
    }
     
    public Background newBackground() {                                 // TODO
        return new Background(width, height, backgroundImageFileName, blocks);
    }
     
    public SimManager newSimManager() {
        return new SimManager(width, height, initialEnemies, initialInanimates, enemySpawners, cribs);
    }
    
    /**
     * Applies width, height, blocks, initialX, initialY, initialDirection, and hayesEnvironment data to the Hayes.
     * Meant for use after Hayes transports to this Realm (but is not newly constructed).
     * @param hayes
     */
    public void applyRealmData(Hayes hayes) {
    	hayes.resetRealm(width, height, blocks);
    	hayes.reset(initialX, initialY, initialDirection);
    	hayes.setHayesEnvironment(hayesEnvironment);
    }
    
    private final String backgroundImageFileName;
    private final int width;
    private final int height;
    private final int initialX;
    private final int initialY;
    private final double initialDirection;
    private final HayesEnvironment hayesEnvironment;
    private final ArrayList<Block> blocks;
    private final ArrayList<Enemy> initialEnemies;
    private final ArrayList<InanimateObject> initialInanimates;
    private final ArrayList<EnemySpawner> enemySpawners;
    private final HashMap<Integer, Crib> cribs;
    private final int xUponReturnToCentral;
    private final int yUponReturnToCentral;
    private final double directionUponReturnToCentral;
     
}