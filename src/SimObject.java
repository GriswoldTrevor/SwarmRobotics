public abstract class SimObject {
	protected Simulation sim;
	
	private double x;
	private double y;
	private double angle;
	
	protected void setX(double x) {
		this.x = x;
	}
	protected void setY(double y) {
		this.y = y;
	}
	protected void setAngle(double angle) {
		this.angle = Util.normalizeAngle(angle);
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
	
	public abstract void act();
}