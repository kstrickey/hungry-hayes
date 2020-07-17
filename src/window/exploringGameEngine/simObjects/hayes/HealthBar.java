package window.exploringGameEngine.simObjects.hayes;

import java.awt.image.BufferedImage;

import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.Paintable;

public class HealthBar implements Paintable {
	
	public static final int MAX_HEALTH = 8;		// DO NOT CHANGE: IMAGE DEPENDS ON THIS
	public static final BufferedImage imageCollection = PaintMachine.getImageFromName("hayes/healthbar.png");
	
	public HealthBar() {
		prescribeCurrentHealth(MAX_HEALTH);
	}
	
	/**
	 * Sets currentHealth, between 0 and MAX_HEALTH, and applicably changes the currentSubimage to the appropriate health bar.
	 * Keeps currentHealth between 0 and MAX_HEALTH.
	 * @param currentHealth
	 */
	public void prescribeCurrentHealth(double currentHealth) {
		if (currentHealth > MAX_HEALTH) {
			currentHealth = MAX_HEALTH;
		} else if (currentHealth < 0) {
			currentHealth = 0;
		}
		this.currentHealth = currentHealth;
		int healthInt = (int) (currentHealth + 0.5);		// for use in subimaging
		currentSubimage = imageCollection.getSubimage(0, imageCollection.getHeight() - imageCollection.getHeight() * (1 + healthInt) / 9, 
				imageCollection.getWidth(), imageCollection.getHeight() / 9);
	}
	
	/**
	 * Adds the parameter double to the current health, keeping it within bounds of 0 and MAX_HEALTH.
	 * May pass a negative number to decrease health.
	 * @param addedHealth
	 */
	public void addHealth(double addedHealth) {
		prescribeCurrentHealth(currentHealth + addedHealth);
	}
	
	@Override
	public void paint(PaintMachine paintMachine) {
		paintMachine.paintImageRelativeToScreen(currentSubimage, 25, 25, 336, 41);
	}
	
	/**
	 * Returns true if the currentHealth level shows 0 hearts [less than 0.5].
	 * @return
	 */
	public boolean isEmpty() {
		return currentHealth < .5;
	}
	
	private double currentHealth;
	private BufferedImage currentSubimage;
}
