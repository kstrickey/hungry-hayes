package window.exploringGameEngine.realm;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;

import window.exploringGameEngine.simObjects.background.Block;
import window.exploringGameEngine.simObjects.background.Portal;
import window.exploringGameEngine.simObjects.enemies.BossGhost;
import window.exploringGameEngine.simObjects.enemies.BossNinjaReal;
import window.exploringGameEngine.simObjects.enemies.Car;
import window.exploringGameEngine.simObjects.enemies.Crab;
import window.exploringGameEngine.simObjects.enemies.Enemy;
import window.exploringGameEngine.simObjects.enemies.EnemySpawner;
import window.exploringGameEngine.simObjects.enemies.EvilGhost;
import window.exploringGameEngine.simObjects.enemies.FastCar;
import window.exploringGameEngine.simObjects.enemies.Frog;
import window.exploringGameEngine.simObjects.enemies.Jellyfish;
import window.exploringGameEngine.simObjects.enemies.MonsterKevin;
import window.exploringGameEngine.simObjects.enemies.Ninja;
import window.exploringGameEngine.simObjects.enemies.Shark;
import window.exploringGameEngine.simObjects.enemies.Skeleton;
import window.exploringGameEngine.simObjects.enemies.SlowCar;
import window.exploringGameEngine.simObjects.enemies.UselessStudent;
import window.exploringGameEngine.simObjects.hayes.HayesEnvironment;
import window.exploringGameEngine.simObjects.inanimates.Baby;
import window.exploringGameEngine.simObjects.inanimates.Barrier;
import window.exploringGameEngine.simObjects.inanimates.BarrierType;
import window.exploringGameEngine.simObjects.inanimates.Breath;
import window.exploringGameEngine.simObjects.inanimates.Crib;
import window.exploringGameEngine.simObjects.inanimates.Electron;
import window.exploringGameEngine.simObjects.inanimates.FakeBaby;
import window.exploringGameEngine.simObjects.inanimates.GhostManager;
import window.exploringGameEngine.simObjects.inanimates.InanimateObject;
import window.exploringGameEngine.simObjects.inanimates.Lever;

/**
 * The class designed to store and create Realm objects on command.
 *
 */
public class RealmFactory {
	
