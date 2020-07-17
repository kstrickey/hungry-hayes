package resources.classes;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageTools {
	
	/**
	 * Rotates an image. Actually rotates a new copy of the image.
	 * Taken from https://code.google.com/p/game-engine-for-java/source/browse/src/com/gej/util/ImageTool.java.
	 * 
	 * @param img : The image to be rotated
	 * @param angle : The angle in degrees
	 * @return The rotated image
	 */
	public static BufferedImage rotate(BufferedImage img, double angle)
	{
	    double sin = Math.abs(Math.sin(Math.toRadians(angle))),
	           cos = Math.abs(Math.cos(Math.toRadians(angle)));

	    int w = img.getWidth(null), h = img.getHeight(null);

	    int neww = (int) Math.floor(w*cos + h*sin),
	        newh = (int) Math.floor(h*cos + w*sin);

	    BufferedImage bimg = new BufferedImage(neww, newh, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = bimg.createGraphics();

	    g.translate((neww-w)/2, (newh-h)/2);
	    g.rotate(Math.toRadians(angle), w/2, h/2);
	    g.drawRenderedImage(img, null);
	    g.dispose();

	    return bimg;
	}
	
}
