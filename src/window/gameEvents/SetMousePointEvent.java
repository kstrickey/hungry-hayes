package window.gameEvents;

import java.awt.Point;

import window.GameEngine;

public class SetMousePointEvent extends GameEvent {
	
	public SetMousePointEvent(Point point) {
		this.point = point;
	}
	
	@Override
	public void execute(GameEngine gameEngine) {
		point.x += gameEngine.getScreen().x();
		point.y -= gameEngine.getScreen().y();
		gameEngine.getMouse().setPosition(point.x, point.y);
	}
	
	private final Point point;
	
}
