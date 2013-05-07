import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.ArrayList;

public class Simulation {
	public static final int MIN_X = 0;
	public static final int MIN_Y = 0;
	public static final int MAX_X = 800;
	public static final int MAX_Y = 600;
	
	public int stepCount = 0;
	private ArrayList<SimObject> simObjects;
	private ArrayList<Rectangle> edges;
	
	public Simulation() {
		init();
		simObjects.add(new Wall(10,10,1,1));
	}
	public Simulation(int numBots) {
		init();
		
		/*
		SwarmRobot r;
		r = new SwarmRobot(15, 5, 0, this);
		simObjects.add(r);
		r.special=true; 
		*/
		
		/* Direction Test
		simObjects.add(new SwarmRobot(100, 100, 0, this));
		simObjects.add(new SwarmRobot(200, 100, 180, this));
		simObjects.add(new SwarmRobot(300, 100, 90, this));
		simObjects.add(new SwarmRobot(300, 200, 270, this));
		simObjects.add(new SwarmRobot(400, 100, 45, this));
		simObjects.add(new SwarmRobot(500, 200, 225, this));
		simObjects.add(new SwarmRobot(600, 200, 315, this));
		simObjects.add(new SwarmRobot(700, 100, 135, this));
		 */
		
		/* Crash Test
		simObjects.add(new SwarmRobot(200, 100, 90, this));
		simObjects.add(new SwarmRobot(100, 200, 0, this));
		simObjects.add(new SwarmRobot(200, 300, 270, this));
		simObjects.add(new SwarmRobot(300, 200, 180, this));
		*/
		
		
		simObjects.add(new Wall(200,200,20,200));
		simObjects.add(new Wall(200,400,200,20));
		simObjects.add(new Wall(500,100,20,200));
		simObjects.add(new Wall(200,400,200,20));
		simObjects.add(new Goal(170,260));
		
		int x, y, rotation;
		for (int i = 0; i < numBots; i++) {
			do {
				x = Util.random.nextInt(701)+50;
				y = Util.random.nextInt(501)+50;
			} while (isOverlapping(x,y,SwarmRobot.ROBOT_RADIUS*2,SwarmRobot.ROBOT_RADIUS*2));
			rotation = Util.random.nextInt(360);
			
			simObjects.add(new SwarmRobot(x, y, rotation, this));
		}
		
	}
	private void init() {
		edges = new ArrayList<Rectangle>();
		edges.add(new Rectangle(MIN_X-1, MIN_Y, 1, MAX_Y));
		edges.add(new Rectangle(MIN_X, MIN_Y-1, MAX_X, 1));
		edges.add(new Rectangle(MIN_X, MAX_Y, MAX_X, 1));
		edges.add(new Rectangle(MAX_X, MIN_Y, 1, MAX_Y));
		
		simObjects = new ArrayList<SimObject>();
	}
	
	public boolean isOverlapping(int x, int y, int width, int height){
		Area area = new Area(new Rectangle(x-width/2, y-height/2, width, height));
		
		//Check if tooClose to edges
		for (Rectangle edge: edges) {
			if (area.intersects(edge)){
				return true;
			}
		}
		
		//Check if tooClose to other objects
		for (SimObject o: simObjects) {
			if (area.intersects(o.getShape())){
				return true;
			}
		}
		return false;
	}
	
	public boolean tooCloseToEdge(SwarmRobot r) {
		Area area = new Area(r.getPolygonView());
		
		//Check if tooClose to edges
		for (Rectangle edge: edges) {
			if (area.intersects(edge)){
				return true;
			}
		}
		return false;
	}
	public boolean tooClose(SwarmRobot r){
		if (tooCloseToEdge(r)){
			return true;
		}
		return !ObjectsInRange(r).isEmpty();
	}
	public ArrayList<SimObject> ObjectsInRange(SwarmRobot r){
		ArrayList<SimObject> inRange = new ArrayList<SimObject>();
		
		Area area = new Area(r.getPolygonView());
		
		//Check if tooClose to other objects
		for (SimObject o: simObjects) {
			if (o != r) {
				if (area.intersects(o.getShape())){
					inRange.add(o);
				}
			}
		}
		return inRange;
	}
	
	public void step() {
		stepCount++;
		for (SimObject simObject: simObjects) {
			simObject.act();
		}
	}
	public ArrayList<SimObject> getObjects() {
		return simObjects;
	}
}