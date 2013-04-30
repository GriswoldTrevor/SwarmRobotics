import java.util.ArrayList;

public class SwarmRobot {
	private double x;
	private double y;
	private double angle;
	private boolean successful;
	
	private static double moveSpeed = 1;
	private static double turnSpeed = 15;
	
	private boolean move = true;
	
	public SwarmRobot() {
		init(0,0,90,false);
	}
	public SwarmRobot(double x, double y) {
		init(x,y,90,false);
	}
	public SwarmRobot(double x, double y, double angle) {
		init(x,y,angle,false);
	}
	public SwarmRobot(double x, double y, double angle, boolean successful) {
		init(x,y,angle,successful);
	}
	private void init(double x, double y, double angle, boolean successful) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.successful = successful;
	}
	
	public void act() {
		if (move) {
			moveForward();
		} else {
			spinRight();
		}
		move = !move;
		
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
		x = x + moveSpeed*Math.sin(angle*((2*Math.PI)/360));
		y = y + moveSpeed*Math.cos(angle*((2*Math.PI)/360));
	}
	private void moveBackward() {
		x = x - moveSpeed*Math.sin(angle*((2*Math.PI)/360));
		y = y - moveSpeed*Math.cos(angle*((2*Math.PI)/360));
	}
	private void spinLeft() {
		angle = (angle + turnSpeed)%360;
	}
	private void spinRight() {
		angle = (angle - turnSpeed)%360;
	}
	
	private void pivotForwardRight() {}
	private void pivotForwardLeft() {}
	private void pivotBackwardRight() {}
	private void pivotBackwardLeft() {}
	
	private boolean objectInFront() {
		return false;
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