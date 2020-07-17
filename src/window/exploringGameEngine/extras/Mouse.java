package window.exploringGameEngine.extras;

//import java.awt.image.BufferedImage;
import java.awt.Component;
import java.awt.Point;

import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.Paintable;
import window.screen.Screen;

/**
 * A special SimObject that is controlled by the user's mouse and is represented by a cursor.
 * This object implements Paintable but does not inherit from SimObject.
 *
 */
public class Mouse implements Paintable {
	
	public Mouse(Screen screen) {
		this.screen = screen;
		
//		image = PaintMachine.getImageFromName("target-red.png");
//		width = 50;
//		height = 50;
		point = new Point();		
		pressed = false;
	}
	
	@Override
	public void paint(PaintMachine paintMachine) {
		/*	For now, we are not painting the Mouse, unfortunately. Maybe later.
		if (!pressed) {
			paintMachine.paintImage(image, x() - width/2.0, y() - height/2.0, width, height);
		} else {
			paintMachine.paintImage(image, x() - width/3.0, y() - height/3.0, width*2.0/3.0, height*2.0/3.0);
		}
		*/
	}
	
	public boolean isPressed() {
		return pressed;
	}
	
	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
	
	public void setPosition(int x, int y) {
		point.x = x;
		point.y = screen.height() - y;
	}
	
	public int x() {
		return point.x - componentLocation.x;
	}
	
	public int y() {
		return point.y + componentLocation.y;
	}
	
	public static void refreshComponentLocation(Component c) {
		componentLocation = c.getLocationOnScreen();
	}
	
	private Screen screen;
	
//	private BufferedImage image;
//	private int width;
//	private int height;
	private Point point;	
	private boolean pressed;
	
	private static Point componentLocation = new Point();			// subtracted from point when returned
	
}
