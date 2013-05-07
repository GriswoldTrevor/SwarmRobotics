import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GUICanvas extends JPanel {
    private Simulation sim;
    
    public GUICanvas(Simulation sim) {
        this.sim = sim;
        setPreferredSize(new Dimension(Simulation.MAX_X, Simulation.MAX_Y));
    }
    
    public void setSim(Simulation sim) {
    	this.sim = sim;
    }
    
    public void paintComponent(Graphics g) {
    	g.setColor(Color.white);
    	g.fillRect(0, 0, Simulation.MAX_X, Simulation.MAX_Y);
    	
    	g.setColor(Color.black);
        for(SimObject simObject: sim.getObjects()) {
        	if (simObject instanceof SwarmRobot) {
                drawRobot((SwarmRobot)simObject, g);
        	} else if (simObject instanceof Wall) {
        		drawWall((Wall)simObject, g);
        	} else if (simObject instanceof Goal) {
        		drawGoal((Goal)simObject, g);
        	} else {
        		System.out.println("Cannot draw unknown SimObject"+simObject);
        	}
        }
    }
    
    private void drawRobot(SwarmRobot robot, Graphics g) {
        int x = robot.getX();
        int y = robot.getY();
        double angle = robot.getAngle();
        
        g.setColor(Color.red);
        if(robot.isSucessful()){
        	g.setColor(Color.green);
        }
        g.fillRect(x-SwarmRobot.ROBOT_RADIUS, y-SwarmRobot.ROBOT_RADIUS, SwarmRobot.ROBOT_RADIUS*2, SwarmRobot.ROBOT_RADIUS*2);
        //g.fillOval(x-SwarmRobot.ROBOT_RADIUS, y-SwarmRobot.ROBOT_RADIUS, SwarmRobot.ROBOT_RADIUS*2, SwarmRobot.ROBOT_RADIUS*2);
    	g.drawPolygon(robot.getPolygonView());
    }
    
    private void drawWall(Wall wall, Graphics g) {
        int centerX = wall.getX();
        int centerY = wall.getY();
        int width = wall.getWidth();
        int height = wall.getHeight();
        
        g.setColor(Color.black);
        g.fillRect(centerX-width/2, centerY-height/2, width, height);
    }
    
    private void drawGoal(Goal goal, Graphics g) {
        int centerX = goal.getX();
        int centerY = goal.getY();

        g.setColor(Color.green);
        g.fillRect(centerX-Goal.RADIUS/2, centerY-Goal.RADIUS/2, Goal.RADIUS, Goal.RADIUS);
        //g.fillOval(centerX-Goal.RADIUS/2, centerY-Goal.RADIUS/2, Goal.RADIUS, Goal.RADIUS);
    }
}