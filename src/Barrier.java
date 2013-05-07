import java.awt.Rectangle;

public class Barrier extends SimObject {
	private int width;
	private int height;
	
	public Barrier(int x, int y) {
		super(x, y);
		init(0, 1, 1);
	}
	public Barrier(int x, int y, int width, int height) {
		super(x, y);
		init(0, width, height);
	}
	public Barrier(int x, int y, int width, int height, double angle) {
		super(x, y);
		init(angle, width, height);
	}
	
	private void init(double angle, int width, int height) {
		this.width = width;
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
	public Rectangle getShape() {
		return new Rectangle(getX()-width/2, getY()-height/2, width, height);
	}
	
	public void act() {
		//What did you expect it to do?
	}
}