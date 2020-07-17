package window.exploringGameEngine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import window.screen.Screen;

public class PaintMachine {
	
	public PaintMachine(Screen screen) {
		this.screen = screen;
	}
	
	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}
	
	/**
	 * Called by Paintable objects to paint an image to the screen at a specified location.
	 * (x, y) coordinates are based off the level's coordinate system, with origin (0, 0) at the bottom-left of the level.
	 * @param image : BufferedImage
	 * @param x : double, relative to overall level- not only the visible portion
	 * @param y : double, relative to overall level- not only the visible portion
	 * @param width : double
	 * @param height : double
	 */
	public void paintImage(BufferedImage image, double x, double y, double width, double height) {
		if (screen.intersects(x, y, width, height)) {
			// Adjust based on scrollWindow: x and y are now relative to screen
			x -= screen.x();
			y -= screen.y();		// note: origin is still bottom-left
			
			// Draw to graphics
			graphics.drawImage(image, (int) x, (int) (screen.height() - height - y), (int) width, (int) height, null);
		}
	}
	
	/**
	 * Paints image relative to Screen, not to Realm.
	 * NOTE: ORIGIN IS IN TOP-LEFT FOR THIS METHOD.
	 * Does not check if intersects or appears: just paints.
	 * @param image
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void paintImageRelativeToScreen(BufferedImage image, double x, double y, double width, double height) {
		graphics.drawImage(image, (int) x, (int) y, (int) width, (int) height, null);
	}
	
//	public void paintImageRotated(BufferedImage image, double x, double y, double width, double height, double radiansRotated) {
//		Graphics2D g2d = (Graphics2D) graphics.create();
//	}
	
	/**
	 * Reads in an image from a file name. Example call: 
	 * 		PaintMachine.getImageFromName("default background.png");
	 * @param imageName
	 * @return
	 */
	public static BufferedImage getImageFromName(String imageName) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("res/images/" + imageName));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return img;
	}
	
	private Graphics graphics;
	
	private final Screen screen;
	
}
