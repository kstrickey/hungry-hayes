package window.exploringGameEngine.simObjects.hayes;
 
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import resources.classes.EuclidianVector;
import resources.classes.SquareSide;
import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.extras.Mouse;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.gameEvents.ShiftScreenEvent;
import window.exploringGameEngine.simObjects.SimObject;
import window.exploringGameEngine.simObjects.background.Block;
import window.exploringGameEngine.simObjects.enemies.Enemy;
import window.gameEvents.AddMessageImageEvent;
import window.gameEvents.SwitchEnginesUsingTransitionEvent;
import window.screen.Screen;
import window.transitionGameEngine.TransitionGameEngine;
 
public class Hayes extends SimObject {
	
//	/**
//	 * An array of the faces Hayes arbitrarily switches between.
//	 */
//    
//    public static final BufferedImage[] FACES = { 
//    	PaintMachine.getImageFromName("hayes/hayes dripping mouth.png"),
//    	PaintMachine.getImageFromName("hayes/hayes grimacing mouth.png"),
//    	PaintMachine.getImageFromName("hayes/hayes open mouth.png"),
//    };
    
    public static final BufferedImage FACE = PaintMachine.getImageFromName("hayes/hayes face.png");
    
    public static final double STD_WIDTH = FACE.getWidth();
    public static final double STD_HEIGHT = FACE.getHeight();
    
    public static final BufferedImage diedMessage = PaintMachine.getImageFromName("textbox/died.png");
    
    /**
     * Initialize Hayes with default HayesEnvironment.
     */
    public Hayes(int initialX, int initialY, int realmWidth, int realmHeight, ArrayList<Block> blocks) {
        this(initialX, initialY, realmWidth, realmHeight, blocks, HayesEnvironment.DEFAULT);
    }
    
    public Hayes(int initialX, int initialY, int realmWidth, int realmHeight, 
            ArrayList<Block> blocks, HayesEnvironment env) {
        super(realmWidth, realmHeight, false, blocks);      
        setRandomImage();
        setWidth(STD_WIDTH);
        setHeight(STD_HEIGHT);
        angle = Math.PI / 2;
        this.hayesEnvironment = env;
        healthBar = new HealthBar();
    }
    
    @Override
    public void tick(double timeElapsed, ExploringGameEventQueue gameEventQueue) {        
        // Check if Hayes is out of health...
        //					... if he is, good-bye.
        if (healthBar.isEmpty()) {
        	dyingTick(timeElapsed, gameEventQueue);
        } else {            
            tickInvincibility(timeElapsed);
            tickFists(timeElapsed);
     
            /*
             * If not stunned, get user input. Adjust velocity and perform 
             * other actions accordingly.
             */
            if (! isStunned()) {
                // Turning
                if (leftArrowDown) {
                    angle += hayesEnvironment.angularV * timeElapsed;
                } else if (rightArrowDown) {
                    angle -= hayesEnvironment.angularV * timeElapsed;
                }
                // Forward and backward
                if (topArrowDown) {
                    getVelocity().addVector(
                            new EuclidianVector(hayesEnvironment.acceleration * timeElapsed, angle));
                 
                } else if (bottomArrowDown) {
                    getVelocity().addVector(
                            new EuclidianVector(-hayesEnvironment.acceleration * timeElapsed, angle));
                }
                // Punching
                if (punchKeyDown) {
                    attemptPunch();
                }
            }
             
            // Feel effects of friction
            if (getSpeed() > 0) {
                setSpeed(getSpeed() - hayesEnvironment.friction * timeElapsed);
            }
            if (getSpeed() < 0) {
                setSpeed(0);
            }
             
            // Ensure that Hayes still adheres to MAX_SPEED
            if (getSpeed() > hayesEnvironment.maxSpeed * timeElapsed && !isStunned()) {
                setSpeed(hayesEnvironment.maxSpeed * timeElapsed);
            }
             
            // Fire event to [attempt to] move screen if within necessary bounds
            if (getEdgeCoordinate(SquareSide.RIGHT) > screen.x() + screen.width() - Screen.HAYES_WIDTH_EDGE_TOLERANCE) {
                gameEventQueue.addEvent(new ShiftScreenEvent(SquareSide.RIGHT));
            } else if (getEdgeCoordinate(SquareSide.LEFT) < screen.x() + Screen.HAYES_WIDTH_EDGE_TOLERANCE) {
                gameEventQueue.addEvent(new ShiftScreenEvent(SquareSide.LEFT));
            }
            if (getEdgeCoordinate(SquareSide.BOTTOM) < screen.y() + Screen.HAYES_HEIGHT_EDGE_TOLERANCE) {
                gameEventQueue.addEvent(new ShiftScreenEvent(SquareSide.BOTTOM));
            } else if (getEdgeCoordinate(SquareSide.TOP) > screen.y() + screen.height() - Screen.HAYES_HEIGHT_EDGE_TOLERANCE) {
                gameEventQueue.addEvent(new ShiftScreenEvent(SquareSide.TOP));
            }

            super.tick(timeElapsed, gameEventQueue);
            
            if (Math.random() < timeElapsed) {
            	setRandomImage();
            }
        }
    }
     