	// REALM IDs - used for identification with babies and Portals
	public static final int ID_CENTRAL = 1;
	public static final int ID_POOL = 2;
	public static final int ID_HAUNTED = 3;
	public static final int ID_JUNGLE = 4;
	public static final int ID_ROAD = 5;
	public static final int ID_CASTLE = 6;
	public static final int ID_NURSERY = 7;
	
	
	/**
	 * Generates a new Realm given the realm ID, as defined by RealmFactory's static constants.
	 * @param realmID
	 * @return
	 */
	public static Realm newRealm(int realmID) {
		switch (realmID) {
		case ID_CENTRAL:
			return newCentralRealm();
		case ID_POOL:
			return newPoolRealm();
		case ID_HAUNTED:
			return newHauntedRealm();
		case ID_JUNGLE:
			return newJungleRealm();
		case ID_ROAD:
			return newRoadRealm();
		case ID_CASTLE:
			return newCastleRealm();
		case ID_NURSERY:
			return newNurseryRealm();
		default:
			System.out.println("Error: No realm exists with this ID.");
			System.exit(1);
		}
		return null;	// unreachable code
	}
	
	
	/**
	 * Static method called to construct and return a new Realm based in Central, to be stored as the homeGameEngine.
	 * Should only be called once, at the start of gameplay.
	 * @return a new Realm, initialized to represent NCHS
	 */
    public static Realm newCentralRealm() {
    	final int realmWidth = 4596, realmHeight = 3856;
    	
        /*
         * Sets up an array list of all Block objects, including Portal objects, in the Realm
         */
        ArrayList<Block> centralBlocks = new ArrayList<Block>();
        //walls--------------------------
        centralBlocks.add(new Block(1066,100,2908,61));
        centralBlocks.add(new Block(1066,161,92,690));
        centralBlocks.add(new Block(2227,161,203,690));
        centralBlocks.add(new Block(1066,851,293,62));
        centralBlocks.add(new Block(1698,851,1668,57));
        centralBlocks.add(new Block(3860,161,114,690));
        centralBlocks.add(new Block(3711,851,263,57));
        centralBlocks.add(new Block(2715,1199,1254,117));
        centralBlocks.add(new Block(2715,1314,96,427));
        centralBlocks.add(new Block(2715,2126,96,488));
        centralBlocks.add(new Block(2715,2614,1257,117));
        centralBlocks.add(new Block(3872,1314,100,1300));
        centralBlocks.add(new Block(100,1661,444,78));
        centralBlocks.add(new Block(100,1739,33,910));
        centralBlocks.add(new Block(100,2649,445,81));
        centralBlocks.add(new Block(507,2258,38,391));
        centralBlocks.add(new Block(507,1739,38,148));
        centralBlocks.add(new Block(832,1199,1603,546));
        centralBlocks.add(new Block(832,1745,127,896));
        centralBlocks.add(new Block(832,2641,486,87));
        centralBlocks.add(new Block(1730,2641,704,87));
        centralBlocks.add(new Block(2305,1745,132,897));
        centralBlocks.add(new Block(100,3016,477,93));
        centralBlocks.add(new Block(100,3109,135,562));
        centralBlocks.add(new Block(100,3671,1091,91));
        centralBlocks.add(new Block(952,3016,239,93));
        centralBlocks.add(new Block(1061,3109,131,564));
        centralBlocks.add(new Block(1188,3016,413,49));
        centralBlocks.add(new Block(1188,3065,114,648));
        centralBlocks.add(new Block(1188,3713,2242,49));
        centralBlocks.add(new Block(2824,3065,190,648));
        centralBlocks.add(new Block(1991,3016,1292,49));
        centralBlocks.add(new Block(3639,3016,332,49));
        centralBlocks.add(new Block(3905,3065,67,497));
        centralBlocks.add(new Block(3488,3667,116,57));
        centralBlocks.add(new Block(3606,3612,196,38));
        centralBlocks.add(new Block(3779,3555,115,43));
        centralBlocks.add(new Block(4254,912,221,2574));
        centralBlocks.add(new Block(3972,3102,281,67));
        centralBlocks.add(new Block(3972,587,505,53));
//        centralBlocks.add(new Block(913,538,445,741));
        //portals------------------------
        centralBlocks.add(new Portal(1542, 365, 265, 265, ID_POOL));
        centralBlocks.add(new Portal(488, 3267, 263, 263, ID_ROAD));
        centralBlocks.add(new Portal(3038, 375, 267, 267, ID_CASTLE));
        centralBlocks.add(new Portal(3202, 1797, 281, 281, ID_JUNGLE));		// outer boundary of button - unlike the rest
        centralBlocks.add(new Portal(1933, 3262, 281, 281, ID_HAUNTED));
        centralBlocks.add(new Portal(3213, 3252, 281, 281, ID_NURSERY));	// outer boundary of button
        // TODO enter portals to supercomputer, APCS rooms
        
        /*
         * Sets up an array list of all Enemy objects present when the Realm starts
         */
        ArrayList<Enemy> centralInitialEnemies = new ArrayList<Enemy>(); // TODO
        
        ArrayList<InanimateObject> centralInitialInanimates = new ArrayList<InanimateObject>();

        /*
         * Sets up an array list of all EnemySpawner objects owned by the Realm
         */
        ArrayList<EnemySpawner> centralEnemySpawners = new ArrayList<EnemySpawner>(); // TODO
        
        Enemy templateUselessStudent = new UselessStudent(825, 3230, realmWidth, realmHeight, centralBlocks);
        ArrayList<Point> spawnPoints = new ArrayList<>();
        spawnPoints.add(new Point(4290, 3628));
        spawnPoints.add(new Point(3626, 3180));
        spawnPoints.add(new Point(825, 3230));
        spawnPoints.add(new Point(1659, 3412));
        spawnPoints.add(new Point(2509, 3234));
        spawnPoints.add(new Point(1433, 2721));
        spawnPoints.add(new Point(285, 2376));
        spawnPoints.add(new Point(2502, 1205));
        spawnPoints.add(new Point(4019, 3085));
        spawnPoints.add(new Point(3615, 1866));
        spawnPoints.add(new Point(3048, 956));
        spawnPoints.add(new Point(3515, 250));
        spawnPoints.add(new Point(2633, 432));
        spawnPoints.add(new Point(1263, 436));
        spawnPoints.add(new Point(1849, 661));
        spawnPoints.add(new Point(3779, 2766));
       	EnemySpawner uselessStudentSpawner = new EnemySpawner(templateUselessStudent, 10, false, spawnPoints);
       	centralEnemySpawners.add(uselessStudentSpawner);
       	centralInitialEnemies.addAll(uselessStudentSpawner.spawnNow(50, null));
       	
       	HashMap<Integer, Crib> centralCribs = new HashMap<>();
       	centralCribs.put(ID_POOL, new Crib(1229, 687, realmWidth, realmHeight));
       	centralCribs.put(ID_HAUNTED, new Crib(1409, 3151, realmWidth, realmHeight));
       	centralCribs.put(ID_JUNGLE, new Crib(2870, 2109, realmWidth, realmHeight));
        centralCribs.put(ID_ROAD, new Crib(254, 3117, realmWidth, realmHeight));
        centralCribs.put(ID_CASTLE, new Crib(3053, 699, realmWidth, realmHeight));
        centralCribs.put(ID_NURSERY, new Crib(3576, 3105, realmWidth, realmHeight));
       	
        /*
         * Constructs, returns a Central Realm
         */
        Realm centralRealm = new Realm("realms/central.png", realmWidth, realmHeight, HayesEnvironment.DEFAULT, centralBlocks,
                centralInitialEnemies, centralInitialInanimates, centralEnemySpawners, centralCribs, 249, 347, 0, 249, 347, 0);
        return centralRealm;
    }
    
