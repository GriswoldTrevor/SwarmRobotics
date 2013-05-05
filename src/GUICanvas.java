import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GUICanvas extends JPanel {
	public static final int SCALE_FACTOR = 20;
    private Simulation sim;
    
    public GUICanvas(Simulation sim) {
        this.sim = sim;
        setPreferredSize(new Dimension(Simulation.MAX_X*SCALE_FACTOR, Simulation.MAX_Y*SCALE_FACTOR));
    }
    
    public void setSim(Simulation sim) {
    	this.sim = sim;
    }
    
    public void paintComponent(Graphics g) {
    	g.setColor(Color.white);
    	g.fillRect(0, 0, Simulation.MAX_X*SCALE_FACTOR, Simulation.MAX_Y*SCALE_FACTOR);
    	
    	g.setColor(Color.black);
        for(SimObject simObject: sim.getObjects()) {
        	if (simObject instanceof SwarmRobot) {
                drawRobot((SwarmRobot)simObject, g);
        	} else {
        		System.out.println("Cannot draw unknown SimObject"+simObject);
        	}
        }
    }
    
    private void drawRobot(SwarmRobot robot, Graphics g) {
        int x = (int)(robot.getX()*SCALE_FACTOR);
        int y = (int)(robot.getY()*SCALE_FACTOR);
        double angle = robot.getAngle();
        double fov = SwarmRobot.ANGLE_OF_VIEW;
        double robotSize = SwarmRobot.ROBOT_RADIUS*SCALE_FACTOR;
        
        g.setColor(Color.black);
        if(robot.special){
        	g.setColor(Color.red);
        }
        g.drawOval(x-((int) robotSize/2), y-((int) robotSize/2), (int)robotSize, (int)robotSize);
        
        int endX = x + (int)(Math.sin(Math.toRadians(angle-fov/2))*robotSize);
        int endY = y + (int)(Math.cos(Math.toRadians(angle-fov/2))*robotSize);
        g.drawLine(x, y, endX, endY);
        
        endX = x + (int)(Math.sin(Math.toRadians(angle+fov/2))*robotSize);
        endY = y + (int)(Math.cos(Math.toRadians(angle+fov/2))*robotSize);
        g.drawLine(x, y, endX, endY);
    }
}