    /**
     * Returns the angle in which Hayes faces.
     */
    public double getAngle () {
        return angle;
    }
    
    public HayesEnvironment getHayesEnvironment() {
    	return hayesEnvironment;
    }
     
    /**
     * If Hayes is invisible because of invincibility, nothing happens.
     * Otherwise, Paint hayes.
     */
    @Override
    public void paint(PaintMachine paintMachine) {
        if (isVisible()) {
            super.paint(paintMachine);
            paintFists(paintMachine);
        }
        
        if (healthBarVisible) {
        	healthBar.paint(paintMachine);
        }
    }
     
    @Override
    public void hitRealmBoundary(ExploringGameEventQueue gameEventQueue) {
        stayFullyInsideRealm();
    }
     
    @Override
    public void removeSelfFromRealm(ExploringGameEventQueue gameEventQueue) { } // will never be called
     
    public void setScreen(Screen screen) {
        this.screen = screen;
    }
    
    public void setMouse(Mouse mouse) {		// TODO .. if Mouse has a use
//        this.mouse = mouse;
    }
    
    public void setHayesEnvironment(HayesEnvironment hayesEnvironment) {
    	this.hayesEnvironment = hayesEnvironment;
    }
    
    public void setArrowKey(SquareSide direction, boolean pressed) {
        switch (direction) {
        case RIGHT:
            rightArrowDown = pressed;
            break;
        case BOTTOM:
            bottomArrowDown = pressed;
            break;
        case LEFT:
            leftArrowDown = pressed;
            break;
        case TOP:
            topArrowDown = pressed;
            break;
        }
    }
    
    /**
     * Called by a GameEvent when an Enemy hits the Hayes.
     * @param enemy
     */
    public void hitByEnemy(Enemy enemy) {
    	if (!isInvincible()) {
	    	// Decrease health
	    	healthBar.addHealth(-enemy.getHayesHealthDamageUponContact());
	    	
	    	stunAndRecoilFrom(enemy);
    	}
    }
    
    /**
     * Starts the stun sequence for Hayes and starts his recoil from the specified SimObject.
     * @param simObject
     */
    public void stunAndRecoilFrom(SimObject simObject) {
    	// Stun Hayes
    	invincibilitySecs = 0;		// starts invincibi3lity sequence
    	
    	// Knock back
    	double dx = center().x - simObject.center().x;
    	double dy = center().y - simObject.center().y;
    	getVelocity().setUsingRectangularDimensions(dx, dy);
    	setSpeed(hayesEnvironment.recoil * .005);		// TODO .005 represents timeElapsed; how to get it?
    }
    
    /**
     * Hurt Hayes for the given amount of damage.
     * This damage is taken through invincibility and does not cause Hayes to recoil.
     * Losing breath and touching an electric barrier deal this kind of damage.
     * @param damage
     */
    public void hurtAlways(double damage) {
        healthBar.addHealth(-damage);        
    }
    
