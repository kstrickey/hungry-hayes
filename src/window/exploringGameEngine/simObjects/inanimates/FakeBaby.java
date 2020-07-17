package window.exploringGameEngine.simObjects.inanimates;

import java.awt.image.BufferedImage;

import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.gameEvents.RemoveInanimateEvent;
import window.exploringGameEngine.simObjects.hayes.Hayes;
import window.gameEvents.AddMessageImageEvent;

public class FakeBaby extends InanimateObject {
	
	public static final BufferedImage IMAGE = Baby.IMAGE;
	public static final BufferedImage WRONG_ANSWER_MESSAGE = PaintMachine.getImageFromName("textbox/wrong baby.png");
	
	public FakeBaby(double x, double y, int realmWidth, int realmHeight) {
		super(IMAGE, x, y, IMAGE.getWidth(), IMAGE.getHeight(), realmWidth, realmHeight);
	}
	
	@Override
	public void tick(double timeElapsed, ExploringGameEventQueue gameEventQueue, Hayes hayes) {
		if (touching(hayes)) {
			hayes.hurtAlways(3);		// damage hayes for a wrong guess
			gameEventQueue.addEvent(new RemoveInanimateEvent(this));		// remove self
			gameEventQueue.addEvent(new AddMessageImageEvent(WRONG_ANSWER_MESSAGE));
		}
	}
	
}