    /**
     * Static method that returns a new Realm instance representing the pool level.
     * @return a new Realm instance representing the pool level
     */
    public static Realm newPoolRealm() {
    	final int realmWidth = 2260, realmHeight = 2628;
    	
        /*
         * Sets up an array list of all Block objects, including Portal objects, in the Realm
         */
        ArrayList<Block> poolBlocks = new ArrayList<Block>();
        //walls--------------------------
        poolBlocks.add(new Block(0,0,55,2626));
        poolBlocks.add(new Block(702,219,63,367));
        poolBlocks.add(new Block(55,520,384,49));
        poolBlocks.add(new Block(765,508,321,79));
        poolBlocks.add(new Block(55,835,686,73));
        poolBlocks.add(new Block(55,1708,337,140));
        poolBlocks.add(new Block(392,1200,66,751));
        poolBlocks.add(new Block(702, 931,63,1005));
        poolBlocks.add(new Block(783, 925, 291, 84));
        poolBlocks.add(new Block(1011,596,71,322));
        poolBlocks.add(new Block(1318,210,60,376));
        poolBlocks.add(new Block(1394,519,572,85));
        poolBlocks.add(new Block(1614,0,80,514));
        poolBlocks.add(new Block(1931,617,70,709));
        poolBlocks.add(new Block(1288,811,421,63));
        poolBlocks.add(new Block(1630,888,75,991));
        poolBlocks.add(new Block(1004,1266,616,91));
        poolBlocks.add(new Block(1011,1369,72,833));
        poolBlocks.add(new Block(817,2223,604,89));
        poolBlocks.add(new Block(414,2245,94,383));
        poolBlocks.add(new Block(523,2597,1737,30));
        poolBlocks.add(new Block(2205,1185,55,1399));
        poolBlocks.add(new Block(2013,1255,182,79));
        poolBlocks.add(new Block(1716,1616,305,54));
        poolBlocks.add(new Block(777,1493,223,96));
        poolBlocks.add(new Block(1326,1921,676,69));
        poolBlocks.add(new Block(1318,1558,70,350));
        poolBlocks.add(new Block(1843,2303,66,286));
        poolBlocks.add(new Block(1711,2239,321,52));
        //portal back to central
        poolBlocks.add(new Portal(108, 50, 80, 80, ID_CENTRAL));
         
        /*
         * Sets up an array list of all Enemy objects present when the Realm starts
         */
        ArrayList<Enemy> poolInitialEnemies = new ArrayList<Enemy>();
        poolInitialEnemies.add(new Shark(875, 269, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Crab(398, 607, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Jellyfish(199, 599, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Jellyfish(1438, 258, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Crab(1463, 379, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Crab(832, 1250, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Shark(1377, 1023, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Jellyfish(1481, 1081, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Crab(1663, 651, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Shark(1745, 1145, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Jellyfish(1765, 823, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Crab(1777, 1733, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Jellyfish(1943, 1747, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Jellyfish(1981, 2357, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Jellyfish(2003, 2413, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Jellyfish(2035, 2411, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Shark(1435, 1707, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Jellyfish(1467, 1637, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Crab(1440, 1680, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Crab(1189, 1407, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Shark(1373, 2058, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Crab(1610, 2381, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Jellyfish(917, 2387, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Crab(605, 2201, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Shark(823, 1727, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Shark(153, 2378, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Shark(501, 1367, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Crab(143, 987, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Crab(146, 1276, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Jellyfish(212, 1520, realmWidth, realmHeight, poolBlocks));
        poolInitialEnemies.add(new Jellyfish(217, 1040, realmWidth, realmHeight, poolBlocks));
        
        /*
         * Sets up an array list of all Inanimate objects present when the Realm starts
         */
        ArrayList<InanimateObject> poolInitialInanimates = new ArrayList<InanimateObject>();
        // Baby (ultimate goal of this game and life in general)
        poolInitialInanimates.add(new Baby(129, 1382, realmWidth, realmHeight));
        // Breath (omnipresent suffocating force)
        poolInitialInanimates.add(new Breath());
        // Various scattered Electrons
        poolInitialInanimates.add(new Electron(146, 449, realmWidth, realmHeight));
        poolInitialInanimates.add(new Electron(75, 1125, realmWidth, realmHeight));
        poolInitialInanimates.add(new Electron(335, 1626, realmWidth, realmHeight));
        poolInitialInanimates.add(new Electron(305, 1583, realmWidth, realmHeight));
        poolInitialInanimates.add(new Electron(856, 622, realmWidth, realmHeight));
        poolInitialInanimates.add(new Electron(1377, 143, realmWidth, realmHeight));
        poolInitialInanimates.add(new Electron(1883, 681, realmWidth, realmHeight));
        poolInitialInanimates.add(new Electron(1537, 951, realmWidth, realmHeight));
        poolInitialInanimates.add(new Electron(851, 1080, realmWidth, realmHeight));
        poolInitialInanimates.add(new Electron(1588, 1574, realmWidth, realmHeight));
        poolInitialInanimates.add(new Electron(2120, 2371, realmWidth, realmHeight));
        poolInitialInanimates.add(new Electron(910, 2079, realmWidth, realmHeight));
        poolInitialInanimates.add(new Electron(806, 1413, realmWidth, realmHeight));
        poolInitialInanimates.add(new Electron(952, 1344, realmWidth, realmHeight));
        poolInitialInanimates.add(new Electron(2121, 1379, realmWidth, realmHeight));
        poolInitialInanimates.add(new Electron(1569, 1625, realmWidth, realmHeight));
        poolInitialInanimates.add(new Electron(1337, 1377, realmWidth, realmHeight));
        poolInitialInanimates.add(new Electron(1957, 2371, realmWidth, realmHeight));
        poolInitialInanimates.add(new Electron(1123, 1885, realmWidth, realmHeight));
        poolInitialInanimates.add(new Electron(173, 2559, realmWidth, realmHeight));
        poolInitialInanimates.add(new Electron(243, 2585, realmWidth, realmHeight));
        poolInitialInanimates.add(new Electron(322, 2521, realmWidth, realmHeight));
        poolInitialInanimates.add(new Electron(403, 1143, realmWidth, realmHeight));
        poolInitialInanimates.add(new Electron(558, 1499, realmWidth, realmHeight));
        
        /*
         * Sets up an array list of all EnemySpawner objects owned by the Realm
         */
        ArrayList<EnemySpawner> poolEnemySpawners = new ArrayList<EnemySpawner>(); // TODO
        
        /*
         * Sets up the pool HayesEnvironment with a reduced max speed (since in water)
         */
        HayesEnvironment poolEnv = new HayesEnvironment(HayesEnvironment.UNDERWATER);
        //poolEnv.maxSpeed = 150;
        
        /*
         * Constructs, returns a pool Realm
         */
        Realm poolRealm = new Realm("realms/pool.png", realmWidth, realmHeight, poolEnv, poolBlocks, 
                poolInitialEnemies, poolInitialInanimates, poolEnemySpawners, null, 
                361, 222, 0, 1870, 391, 0);
        return poolRealm;
    }
    
    /**
     * Static method that constructs and returns a new haunted Realm.
     * @return a new Realm instance representing the haunted level
     */
    public static Realm newHauntedRealm() {
    	/*
         * Sets up an array list of all Block objects, including Portal objects, in the Realm
         */
        ArrayList<Block> hauntedBlocks = new ArrayList<Block>();
        //walls--------------------------
        hauntedBlocks.add(new Block(1934,513,182,63));
        hauntedBlocks.add(new Block(1860,434,117,59));
        hauntedBlocks.add(new Block(1787,352,115,61));
        hauntedBlocks.add(new Block(1708,275,108,57));
        hauntedBlocks.add(new Block(1629,189,116,59));
        hauntedBlocks.add(new Block(1542,92,109,61));
        hauntedBlocks.add(new Block(1450,10,109,59));
        hauntedBlocks.add(new Block(740,23,106,67));
        hauntedBlocks.add(new Block(646,108,121,74));
        hauntedBlocks.add(new Block(557,218,121,61));
        hauntedBlocks.add(new Block(470,309,121,69));
        hauntedBlocks.add(new Block(342,422,133,76));
        hauntedBlocks.add(new Block(247,533,123,68));
        hauntedBlocks.add(new Block(0,607,267,75));
        hauntedBlocks.add(new Block(383,781,814,61));
        hauntedBlocks.add(new Block(1142,858,80,80));
        hauntedBlocks.add(new Block(1231,929,80,80));
        hauntedBlocks.add(new Block(1315,1013,68,69));
        hauntedBlocks.add(new Block(1403,1091,60,62));
        hauntedBlocks.add(new Block(1486,1151,357,64));
        hauntedBlocks.add(new Block(1777,1230,79,153));
        hauntedBlocks.add(new Block(1850,776,268,64));
        hauntedBlocks.add(new Block(0,1383,1580,25));
        hauntedBlocks.add(new Block(387,1647,385,46));
        hauntedBlocks.add(new Block(733,1691,37,438));
        //another block
        hauntedBlocks.add(new Block(1644, 1647, 44, 501));
        
        /*
         * Sets up an array list of all Enemy objects present when the Realm starts
         */
        ArrayList<Enemy> hauntedInitialEnemies = new ArrayList<Enemy>(); // TODO
        
        final int REALM_W = 2117, REALM_H = 2150;
        
        ArrayList<InanimateObject> hauntedInitialInanimates = new ArrayList<InanimateObject>();
        //Third level bed
        Baby baby = new Baby(511, 1775, REALM_W, REALM_H);
        //Below lower left stairwell
        BossGhost boss = new BossGhost(120, 120, REALM_W, REALM_H, baby);
        GhostManager gm = new GhostManager(4, boss);
        hauntedInitialInanimates.add(gm);
        
        //First floor, at top of right stairwell
        hauntedInitialEnemies.add(new EvilGhost(1929, 619, REALM_W, REALM_H, hauntedBlocks, gm));
        //Second floor left of stairwell
        hauntedInitialEnemies.add(new EvilGhost(737, 927, REALM_W, REALM_H, hauntedBlocks, gm));
        //Third floor bed
        hauntedInitialEnemies.add(new EvilGhost(511, 1775, REALM_W, REALM_H, hauntedBlocks, gm));
        //Third floor top right
        hauntedInitialEnemies.add(new EvilGhost(1751, 1970, REALM_W, REALM_H, hauntedBlocks, gm));
        
        //Skeletons
        //Second floor left
        hauntedInitialEnemies.add(new Skeleton(804, 1055, REALM_W, REALM_H, hauntedBlocks));
        //Second floor right
        hauntedInitialEnemies.add(new Skeleton(2000, 1061, REALM_W, REALM_H, hauntedBlocks));
        //Third floor left
        hauntedInitialEnemies.add(new Skeleton(132, 1730, REALM_W, REALM_H, hauntedBlocks));
        //Third floor top, electron pile
        hauntedInitialEnemies.add(new Skeleton(1314, 1994, REALM_W, REALM_H, hauntedBlocks));
        
        //Electrons
        //Second floor couch
        hauntedInitialInanimates.add(new Electron(821, 1012, REALM_W, REALM_H));
        hauntedInitialInanimates.add(new Electron(861, 1022, REALM_W, REALM_H));
        //Second floor stairwell
        hauntedInitialInanimates.add(new Electron(1473, 923, REALM_W, REALM_H));
        hauntedInitialInanimates.add(new Electron(1497, 893, REALM_W, REALM_H));
        //Third floor right, for free
        hauntedInitialInanimates.add(new Electron(1909, 1784, REALM_W, REALM_H));
        hauntedInitialInanimates.add(new Electron(1971, 1724, REALM_W, REALM_H));
        //Third floor top electron pile
        hauntedInitialInanimates.add(new Electron(1161, 2054, REALM_W, REALM_H));
        hauntedInitialInanimates.add(new Electron(1206, 2088, REALM_W, REALM_H));
        hauntedInitialInanimates.add(new Electron(1212, 1961, REALM_W, REALM_H));
                //Third floor near bed
        hauntedInitialInanimates.add(new Electron(252, 1598, REALM_W, REALM_H));



        /*
         * Sets up an array list of all EnemySpawner objects owned by the Realm
         */
        ArrayList<EnemySpawner> hauntedEnemySpawners = new ArrayList<EnemySpawner>(); // TODO
         
        /*
         * Constructs, returns a haunted Realm
         */
        Realm hauntedRealm = new Realm("realms/haunted.png", REALM_W, REALM_H, hauntedBlocks, 
                hauntedInitialEnemies, hauntedInitialInanimates, hauntedEnemySpawners, 
                1168, 190, Math.PI/2, 1697, 3282, Math.PI/2);
        //Start mid-bottom
        return hauntedRealm;
    }
    
    /**
     * Static method that constructs and returns a new jungle Realm.
     * @return a new Realm instance that represents the jungle level
     */
    public static Realm newJungleRealm() {
    	/*
         * Sets up an array list of all Block objects, including Portal objects, for Hayes in the Realm
         */
        ArrayList<Block> jungleBlocks = new ArrayList<Block>();
        //walls--------------------------
        jungleBlocks.add(new Block(4638, 203, 328, 838));
        jungleBlocks.add(new Block(66,1621,203,622));
        jungleBlocks.add(new Block(268,1045,305,579));
        jungleBlocks.add(new Block(852,1330,743,295));
        jungleBlocks.add(new Block(1154,1625,438,960));
        jungleBlocks.add(new Block(264,2246,885,84));
        jungleBlocks.add(new Block(571,206,1020,839));
        jungleBlocks.add(new Block(1589,137,3047,67));
        jungleBlocks.add(new Block(5616,207,326,834));
        jungleBlocks.add(new Block(4965,1042,132,285));
        jungleBlocks.add(new Block(4638,1326,331,1258));
        jungleBlocks.add(new Block(1593,2586,3047,111));
        jungleBlocks.add(new Block(2807,487,13,1785));
        jungleBlocks.add(new Block(3088,1544,13,1043));
        jungleBlocks.add(new Block(3087,1532,986,13));
        jungleBlocks.add(new Block(4070,766,12,777));
        jungleBlocks.add(new Block(4353,490,13,1814));
        jungleBlocks.add(new Block(1590,476,2775,13));
        //trees--------------------------
        jungleBlocks.add(new Block(2183,2257,211,251));
        jungleBlocks.add(new Block(1659,1762,193,239));
        jungleBlocks.add(new Block(2324,1423,208,230));
        jungleBlocks.add(new Block(1900,809,193,209));
        jungleBlocks.add(new Block(3222,595,171,175));
        jungleBlocks.add(new Block(3326,1233,148,185));
        jungleBlocks.add(new Block(3803,1035,159,203));
        jungleBlocks.add(new Block(3814,2265,168,185));
        jungleBlocks.add(new Block(3330,2115,175,190));
        jungleBlocks.add(new Block(3679,1662,180,181));
        //TODO electric barrier??
         
        /*
         * Sets up an array list of all Block objects, including Portal objects, for enemies in the Realm
         */
        ArrayList<Block> enemyBlocks = new ArrayList<>();
        enemyBlocks.addAll(jungleBlocks);
        enemyBlocks.add(new Block(571,1426,282,55));
        enemyBlocks.add(new Block(2815,1893,279,99));
        enemyBlocks.add(new Block(4077,1153,283,119));
        enemyBlocks.add(new Block(4360,2110,278,109));
          
        /*
         * Sets up an array list of all Enemy objects present when the Realm starts
         * Also sets up a baby for the BossNinjaReal to drop when killed
         */
        ArrayList<Enemy> jungleInitialEnemies = new ArrayList<Enemy>();
        jungleInitialEnemies.add(new Frog(1708,2261,5616,2936,enemyBlocks));
        jungleInitialEnemies.add(new Frog(2187,577,5616,2936,enemyBlocks));
        jungleInitialEnemies.add(new Frog(2967,721,5616,2936,enemyBlocks));
        jungleInitialEnemies.add(new Frog(3726,1976,5616,2936,enemyBlocks));
        jungleInitialEnemies.add(new Ninja(2188,1872,5616,2936,enemyBlocks));
        jungleInitialEnemies.add(new Ninja(3472,924,5616,2936,enemyBlocks));
        jungleInitialEnemies.add(new BossNinjaReal(408,1958,5616,2936,enemyBlocks,
              new Rectangle2D.Double(270,1622,883,619),new Baby(408,1958,5616,2936)));
         
        /*
         * Sets up an array list of all Inanimate objects present when the Realm starts
         */
        ArrayList<InanimateObject> jungleInitialInanimates = new ArrayList<InanimateObject>();
        //electrons--------------
        jungleInitialInanimates.add(new Electron(877,1269,5616,2936));
        jungleInitialInanimates.add(new Electron(1401,1076,5616,2936));
        jungleInitialInanimates.add(new Electron(2850,1585,5616,2936));
        jungleInitialInanimates.add(new Electron(3033,2145,5616,2936));
        jungleInitialInanimates.add(new Electron(4123,1405,5616,2936));
        jungleInitialInanimates.add(new Electron(4244,381,5616,2936));
        jungleInitialInanimates.add(new Electron(4291,801,5616,2936));
        //barrier----------------
        Barrier barrier = new Barrier(BarrierType.H281, 571, 1472, 5616, 2936, true);
        jungleInitialInanimates.add(barrier);
        //lever------------------
        jungleInitialInanimates.add(new Lever(1664, 279, 5616, 2936, barrier));
          
        /*
         * Sets up an array list of all EnemySpawner objects owned by the Realm
         */
        ArrayList<EnemySpawner> jungleEnemySpawners = new ArrayList<EnemySpawner>();
        Frog template = new Frog(0,0,5616,2936,enemyBlocks);
        jungleEnemySpawners.add(new EnemySpawner(template,60,true,new Point(3726,1976)));
        jungleEnemySpawners.add(new EnemySpawner(template,90,true,new Point(2967,721)));
        jungleEnemySpawners.add(new EnemySpawner(template,180,true,new Point(2187,577)));
        jungleEnemySpawners.add(new EnemySpawner(template,150,true,new Point(1708,2261)));
          
        /*
         * Constructs, returns a jungle Realm
         */
        Realm jungleRealm = new Realm("realms/jungle.png", 5616, 2936, jungleBlocks, 
        		jungleInitialEnemies, jungleInitialInanimates, jungleEnemySpawners, 
        		4831, 1118, 0, 3540, 1853, 0);
        return jungleRealm;
    }
    
    
    public static Realm newRoadRealm() {
    	
    	final int realmWidth = 1000, realmHeight = 2796;
    	
        /*
         * Sets up an array list of all Block objects, including Portal objects, in the Realm.
         * In the road Realm, there are no Blocks except the Portal to return.
         */
        ArrayList<Block> roadBlocks = new ArrayList<Block>();
        roadBlocks.add(new Portal(53, 42, 71, 41, ID_CENTRAL));
         
        /*
         * Sets up an array list of all Enemy objects present when the Realm starts
         */
        ArrayList<Enemy> roadInitialEnemies = new ArrayList<Enemy>();
        roadInitialEnemies.add(new SlowCar(1000,305,1000,2796));
        roadInitialEnemies.add(new SlowCar(1000,487,1000,2796));
        roadInitialEnemies.add(new Car(1000,908,1000,2796));
        roadInitialEnemies.add(new Car(1000,1088,1000,2796));
        roadInitialEnemies.add(new Car(1000,1271,1000,2796));
        roadInitialEnemies.add(new FastCar(1000,1690,1000,2796));
        roadInitialEnemies.add(new FastCar(1000,1871,1000,2796));
        roadInitialEnemies.add(new FastCar(1000,2053,1000,2796));
        roadInitialEnemies.add(new FastCar(1000,2237,1000,2796));
        
        ArrayList<InanimateObject> roadInitialInanimates = new ArrayList<InanimateObject>();
        // Baby
        Baby bby = new Baby(810, 100, realmWidth, realmHeight);
        roadInitialInanimates.add(bby);
        // Barriers surrounding Baby
        ArrayList<Barrier> barriers = new ArrayList<>();
        barriers.add(new Barrier(BarrierType.V281, 719, 0, realmWidth, realmHeight, true));
        barriers.add(new Barrier(BarrierType.H281, 719, 281, realmWidth, realmHeight, true));
        roadInitialInanimates.addAll(barriers);
        // Lever to control Barriers
        roadInitialInanimates.add(new Lever(450, 2596, realmWidth, realmHeight, barriers));
        // Replenishing Electrons at top
        roadInitialInanimates.add(new Electron(200, 2590, realmWidth, realmHeight));
        roadInitialInanimates.add(new Electron(800 - Electron.IMAGE.getWidth(), 2590, realmWidth, realmHeight));
        roadInitialInanimates.add(new Electron(330, 2721, realmWidth, realmHeight));
        roadInitialInanimates.add(new Electron(670 - Electron.IMAGE.getWidth(), 2721, realmWidth, realmHeight));
        // Replenishing Electrons by Baby
        roadInitialInanimates.add(new Electron(758, 59, realmWidth, realmHeight));
        roadInitialInanimates.add(new Electron(758, 111, realmWidth, realmHeight));
        roadInitialInanimates.add(new Electron(758, 163, realmWidth, realmHeight));
        roadInitialInanimates.add(new Electron(758, 215, realmWidth, realmHeight));
        roadInitialInanimates.add(new Electron(810, 215, realmWidth, realmHeight));
        roadInitialInanimates.add(new Electron(862, 215, realmWidth, realmHeight));
        roadInitialInanimates.add(new Electron(914, 215, realmWidth, realmHeight));
        
        /*
         * Sets up an array list of all EnemySpawner objects owned by the Realm
         */
        // KEEP RATIOS
        ArrayList<EnemySpawner> roadEnemySpawners = new ArrayList<EnemySpawner>();
        Car template;
        // Add SlowCar spawners
        template = new SlowCar(0, 0, realmWidth, realmHeight);
        roadEnemySpawners.add(new EnemySpawner(template, 3, true, new Point(1000, 305)));
        roadEnemySpawners.add(new EnemySpawner(template, 4, true, new Point(1000, 487)));
        // Add normal Car spawners
        template = new Car(0, 0, realmWidth, realmHeight);
        roadEnemySpawners.add(new EnemySpawner(template, 3.5, true, new Point(1000, 908)));
        roadEnemySpawners.add(new EnemySpawner(template, 2.5, true, new Point(1000, 1088)));
        roadEnemySpawners.add(new EnemySpawner(template, 3, true, new Point(1000, 1271)));
        // Add FastCar spawners
        template = new FastCar(0, 0, realmWidth, realmHeight);
        roadEnemySpawners.add(new EnemySpawner(template, 4.5, true, new Point(1000, 1690)));
        roadEnemySpawners.add(new EnemySpawner(template, 2, true, new Point(1000, 1871)));
        roadEnemySpawners.add(new EnemySpawner(template, 3, true, new Point(1000, 2053)));
        roadEnemySpawners.add(new EnemySpawner(template, 2.5, true, new Point(1000, 2237)));
        
        HayesEnvironment roadEnv = new HayesEnvironment(HayesEnvironment.DEFAULT);
        roadEnv.maxSpeed += 30;
         
        /*
         * Constructs, returns a road Realm
         */
        Realm roadRealm = new Realm("realms/road.png", realmWidth, realmHeight, roadEnv, roadBlocks, 
        		roadInitialEnemies, roadInitialInanimates, roadEnemySpawners, null, 456, 30, 0, 819, 3298, 0);
        return roadRealm;
    }
    
    
    public static Realm newCastleRealm() {
    	
    	final int realmWidth = 2745, realmHeight = 2244;
    	
	    /*
	     * Sets up an array list of all Block objects, including Portal objects, in the Realm
	     */
	    ArrayList<Block> castleBlocks = new ArrayList<Block>();
	    castleBlocks.add(new Block(0, 0, realmWidth, 27));			// bottom
	    castleBlocks.add(new Block(0, 0, 27, 1695));				// left
	    castleBlocks.add(new Block(realmWidth - 27, 0, 27, 1695));	// right
	    castleBlocks.add(new Block(0, 1695, 1208, 32));
	    castleBlocks.add(new Block(realmWidth - 1208, 1695, 1208, 32));
	    castleBlocks.add(new Block(1015, 1727, 195, 118));
	    castleBlocks.add(new Block(1488, 1727, 195, 118));
	    castleBlocks.add(new Block(974, 1842, 47, 402));
	    castleBlocks.add(new Block(1679, 1842, 47, 402));
	    castleBlocks.add(new Block(1022, 2222, 656, 22));			// ceiling
	    // Portal to Nursery at top
	    castleBlocks.add(new Portal(1267, 1978, 167, 167, ID_NURSERY));
	    // Portal back to Central at bottom
	    castleBlocks.add(new Portal(212, 134, 60, 50, ID_CENTRAL));
	     
	    /*
	     * Sets up an array list of all Enemy objects present when the Realm starts
	     */
	    ArrayList<Enemy> castleInitialEnemies = new ArrayList<Enemy>();
	    castleInitialEnemies.add(new MonsterKevin(1002, 925, realmWidth, realmHeight, castleBlocks));
	    castleInitialEnemies.add(new MonsterKevin(1390, 1070, realmWidth, realmHeight, castleBlocks));
	    castleInitialEnemies.add(new MonsterKevin(1522, 825, realmWidth, realmHeight, castleBlocks));
	    castleInitialEnemies.add(new MonsterKevin(1263, 667, realmWidth, realmHeight, castleBlocks));
	    castleInitialEnemies.add(new MonsterKevin(1300, 1300, realmWidth, realmHeight, castleBlocks));
	    castleInitialEnemies.add(new MonsterKevin(1268, 1700, realmWidth, realmHeight, castleBlocks));
	    
	    ArrayList<InanimateObject> castleInitialInanimates = new ArrayList<>();
	    // Baby
	    castleInitialInanimates.add(new Baby(1307, 919, realmWidth, realmHeight));
	    
	    /*
	     * Sets up an array list of all EnemySpawner objects owned by the Realm
	     */
	    ArrayList<EnemySpawner> castleEnemySpawners = new ArrayList<EnemySpawner>();
	     
	    /*
	     * Constructs, returns a castle Realm
	     */
	    Realm castleRealm = new Realm("realms/castle.png", realmWidth, realmHeight, castleBlocks, 
	    		castleInitialEnemies, castleInitialInanimates, castleEnemySpawners, 
	    		347, 107, 0, 3385, 419, 0);
	    return castleRealm;
	}


	public static Realm newNurseryRealm() {
    	final int realmWidth = 1490, realmHeight = 1614;
    	
    	ArrayList<Block> nurseryBlocks = new ArrayList<>();
    	nurseryBlocks.add(new Block(0, 1201, 433, 413));
    	nurseryBlocks.add(new Block(1059, 1201, 432, 412));
    	// Portal back to Central
    	nurseryBlocks.add(new Portal(41, 1575, 220, 86, ID_CENTRAL));
    	
    	ArrayList<Enemy> nurseryInitialEnemies = new ArrayList<>();
    	
    	ArrayList<InanimateObject> nurseryInitialInanimates = new ArrayList<>();
    	// Real and fake Baby objects: which one is real?? >:)
    	nurseryInitialInanimates.add(new FakeBaby(182, 949, realmWidth, realmHeight));
    	nurseryInitialInanimates.add(new FakeBaby(504, 949, realmWidth, realmHeight));
    	nurseryInitialInanimates.add(new FakeBaby(824, 949, realmWidth, realmHeight));
    	nurseryInitialInanimates.add(new FakeBaby(1143, 949, realmWidth, realmHeight));
    	nurseryInitialInanimates.add(new FakeBaby(182, 698, realmWidth, realmHeight));
    	nurseryInitialInanimates.add(new FakeBaby(504, 698, realmWidth, realmHeight));
    	nurseryInitialInanimates.add(new FakeBaby(824, 698, realmWidth, realmHeight));
    	nurseryInitialInanimates.add(new FakeBaby(1143, 698, realmWidth, realmHeight));
    	nurseryInitialInanimates.add(new FakeBaby(182, 449, realmWidth, realmHeight));
    	nurseryInitialInanimates.add(new FakeBaby(504, 449, realmWidth, realmHeight));
    	nurseryInitialInanimates.add(new Baby(824, 449, realmWidth, realmHeight));			// <== the real one!!
    	nurseryInitialInanimates.add(new FakeBaby(1143, 449, realmWidth, realmHeight));
    	nurseryInitialInanimates.add(new FakeBaby(182, 200, realmWidth, realmHeight));
    	nurseryInitialInanimates.add(new FakeBaby(504, 200, realmWidth, realmHeight));
    	nurseryInitialInanimates.add(new FakeBaby(824, 200, realmWidth, realmHeight));
    	nurseryInitialInanimates.add(new FakeBaby(1143, 200, realmWidth, realmHeight));
    	
    	ArrayList<EnemySpawner> nurseryEnemySpawners = new ArrayList<>();
    	
    	Realm nurseryRealm = new Realm("realms/nursery.png", realmWidth, realmHeight, nurseryBlocks, 
    			nurseryInitialEnemies, nurseryInitialInanimates, nurseryEnemySpawners, 
    			209, 1277, 0, 3622, 3250, 0);
    	return nurseryRealm;
    }
    
}