    /**
     * Heal Hayes for the given amount of health.
     */
    public void heal(double amount) {
        healthBar.addHealth(amount);
    }
    
    /**
     * Sets position and direction as given and speed as 0.
     * Sets all arrow key down boolean variables to false.
     * @param x
     * @param y
     * @param direction
     */
    public void reset(double x, double y, double direction) {
        setX(x);
        setY(y);
        setSpeed(0);
        setDirection(0);
        rightArrowDown = bottomArrowDown = leftArrowDown = topArrowDown = false;
    }
    
    /**
     * Randomly sets a BufferedImage from FACES to the image of Hayes.
     */
    public void setRandomImage() {
    	//setImage(FACES[(int) (Math.random() * FACES.length)]);
        setImage(FACE);
    }
    
    private Screen screen;
    
    private HayesEnvironment hayesEnvironment;
    private boolean rightArrowDown;
    private boolean bottomArrowDown;
    private boolean leftArrowDown;
    private boolean topArrowDown;
//    private Mouse mouse;
    private double angle;
    
    private HealthBar healthBar;
    
    // Dying sequence ===============================================
    /**
     * The flicker time of the health bar.
     */
    public static final double HEALTH_BAR_FLASH_TIME = .25;
    /**
     * The amount of time the health bar flashes for.
     */
    public static final double TOTAL_FLASH_TIME = 1.0;
    
    public void dyingTick(double timeElapsed, ExploringGameEventQueue gameEventQueue) {
    	flashTimePassed += timeElapsed;
    	if ((int) (flashTimePassed / HEALTH_BAR_FLASH_TIME) % 2 == 0) {
    		healthBarVisible = !healthBarVisible;
    	}
    	if (flashTimePassed > TOTAL_FLASH_TIME) {
    		if (!displayedMessageYet) {
    			gameEventQueue.addEvent(new AddMessageImageEvent(diedMessage));
    			displayedMessageYet = true;
    		} else {
	    		healthBar = new HealthBar();									// reset health
	    		healthBarVisible = true;
	    		gameEventQueue.addEvent(new SwitchEnginesUsingTransitionEvent(	// transport back to homeGameEngine
	    				null, TransitionGameEngine.DEFAULT_TRANSITION_TIME));
    		}
    	}
    }
    
    private double flashTimePassed = 0;
    private boolean healthBarVisible = true;
    
    private boolean displayedMessageYet = false;
    
    // Blocked =======================================================
    
    public void hasHitWall(SquareSide simObjectSide) {
    	stopInDirection(simObjectSide);
    }
    
    // Invincibility =================================================
    public static final double
    /**
     * The total number of seconds that Hayes is invincible after he gets
     * hurt.
     */
    INVINCIBILITY_TOTAL_SECS = 1.5,
    /**
     * The total number of seconds that Hayes is stunned, i.e. unresponsive to
     * user input, after he gets hurt.
     */
    STUNNED_SECS = .5,
    /**
     * The value of invincibilitySecs when Hayes is not invincible.
     */
    NOT_INVINCIBLE = -1,
    /**
     * The time spent off or on while Hayes is flickering after being hit.
     */
    FLICKER_TIME = .3;
    /**
     * The number of seconds since Hayes was first made invincible. Hayes 
     * begins vulnerable (not invincible).
     */
    private double invincibilitySecs = NOT_INVINCIBLE;
    /**
     * If Hayes is invincible, increment the count of invincible seconds. If 
     * the number of seconds that Hayes has been invincible is greater than 
     * the total intended time, make Hayes vulnerable.
     */
    private void tickInvincibility (double timeElapsed) {
        if (isInvincible()) {
            invincibilitySecs += timeElapsed;
            if (invincibilitySecs > INVINCIBILITY_TOTAL_SECS) {
                invincibilitySecs = NOT_INVINCIBLE;
            }
        }
    }
    /**
     * Return whether Hayes is invincible.
     */
    public boolean isInvincible () {
        return invincibilitySecs != NOT_INVINCIBLE;
    }
    /**
     * Return whether Hayes is stunned.
     */
    public boolean isStunned () {
        return isInvincible() && invincibilitySecs <= STUNNED_SECS;
    }
    /**
     * Hayes flickers on and off when invincible. Returns whether
     * he is currently visible.
     */
    public boolean isVisible () {
        if (isInvincible()) {
            //Alternate .15s on, .15s off
            return ((int) (invincibilitySecs / FLICKER_TIME)) % 2 == 0;
        }
        return true;
    }
 
