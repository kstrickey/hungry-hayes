package window.gameEvents;

import window.GameEngine;

public class MousePressedOrReleasedEvent extends GameEvent {
	
	public MousePressedOrReleasedEvent(boolean pressed) {
		this.pressed = pressed;
	}
	
	@Override
	public void execute(GameEngine exploringGameEngine) {
		exploringGameEngine.getMouse().setPressed(pressed);
//		System.out.println("MousePorREvent: " + exploringGameEngine.getMouse().x() + "," + exploringGameEngine.getMouse().y());	// to aid finding coordinates
	}
	
	private final boolean pressed;
	
}
