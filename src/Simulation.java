import java.util.ArrayList;

public class Simulation {
	public static final int MIN_X = 0;
	public static final int MIN_Y = 0;
	public static final int MAX_X = 40;
	public static final int MAX_Y = 30;
	
	public int stepCount = 0;
	private ArrayList<SimObject> simObjects;
	
	public Simulation() {
		simObjects = new ArrayList<SimObject>();
		
	}
	public Simulation(int numBots) {
		/*
		SwarmRobot r;
		r = new SwarmRobot(15, 5, 0, this);
		simObjects.add(r);
		r.special=true; 
		*/
		
		/* Direction Test
		simObjects.add(new SwarmRobot(5, 5, 90, this));
		simObjects.add(new SwarmRobot(10, 5, -90, this));
		simObjects.add(new SwarmRobot(15, 5, 0, this));
		simObjects.add(new SwarmRobot(15, 10, 180, this));
		simObjects.add(new SwarmRobot(20, 5, 45, this));
		simObjects.add(new SwarmRobot(25, 10, -135, this));
		simObjects.add(new SwarmRobot(30, 10, 135, this));
		simObjects.add(new SwarmRobot(35, 5, -45, this));
		*/
		
		/* Crash Test
		simObjects.add(new SwarmRobot(10, 5, 0, this));
		simObjects.add(new SwarmRobot(5, 10, 90, this));
		simObjects.add(new SwarmRobot(10, 15, 180, this));
		simObjects.add(new SwarmRobot(15, 10, 270, this));
		*/
		
		simObjects = new ArrayList<SimObject>();
		int x, y, rotation;
		for (int i = 0; i < numBots; i++) {
			do {
				x = Util.random.nextInt(21)+10;
				y = Util.random.nextInt(11)+10;
			} while (tooClose(x,y,1));
			rotation = Util.random.nextInt(360);
			
			simObjects.add(new SwarmRobot(x, y, rotation, this));
		}
	}
	
	private boolean outOfBounds(double x, double y) {
		if ((x <= MIN_X) || (y <= MIN_Y) || (x >= MAX_X) || (y >= MAX_Y)) {
			return true;
		}
		return false;
	}
	private boolean tooCloseToEdge(double x, double y, double distance) {
		if (outOfBounds(x - distance, y) || outOfBounds(x, y - distance) || outOfBounds(x + distance, y) || outOfBounds(x, y + distance)) {
			return true;
		}
		return false;
	}
	public boolean tooClose(double x, double y, double distance) {
		if (tooCloseToEdge(x,y,distance)) {
			return true;
		}
		for (SimObject r: simObjects) {
			double xDif = r.getX() - x;
			double yDif = r.getY() - y;
			double distanceBetween = Math.sqrt(Math.pow(xDif,2) + Math.pow(yDif,2));
			if (distanceBetween <= distance) {
				return true;
			}
		}
		
		return false;
	}
	public boolean tooClose(double x, double y, double distance, double angle, double angleDistance, SwarmRobot thisR) {
		if (tooCloseToEdge(x,y,distance)) {
			//This works, for now. Not entirely accurate
			if (outOfBounds(x + Math.sin(Math.toRadians(angle))*distance, y + Math.cos(Math.toRadians(angle))*distance)){
				return true;
			}
		}
		for (SimObject r: simObjects) {
			if (r != thisR) {
				double xDif = r.getX() - x;
				double yDif = r.getY() - y;
				double distanceBetween = Math.sqrt(Math.pow(xDif,2) + Math.pow(yDif,2));
				if (distanceBetween <= distance) {
					double angleBetween = Util.normalizeAngle(Math.toDegrees(Math.atan2(xDif, yDif)));
					if(thisR.special){
						System.out.println("AngleBetween: "+angleBetween+", angle="+angle+" angleDistance="+angleDistance);
					}
					if (Util.angleDifference(angleBetween, angle) <= angleDistance) {
						return true;
					}
				}
			}
		}
		return false;
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