import java.util.ArrayList;

public class Simulation {
	private ArrayList<SwarmRobot> robots;
	
	public Simulation() {
		robots = new ArrayList<SwarmRobot>();
		robots.add(new SwarmRobot(10, 10));
		robots.add(new SwarmRobot(20, 20, -90));
	}
	
	public void step() {
		for (SwarmRobot robot: robots) {
			robot.act();
		}
	}
	public ArrayList<SwarmRobot> getRobots() {
		return robots;
	}
}