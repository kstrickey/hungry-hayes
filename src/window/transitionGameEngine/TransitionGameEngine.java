package window.transitionGameEngine;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import resources.classes.SquareSide;
import window.GameEngine;
import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.simObjects.hayes.Hayes;
import window.gameEvents.GameEventQueue;
import window.gameEvents.SwitchGameEngineEvent;
import window.screen.Screen;

/**
 * A GameEngine that runs little animations and things between actual GameEngine switches.
 * 
 */
public class TransitionGameEngine extends GameEngine {
	
	/**
	 * A convenience constant for programmers who enjoy standardness or indulge in laziness.
	 */
	public static final double DEFAULT_TRANSITION_TIME = 0.1;
	
	public TransitionGameEngine(GameEngine previousGameEngine, GameEngine nextGameEngine, double transitionTime) {
//		this.previousGameEngine = previousGameEngine;
		this.nextGameEngine = nextGameEngine;
		this.transitionTime = transitionTime;
		transitionGameEventQueue = new TransitionGameEventQueue(this);
		
//		previousImage = null;
		overlayingWhite = PaintMachine.getImageFromName("whitebox.png");
		
	}
	
	@Override
	public void start(Screen screen, Hayes hayes) {
		super.start(screen, hayes);
		currentTime = 0;
		this.hayes = hayes;
	}
	
	@Override
	public GameEventQueue gameEventQueue() {
		return transitionGameEventQueue;
	}
	
	@Override
	public void tick(double timeElapsed) {
		setOverlayingWhiteOpacity(currentTime / transitionTime);
		currentTime += timeElapsed;
		if (currentTime >= transitionTime) {
			transitionGameEventQueue.addEvent(new SwitchGameEngineEvent(nextGameEngine));
		}
		
		transitionGameEventQueue.executeAllEvents();
	}
	
	@Override
	public void paint(PaintMachine paintMachine) {
//		if (previousImage == null) {
//			// Load previousImage
//			previousImage = new BufferedImage(getScreen().width(), getScreen().height(), BufferedImage.TYPE_INT_ARGB);
//			Graphics2D g2d = previousImage.createGraphics();
//			paintMachine.setGraphics(g2d);
//			previousGameEngine.paint(paintMachine);		// paint current frame to image using paintMachine
//			g2d.dispose();
//			return;
//		}
		
//		paintMachine.paintImage(previousImage, 0, 0, getScreen().width(), getScreen().height());
		paintMachine.paintImage(overlayingWhite, 0, 0, getScreen().width(), getScreen().height());
	}
	
	@Override
	public Hayes sayGoodbye() {
		return hayes;
	}
	
	@Override
	public void setArrowKey(SquareSide direction, boolean pressed) { }
	
	private void setOverlayingWhiteOpacity(double alpha) {
		BufferedImage target = new BufferedImage(overlayingWhite.getWidth(null), overlayingWhite.getHeight(null), Transparency.TRANSLUCENT);
		Graphics2D tg = target.createGraphics();
		tg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) alpha));
		tg.drawImage(overlayingWhite, 0, 0, null);
		tg.dispose();
		overlayingWhite = target;
	}
	
//	private final GameEngine previousGameEngine;
	private final GameEngine nextGameEngine;
	private final double transitionTime;
	
	private final GameEventQueue transitionGameEventQueue;
	
	private double currentTime;
//	private BufferedImage previousImage;
	private BufferedImage overlayingWhite;
	
	private Hayes hayes;			// simply taken in start method and returned in sayGoodbye method
	
}
