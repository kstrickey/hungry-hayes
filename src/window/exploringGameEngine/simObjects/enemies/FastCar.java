package window.exploringGameEngine.simObjects.enemies;

import java.awt.image.BufferedImage;

import window.exploringGameEngine.PaintMachine;

public class FastCar extends Car {
	
	public static final BufferedImage IMAGE = PaintMachine.getImageFromName("enemy/car kevin.png");
	
	public static final double SPEED = 850;
     
    public FastCar (double x, double y, int realmWidth, int realmHeight) {
        super(x, y, realmWidth, realmHeight);
        setImage(IMAGE);
        setSpeed(SPEED * .005);
    }
    
    @Override
    public double normalSpeed() {
    	return SPEED;
    }
     
    @Override
    public Enemy duplicate() {
        return new FastCar(0, 0, getRealmWidth(), getRealmHeight());
    }
    
}