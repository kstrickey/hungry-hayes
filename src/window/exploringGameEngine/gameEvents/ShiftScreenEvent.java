package window.exploringGameEngine.gameEvents;

import java.awt.MouseInfo;
import java.awt.Point;

import resources.classes.SquareSide;
import window.exploringGameEngine.ExploringGameEngine;
import window.gameEvents.SetMousePointEvent;
import window.screen.Screen;

public class ShiftScreenEvent extends ExploringGameEvent {
	
	public ShiftScreenEvent(SquareSide side) {
		this.side = side;
	}
	
	@Override
	public void exploringExecute(ExploringGameEngine exploringGameEngine) {
//		// Get initial position to track changes for mouse
//		double Xi = exploringGameEngine.getScreen().x();
//		double Yi = exploringGameEngine.getScreen().y();
		
		// Shift screen
		switch (side) {
			case RIGHT:
				exploringGameEngine.getScreen().setX(exploringGameEngine.getHayes().getEdgeCoordinate(SquareSide.RIGHT) + Screen.HAYES_WIDTH_EDGE_TOLERANCE - 
						exploringGameEngine.getScreen().width());
				break;
			case BOTTOM:
				exploringGameEngine.getScreen().setY(exploringGameEngine.getHayes().getEdgeCoordinate(SquareSide.BOTTOM) - Screen.HAYES_HEIGHT_EDGE_TOLERANCE);
				break;
			case LEFT:
				exploringGameEngine.getScreen().setX(exploringGameEngine.getHayes().getEdgeCoordinate(SquareSide.LEFT) - Screen.HAYES_WIDTH_EDGE_TOLERANCE);
				break;
			case TOP:
				exploringGameEngine.getScreen().setY(exploringGameEngine.getHayes().getEdgeCoordinate(SquareSide.TOP) + Screen.HAYES_HEIGHT_EDGE_TOLERANCE - 
						exploringGameEngine.getScreen().height());
				break;
		}
		
//		double dx = exploringGameEngine.getScreen().x() - Xi;
//		double dy = exploringGameEngine.getScreen().y() - Yi;
//		Point p = new Point((int) (exploringGameEngine.getMouse().x() + dx), (int) (exploringGameEngine.getMouse().y() + dy));
		Point p = MouseInfo.getPointerInfo().getLocation();	// TODO
//		p.x 
		SetMousePointEvent e = new SetMousePointEvent(p);
		e.execute(exploringGameEngine);
//		
//		// Shift mouse point as well
//		double dx = gameEngine.getScreen().x() - Xi;
//		double dy = gameEngine.getScreen().y() - Yi;
//		Mouse mouse = gameEngine.getMouse();
//		mouse.setPosition((int) (mouse.x() + dx), (int) (mouse.y() + dy));
	}
	
	private final SquareSide side;
	
}
