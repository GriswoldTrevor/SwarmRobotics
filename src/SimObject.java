import java.awt.Rectangle;

public abstract class SimObject {
	protected Simulation sim;
	
	protected double x;
	protected double y;
	
	public SimObject(int x, int y) {
		setX(x);
		setY(y);
	}
	
	protected void setX(double x) {
		this.x = x;
	}
	protected void setY(double y) {
		this.y = y;
	}
	
	public int getX() {
		return (int)x;
	}
	public int getY() {
		return (int)y;
	}
	
	public abstract Rectangle getShape();
	public abstract void act();
}