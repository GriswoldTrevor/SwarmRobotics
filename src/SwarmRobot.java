import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.ArrayList;

public class SwarmRobot extends SimObject {
	public static final int MOVE_SPEED = 1;
	public static final double TURN_SPEED = 1;
	public static final double FOV = 45;
	public static final int FRONT_SENSOR_SIGHT_DISTANCE = 20;
	public static final int CAMERA_SIGHT_DISTANCE = 800;
	public static final int RADIUS = 6;
	public static final int AVERAGE_SCAN_WAIT_TIME = 500;
	public static final int AVERAGE_SCAN_WAIT_JITTER = 200;
	
	private boolean successful;
	private double angle;
	
	private boolean scanning;
	private double desiredAngle;
	private int timeUntilScan;
	
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
		scanning = false;
		desiredAngle = -1;
		timeUntilScan = 0;
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
		return "("+getX()+", "+getY()+") "+getAngle()+" degrees";
	}
	
	public Rectangle getShape() {
		return new Rectangle(getX()-RADIUS, getY()-RADIUS, RADIUS*2, RADIUS*2);
	}
	public Polygon getFrontSensorView(){
		int x1 = getX();
		int y1 = getY();
		int x2 = (int) (x1 + Math.cos(Math.toRadians(angle-FOV/2))*FRONT_SENSOR_SIGHT_DISTANCE);
		int y2 = (int) (y1 + Math.sin(Math.toRadians(angle-FOV/2))*FRONT_SENSOR_SIGHT_DISTANCE);
		int x3 = (int) (x1 + Math.cos(Math.toRadians(angle))*FRONT_SENSOR_SIGHT_DISTANCE);
		int y3 = (int) (y1 + Math.sin(Math.toRadians(angle))*FRONT_SENSOR_SIGHT_DISTANCE);
		int x4 = (int) (x1 + Math.cos(Math.toRadians(angle+FOV/2))*FRONT_SENSOR_SIGHT_DISTANCE);
		int y4 = (int) (y1 + Math.sin(Math.toRadians(angle+FOV/2))*FRONT_SENSOR_SIGHT_DISTANCE);

		return new Polygon(new int[] {x1, x2, x3, x4}, new int[] {y1, y2, y3, y4}, 4);
	}
	public Polygon getCameraView(){
		int x1 = getX();
		int y1 = getY();
		int x2 = (int) (x1 + Math.cos(Math.toRadians(angle-FOV/2))*CAMERA_SIGHT_DISTANCE);
		int y2 = (int) (y1 + Math.sin(Math.toRadians(angle-FOV/2))*CAMERA_SIGHT_DISTANCE);
		int x3 = (int) (x1 + Math.cos(Math.toRadians(angle))*CAMERA_SIGHT_DISTANCE);
		int y3 = (int) (y1 + Math.sin(Math.toRadians(angle))*CAMERA_SIGHT_DISTANCE);
		int x4 = (int) (x1 + Math.cos(Math.toRadians(angle+FOV/2))*CAMERA_SIGHT_DISTANCE);
		int y4 = (int) (y1 + Math.sin(Math.toRadians(angle+FOV/2))*CAMERA_SIGHT_DISTANCE);

		return new Polygon(new int[] {x1, x2, x3, x4}, new int[] {y1, y2, y3, y4}, 4);
	}
	private void stopScanning() {
		scanning = false;
		timeUntilScan = AVERAGE_SCAN_WAIT_TIME+Util.random.nextInt(AVERAGE_SCAN_WAIT_JITTER*2 +1)-AVERAGE_SCAN_WAIT_JITTER;
	}
	private void startScanning() {
		desiredAngle = Util.normalizeAngle(angle - TURN_SPEED - 1);
		scanning = true;
	}
	private boolean soundHeard() {
		return sim.checkSound();
	}
	public boolean hasDesiredAngle() {
		return desiredAngle != -1;
	}
	public void act() {
		if (!successful) {
			if (!goalInFront()){
				if(sim.getCooperate() && soundHeard()){
					startScanning();
				}
				if (desiredAngle != -1){
					spinRight();
					if (scanning) {
						SimObject goal = findGoal();
						if (goal != null) {
							stopScanning();
							desiredAngle = -1;
						}
					}
					if (Util.angleDifference(desiredAngle,angle) <= TURN_SPEED){
						desiredAngle = -1;
						if (scanning) {
							stopScanning();
						}
					}
					
				} else {
					if (timeUntilScan <= 0){
						startScanning();
					} else {
						if (!objectInFront()) {
							moveForward();
						} else {
							spinRight();
						}
					}
				}
			} else {
				if(sim.getCooperate()) {
					sim.makeSound();
				}
				successful = true;
			}
		} else {
			//Celebrate!
		}
		timeUntilScan--;
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
		return !getObjsInRange(getFrontSensorView()).isEmpty();
	}
	private boolean goalInFront() {
		ArrayList<SimObject> objectsInFront = getObjsInRange(getFrontSensorView());
		for (SimObject o : objectsInFront) {
			if (o instanceof Goal) {
				return true;
			}
			if (sim.getCooperate() && ((o instanceof SwarmRobot) && (((SwarmRobot)o).isSucessful()))){
				return true;
			}
		}
		return false;
	}
	private SimObject findGoal() {
		ArrayList<SimObject> objectsInFront = getObjsInRange(getCameraView());
		for (SimObject o : objectsInFront) {
			if (o instanceof Goal) {
				return o;
			}
			if (sim.getCooperate() && ((o instanceof SwarmRobot) && (((SwarmRobot)o).isSucessful()))){
				return o;
			}
		}
		return null;
	}
	private ArrayList<SimObject> getObjsInRange(Polygon p){
		ArrayList<SimObject> objs = sim.ObjectsInRange(p);
		objs.remove(this);
		return objs;
	}
}