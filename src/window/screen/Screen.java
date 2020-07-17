package window.screen;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;

import window.exploringGameEngine.simObjects.hayes.Hayes;

public class Screen {
	
	/**
	 * Denotes the distance that Hayes can be with the edge of the Screen before the Screen shifts to accomodate (if possible).
	 * Must be positive but less than half the height and width of the screen (obviously).
	 */
	public static final double HAYES_WIDTH_EDGE_TOLERANCE = Hayes.STD_WIDTH * 3;
	
	public static final double HAYES_HEIGHT_EDGE_TOLERANCE = Hayes.STD_HEIGHT * 1.5;
	
	/**
	 * Constructs a ScreenSize object with the specified width and height and adds a ComponentListener to the window passed
	 * @param x : int
	 * @param y : int
	 * @param width : int
	 * @param height : int
	 * @param window : JFrame
	 */
	public Screen(int x, int y, int width, int height, JFrame window) {
		scrollWindow = new Rectangle2D.Double(x, y, width, height);
		
		window.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				scrollWindow.width = e.getComponent().getWidth();
				scrollWindow.height = e.getComponent().getHeight();
			}
		});
	}
	
	public void setRealmWidth(int realmWidth) {
		this.realmWidth = realmWidth;
	}
	
	public void setRealmHeight(int realmHeight) {
		this.realmHeight = realmHeight;
	}
	
	public double x() {
		return scrollWindow.x;
	}
	
	public double y() {
		return scrollWindow.y;
	}
	
	public int width() {
		return (int) scrollWindow.width;
	}
	
	public int height() {
		return (int) scrollWindow.height;
	}
	
	/**
	 * Checks if the parameter x is valid according to realmWidth and scrollWindow.width and, if valid, sets it.
	 * @param x
	 */
	public void setX(double x) {
		if (x < 0) {
			x = 0;
		} else if (x + scrollWindow.width > realmWidth) {
			x = realmWidth - scrollWindow.width;
		}
		scrollWindow.x = x;
	}
	
	/**
	 * Checks if the parameter y is valid according to realmHeight and scrollWindow.height and, if valid, sets it.
	 * @param y
	 */
	public void setY(double y) {
		if (y < 0) {
			y = 0;
		} else if (y + scrollWindow.height > realmHeight) {
			y = realmHeight - scrollWindow.height;
		}
		scrollWindow.y = y;
	}
	
	/**
	 * Returns true if the scrollWindow at all intersects the rectangle defined by the parameters; false if otherwise.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return true if the scrollWindow at all intersects the rectangle defined by the parameters; false if otherwise
	 */
	public boolean intersects(double x, double y, double width, double height) {
		return x + width > scrollWindow.x &&
				x < scrollWindow.x + scrollWindow.width &&
				y + height > scrollWindow.y &&
				y < scrollWindow.y + scrollWindow.height;
	}
	
	private final Rectangle2D.Double scrollWindow;
	
	private int realmWidth;
	private int realmHeight;
	
}
