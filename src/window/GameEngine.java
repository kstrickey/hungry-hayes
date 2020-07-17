package window;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

import resources.classes.SquareSide;
import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.Paintable;
import window.exploringGameEngine.extras.Mouse;
import window.exploringGameEngine.simObjects.hayes.Hayes;
import window.gameEvents.GameEventQueue;
import window.screen.Screen;

/**
 * Each game engine implemented (each with a separate package) should extend this class.
 * Includes several abstract methods required by the WindowEngine as well as a public synchronizer Object.
 *
 */
public abstract class GameEngine implements Paintable {
	
	public GameEngine() {
//		frozen = false;
		currentMessageQueue = new LinkedList<BufferedImage>();
		switchFlag = false;
		nextGameEngine = null;
	}
	
	/**
	 * Called when the Engine needs to start. Should be called in the overriding method.
	 * @param screen
	 */
	public void start(Screen screen, Hayes hayes) {
		gameEventQueue().clear();
		this.screen = screen;
		mouse = new Mouse(screen);
		switchFlag = false;
	}
	
	/**
	 * Called when the game needs to switch to a different Engine.
	 * @return the Hayes object for the next GameEngine
	 */
	public abstract Hayes sayGoodbye();
	
	public abstract void tick(double timeElapsed);
	
	public Mouse getMouse() {
		return mouse;
	}
	
	public Screen getScreen() {
		return screen;
	}
	
//	public boolean isFrozen() {
//		return frozen;
//	}
//	
//	public void setFrozen(boolean frozen) {
//		this.frozen = frozen;
//	}
	
	/**
	 * Adds a message to the message queue.
	 * @param message : BufferedImage
	 */
	public void addMessage(BufferedImage message) {
		currentMessageQueue.add(message);
	}
	
	public BufferedImage peekCurrentMessageQueue() {
		return currentMessageQueue.peek();
	}
	
	/**
	 * Does not return a value (see peekCurrentMessageQueue for that functionality).
	 * Simply removes the head of the currentMessageQueue if it exists.
	 */
	public void pollCurrentMessageQueue() {
		currentMessageQueue.poll();
	}
	
	public boolean isSwitchFlag() {
		return switchFlag;
	}
	
	public GameEngine getNextGameEngine() {
		return nextGameEngine;
	}
	
	/**
	 * Method called when the GameEngine's time is up to indicate which the next GameEngine should be.
	 * @param nextGameEngine
	 */
	public void markForDestruction(GameEngine nextGameEngine) {
		this.nextGameEngine = nextGameEngine;
		switchFlag = true;
	}
	
	/**
	 * Method called similar to markForDestruction but indicates that the Hayes should return to the homeGameEngine in WindowEngine.
	 * This is indicated by switchFlag == true && nextGameEngine == null.
	 */
	public void markForDestructionAndReturnHome() {
		nextGameEngine = null;
		switchFlag = true;
	}
	
	public abstract GameEventQueue gameEventQueue();
	
	public abstract void paint(PaintMachine paintMachine);
	
	public abstract void setArrowKey(SquareSide direction, boolean pressed);
	
	/**
	 * Called when punch key is pressed or released.
	 * @param pressed
	 */
	public void setPunchKey(boolean pressed) { };
	
	private Screen screen;
	
	private Mouse mouse;
	
//	private boolean frozen;					// if frozen, the GameEngine will not tick
	private Queue<BufferedImage> currentMessageQueue;	// if not null, GameEngine is frozen
	
	private boolean switchFlag;				// when true, this GameEngine will end
	private GameEngine nextGameEngine;		// when switchFlag is true, this will be the next GameEngine to take over
	
}
