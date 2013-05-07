import java.awt.Rectangle;

public class Goal extends SimObject {
	public static final int RADIUS = 20;
	public Goal(int x, int y) {
		super(x, y);
	}
	
	public Rectangle getShape() {
		return new Rectangle(getX()-RADIUS/2, getY()-RADIUS/2, RADIUS, RADIUS);
	}
	
	public void act() {
		//It would be interesting if it ran away...
	}
}