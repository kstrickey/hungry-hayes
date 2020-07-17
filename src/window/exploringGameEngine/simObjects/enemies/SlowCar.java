package window.exploringGameEngine.simObjects.enemies;

public class SlowCar extends Car {
	
    public static final double SPEED = 300;
     
    public SlowCar (double x, double y, int realmWidth, int realmHeight) {
        super(x, y, realmWidth, realmHeight);
        setSpeed(SPEED*.005);
    }
    
    @Override
    public double normalSpeed() {
    	return SPEED;
    }
     
    @Override
    public Enemy duplicate() {
        return new SlowCar(0, 0, getRealmWidth(), getRealmHeight());
    }
    
}