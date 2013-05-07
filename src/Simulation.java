import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.ArrayList;

public class Simulation {
	public static final int MIN_X = 0;
	public static final int MIN_Y = 0;
	public static final int MAX_X = 800;
	public static final int MAX_Y = 600;
	public static final int EDGE_THICKNESS = 4;
	
	public int stepCount = 0;
	
	private boolean cooperate;
	private int stepLastSoundMade = -1;
	private ArrayList<SimObject> simObjects;
	
	public Simulation() {
		init(true);
		simObjects.add(new Wall(10,10,1,1));
	}
	public Simulation(int numBots, boolean cooperate) {
		init(cooperate);
		
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
			} while (isOverlapping(x,y,SwarmRobot.RADIUS*2,SwarmRobot.RADIUS*2));
			rotation = Util.random.nextInt(360);
			
			simObjects.add(new SwarmRobot(x, y, rotation, this));
		}
		
	}
	private void init(boolean cooperate) {		
		simObjects = new ArrayList<SimObject>();
		simObjects.add(new Wall(MAX_X/2, MIN_Y+EDGE_THICKNESS/2, MAX_X, EDGE_THICKNESS));
		simObjects.add(new Wall(MIN_X+EDGE_THICKNESS/2, MAX_Y/2, EDGE_THICKNESS, MAX_Y));
		simObjects.add(new Wall(MAX_X/2, MAX_Y-EDGE_THICKNESS/2, MAX_X, EDGE_THICKNESS));
		simObjects.add(new Wall(MAX_X-EDGE_THICKNESS/2, MAX_Y/2, EDGE_THICKNESS, MAX_Y));
		
		this.cooperate = cooperate;
	}
	
	public void setCooperate(boolean val){
		if(val){
			System.out.println("Now cooperating");
		} else {
			System.out.println("No longer cooperating");
		}
		cooperate = val;
	}
	public boolean getCooperate(){
		return cooperate;
	}
	
	public boolean isOverlapping(int x, int y, int width, int height){
		Area area = new Area(new Rectangle(x-width/2, y-height/2, width, height));

		//Check if tooClose to other objects
		for (SimObject o: simObjects) {
			if (area.intersects(o.getShape())){
				return true;
			}
		}
		return false;
	}
	
	public void makeSound(){
		stepLastSoundMade= stepCount +1;
	}
	public boolean checkSound(){
		return stepLastSoundMade == stepCount;
	}
	
	public ArrayList<SimObject> ObjectsInRange(Polygon p){
		ArrayList<SimObject> inRange = new ArrayList<SimObject>();
		
		Area area = new Area(p);
		
		//Check if tooClose to other objects
		for (SimObject o: simObjects) {
			if (area.intersects(o.getShape()) || area.contains(o.getShape())){
				inRange.add(o);
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
	public boolean isDone() {
		boolean successfulSoFar = true;
		for (SimObject simObject: simObjects) {
			if (successfulSoFar && (simObject instanceof SwarmRobot)){
				successfulSoFar = successfulSoFar && ((SwarmRobot) simObject).isSucessful();
			}
		}
		return successfulSoFar;
	}
	public ArrayList<SimObject> getObjects() {
		return simObjects;
	}
}