    // Fists =========================================================
    // Input ---------------------------
    /**
     * Whether the punch key (Space) is currently pressed down.
     * Begins false.
     */
    private boolean punchKeyDown = false;
    /**
     * Set whether the punch key is currently pressed.
     */
    public void setPunchKey (boolean punchKeyDown) {
        this.punchKeyDown = punchKeyDown;
    }
     
    // Fist timing ---------------------
    public static final double
    /**
     * After he first punches, the time it takes for Hayes' 
     * punching fist to reach a fully extended position. Only during this time
     * can Hayes deal damage
     */
    FIST_CLIMAX_SECS = .02,
    /**
     * The number of seconds it takes for Hayes' fists to return to their
     * original positions after he first punches. Only then can he punch 
     * again--so this time also dictates how frequently Hayes can punch.
     */
    FIST_END_SECS = .5,
    /**
     * The value of fistSecs when Hayes is not punching, i.e. both fists
     * are in original position.
     */
    NOT_PUNCHING = -1;
    /**
     * A running count of how many frames since Hayes last punched.
     */
    private double fistSecs = NOT_PUNCHING;
    private boolean isLeft = true;
    /**
     * Return whether Hayes has at least one fist not in original position.
     */
    private boolean isPunching () {
        return fistSecs != NOT_PUNCHING;
    }
    /**
     * Return whether Hayes is punching with his left hand.
     */
    private boolean isLeft () {
        return isLeft;
    }
    /**
     * If Hayes is punching, increment the time since the most recent punch.
     * If this is greater than FIST_END_SECS, make Hayes no longer punching.
     */
    private void tickFists (double timeElapsed) {
        if (isPunching()) { 
            fistSecs += timeElapsed;
            if (fistSecs > FIST_END_SECS) {
                fistSecs = NOT_PUNCHING;
            }
        }    
    }
    /**
     * If Hayes is currently punching, return the fraction of the way that 
     * the punching fist is between original position and fully extended.
     * The value returned is in [0, 1]. If Hayes is not punching, both fists
     * are in original position, so return 0.
     */
    private double getFistFraction()
    {
        if (isPunching()) {
            if (fistSecs <= FIST_CLIMAX_SECS) {
                return fistSecs / FIST_CLIMAX_SECS;
            } else { //fistSecs between climax and end secs
                return (FIST_END_SECS - fistSecs)
                    / (FIST_END_SECS - FIST_CLIMAX_SECS);
            }
        }
        return 0;
    }
    /**
     * If Hayes is currently punching, he cannot begin another punch. Otherwise,
     * begin a new punch.
     */
    public void attemptPunch() {
        if (!isInvincible() && !isPunching()) {
            // Alternate hands
            isLeft = !isLeft;
            fistSecs = 0;
        }
    }
     
    // Hurting enemies -----------------    
    public static final double
    /**
     * The angle between the direction that Hayes faces and either of his
     * fists, in radians.
     */
    FIST_ORIGINAL_ANGLE = Math.PI / 3,
    /**
     * The distance between Hayes' center and one of his fists in original
     * position, in px.
     */
    FIST_ORIGINAL_RADIUS = 60,
    /**
     * The distance between Hayes' center and a fully extended fist, in px.
     */
    FIST_EXTENDED_RADIUS = 90;
 
