import java.util.ArrayList;

public class SwarmRobot extends SimObject {
	public static final double MOVE_SPEED = 0.1;
	public static final double TURN_SPEED = 5;
	public static final double ANGLE_OF_VIEW = 90;
	public static final double SIGHT_DISTANCE = 1;
	public static final double ROBOT_RADIUS = 0.5;
	
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
		setX(x);
		setY(y);
		setAngle(angle);
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
	public String toString() {
		return "("+((int)(getX()*100))/100.0+", "+((int)(getY()*100))/100.0+") "+getAngle()+" degrees";
	}
	
	private void moveForward() {
		setX(getX() + MOVE_SPEED*Math.sin(Math.toRadians(getAngle())));
		setY(getY() + MOVE_SPEED*Math.cos(Math.toRadians(getAngle())));
	}
	private void moveBackward() {
		setX(getX() - MOVE_SPEED*Math.sin(Math.toRadians(getAngle())));
		setY(getY() - MOVE_SPEED*Math.cos(Math.toRadians(getAngle())));
	}
	private void spinLeft() {
		setAngle(getAngle() + TURN_SPEED);
	}
	private void spinRight() {
		setAngle(getAngle() - TURN_SPEED);
	}
	
	private boolean objectInFront() {
		return sim.tooClose(getX(), getY(), SIGHT_DISTANCE, getAngle(), ANGLE_OF_VIEW/2, this);
	}
	private ArrayList<SwarmRobot> robotsInSight() {
		return null;
	}
	private boolean goalInFront() {
		return false;
	}
}