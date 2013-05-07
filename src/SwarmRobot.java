import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;

public class SwarmRobot extends SimObject {
	public static final int MOVE_SPEED = 1;
	public static final double TURN_SPEED = 5;
	public static final double ANGLE_OF_VIEW = 90;
	public static final int SIGHT_DISTANCE = 20;
	public static final int ROBOT_RADIUS = 6;
	
	private boolean successful;
	private double angle;
	public boolean special = false;
	
	public SwarmRobot(int x, int y, Simulation sim) {
		super(x, y);
		init(90, false, sim);
	}
	public SwarmRobot(int x, int y, double angle, Simulation sim) {
		super(x, y);
		init(angle, false, sim);
	}
	public SwarmRobot(int x, int y, double angle, boolean successful, Simulation sim) {
		super(x, y);
		init(angle, successful, sim);
	}
	
	private void init(double angle, boolean successful, Simulation sim) {
		setAngle(angle);
		this.successful = successful;
		this.sim = sim;
	}
	private void setAngle(double angle) {
		this.angle = Util.normalizeAngle(angle);
	}
	
	public double getAngle() {
		return angle;
	}
	public boolean isSucessful() {
		return successful;
	}
	public String toString() {
		return "("+((int)(getX()*100))/100.0+", "+((int)(getY()*100))/100.0+") "+getAngle()+" degrees";
	}
	
	public Rectangle getShape() {
		return new Rectangle(getX()-ROBOT_RADIUS, getY()-ROBOT_RADIUS, ROBOT_RADIUS*2, ROBOT_RADIUS*2);
	}
	public Polygon getPolygonView(){
		int x1 = getX();
		int y1 = getY();
		int x2 = (int) (x1 + Math.cos(Math.toRadians(angle-ANGLE_OF_VIEW/2))*SIGHT_DISTANCE);
		int y2 = (int) (y1 + Math.sin(Math.toRadians(angle-ANGLE_OF_VIEW/2))*SIGHT_DISTANCE);
		int x3 = (int) (x1 + Math.cos(Math.toRadians(angle))*SIGHT_DISTANCE);
		int y3 = (int) (y1 + Math.sin(Math.toRadians(angle))*SIGHT_DISTANCE);
		int x4 = (int) (x1 + Math.cos(Math.toRadians(angle+ANGLE_OF_VIEW/2))*SIGHT_DISTANCE);
		int y4 = (int) (y1 + Math.sin(Math.toRadians(angle+ANGLE_OF_VIEW/2))*SIGHT_DISTANCE);

		return new Polygon(new int[] {x1, x2, x3, x4}, new int[] {y1, y2, y3, y4}, 4);
	}
	
	public void act() {
		if (!successful) {
			if (!objectInFront()) {
				moveForward();
			} else {
				if (!goalInFront()){
					spinRight();
				} else {
					successful = true;
				}
			}
		} else {
			//Celebrate!
		}
	}
	private void moveForward() {
		setX(x + MOVE_SPEED*Math.cos(Math.toRadians(angle)));
		setY(y + MOVE_SPEED*Math.sin(Math.toRadians(angle)));
	}
	private void moveBackward() {
		setX(x - MOVE_SPEED*Math.cos(Math.toRadians(angle)));
		setY(y - MOVE_SPEED*Math.sin(Math.toRadians(angle)));
	}
	private void spinLeft() {
		setAngle(getAngle() - TURN_SPEED);
	}
	private void spinRight() {
		setAngle(getAngle() + TURN_SPEED);
	}
	private boolean objectInFront() {
		return sim.tooClose(this);
	}
	private boolean goalInFront() {
		ArrayList<SimObject> objectsInFront = sim.ObjectsInRange(this);
		for (SimObject o : objectsInFront) {
			if ((o instanceof Goal) || ((o instanceof SwarmRobot) && (((SwarmRobot)o).isSucessful()))){
				return true;
			}
		}
		return false;
	}
	private ArrayList<SwarmRobot> robotsInSight() {
		return null;
	}
	
}