    /**
     * Return the rectangle bounding the fist image located at the given
     * polar coordinates relative to Hayes' center.
     */
    private Rectangle getFistRectanglePolar(double radius, double angle) {
        int xlo = (int) Math.round(center().x + radius * Math.cos(getAngle() + angle))
                - FIST_IMAGE.getWidth() / 2;
        int ylo = (int) Math.round(center().y + radius * Math.sin(getAngle() + angle))
                - FIST_IMAGE.getHeight() / 2;
        return new Rectangle(xlo, ylo, FIST_IMAGE.getWidth(), FIST_IMAGE.getHeight());
    }
     
    /**
     * Get the rectangle which represents the bounds of the 
     * left fist.
     */
    private Rectangle getFistRectangle(boolean isLeft) {
        // If punching with this fist, interpolate with getfistfraction
        if (isPunching() && isLeft() == isLeft) {             
            double interpolateRadius = FIST_ORIGINAL_RADIUS + 
                    (FIST_EXTENDED_RADIUS - FIST_ORIGINAL_RADIUS) * getFistFraction();
            double interpolateAngle = FIST_ORIGINAL_ANGLE * (1 - getFistFraction());
             
            if (isLeft)
                return getFistRectanglePolar(interpolateRadius, interpolateAngle);
            else
                return getFistRectanglePolar(interpolateRadius, -interpolateAngle);
        }
        // Not punching with this fist. Return original position of this fist
         
        if (isLeft)
            return getFistRectanglePolar(FIST_ORIGINAL_RADIUS, FIST_ORIGINAL_ANGLE);
        else
            return getFistRectanglePolar(FIST_ORIGINAL_RADIUS, -FIST_ORIGINAL_ANGLE);
    }
     
    /**
     * Returns whether Hayes is currently punching and the hitbox of his extended 
     * fist is intersecting the hitbox of the specified Sim object
     */
    public boolean isPunchingSuccessfully(SimObject other) {
        if (isPunching()) {
            if (fistSecs <= FIST_CLIMAX_SECS) {
                return getFistRectangle(isLeft()).intersects(other.rectangleArea());
            }
        }
        return false;
    }
 
    // Fist rendering ------------------
    public static final BufferedImage FIST_IMAGE = 
            PaintMachine.getImageFromName("hayes/fist.png");
     
    /**
     * Render both fists on the specified paint machine.
     */
    private void paintFists(PaintMachine paintMachine) {
        Rectangle leftFist = getFistRectangle(true);
        paintMachine.paintImage(FIST_IMAGE, leftFist.x, leftFist.y, leftFist.width, leftFist.height);
        Rectangle rightFist = getFistRectangle(false);
        paintMachine.paintImage(FIST_IMAGE, rightFist.x, rightFist.y, rightFist.width, rightFist.height);
    }   
    
    
    // For interaction with Realms (e.g. with Babies) ===========================
    
    public boolean isPastOneRealm() {
    	return pastOneRealm;
    }
    
    /**
     * Should only be called by ExploringGameEngine.sayGoodbye().
     * Used for purposes of Hayes setting initial x, y, and direction when he returns to Central.
     * @param dyingRealmID
     */
    public void realmSaysGoodbye(int dyingRealmID) {
    	pastOneRealm = true;
    	previousRealmID = dyingRealmID;
    }
    
    public int getPreviousRealmID() {
    	return previousRealmID;
    }
    
    public void setRetrievedBaby(boolean retrievedBaby) {
    	this.retrievedBaby = retrievedBaby;
    }
    
    public boolean isRetrievedBaby() {
    	return retrievedBaby;
    }
    
    private boolean pastOneRealm = false;				// always initialized as false: 
    		// when true, Hayes will be set to a different x, y, and direction when he returns to Central
    		// this is set to true when the first GameEngine (Central) says good-bye
    
    private boolean retrievedBaby = false;		// checked when Hayes returns to home Realm
    private int previousRealmID;				// checked when retrievedBaby is true to know which Baby
    
}