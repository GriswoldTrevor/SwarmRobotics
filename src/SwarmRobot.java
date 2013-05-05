import java.util.ArrayList;

public class SwarmRobot {
	public static final double MOVE_SPEED = 0.1;
	public static final double TURN_SPEED = 5;
	public static final double ANGLE_OF_VIEW = 90;
	public static final double SIGHT_DISTANCE = 1;
	public static final double ROBOT_RADIUS = 0.5;
	
	private Simulation sim;
	
	private double x;
	private double y;
	private double angle;
	private boolean successful;
	public boolean special = false;
	
	public SwarmRobot(Simulation sim) {
		init(0,0,90,false, sim);
	}
	public SwarmRobot(double x, double y, Simulation sim) {
		init(x,y,90,false, sim);
	}
	public SwarmRobot(double x, double y, double angle, Simulation sim) {
		init(x,y,angle,false, sim);
	}
	public SwarmRobot(double x, double y, double angle, boolean successful, Simulation sim) {
		init(x,y,angle,successful, sim);
	}
	private void init(double x, double y, double angle, boolean successful, Simulation sim) {
		this.x = x;
		this.y = y;
		this.angle = Util.normalizeAngle(angle);
		this.successful = successful;
		this.sim = sim;
	}
	
	public void act() {
		if (!objectInFront()) {
			moveForward();
		} else {
			spinRight();
		}
	}
	public boolean isSucessful() {
		return successful;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getAngle() {
		return angle;
	}
	public String toString() {
		return "("+((int)(x*100))/100.0+", "+((int)(y*100))/100.0+") "+angle+" degrees";
	}
	
	private void moveForward() {
		x = x + MOVE_SPEED*Math.sin(Math.toRadians(angle));
		y = y + MOVE_SPEED*Math.cos(Math.toRadians(angle));
	}
	private void moveBackward() {
		x = x - MOVE_SPEED*Math.sin(Math.toRadians(angle));
		y = y - MOVE_SPEED*Math.cos(Math.toRadians(angle));
	}
	private void spinLeft() {
		angle = Util.normalizeAngle(angle + TURN_SPEED);
	}
	private void spinRight() {
		angle = Util.normalizeAngle(angle - TURN_SPEED);
	}
	
	private void pivotForwardRight() {}
	private void pivotForwardLeft() {}
	private void pivotBackwardRight() {}
	private void pivotBackwardLeft() {}
	
	private boolean objectInFront() {
		return sim.tooClose(x, y, SIGHT_DISTANCE, angle, ANGLE_OF_VIEW/2, this);
	}
	private boolean objectBelow() {
		return false;
	}
	private ArrayList<SwarmRobot> robotsInSight() {
		return null;
	}
	private boolean goalInFront() {
		return false;
	}
}