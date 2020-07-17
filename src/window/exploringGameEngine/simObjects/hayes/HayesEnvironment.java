package window.exploringGameEngine.simObjects.hayes;

/**
 * Define Hayes movement constants.
 */
public class HayesEnvironment 
{
    public static final HayesEnvironment DEFAULT = new HayesEnvironment(
            200, Math.PI*3/2, 50, 30, 1500);
    
    public static final HayesEnvironment UNDERWATER = new HayesEnvironment(
            200, Math.PI*3/2, 1.8, .4, 400);
    
    public double maxSpeed, angularV, acceleration, friction, recoil;
    
    /**
     * Construct a new environment with constants involving Hayes' movement.
     * @param maxSpeed The maximum speed that Hayes may attain while not stunned, in px/sec.
     * @param angularV The angle through which Hayes can turn, in rad/sec.
     * @param acceleration Change in speed per second while Hayes moves forward or backward, px/sec^2.
     * @param friction Amount in which speed is impeded, px/sec^2.
     * @param recoil Hayes' speed changed by this much when he is hurt, px/sec.
     */
    public HayesEnvironment(double maxSpeed, double angularV, double acceleration, double friction, double recoil) {
        this.maxSpeed = maxSpeed;
        this.angularV = angularV;
        this.acceleration = acceleration;
        this.friction = friction;
        this.recoil = recoil;
    }
    
    /**
     * Copy constructor.
     * @param other
     */
    public HayesEnvironment(HayesEnvironment other) {
    	this(other.maxSpeed, other.angularV, other.acceleration, other.friction, other.recoil);
    }

}
