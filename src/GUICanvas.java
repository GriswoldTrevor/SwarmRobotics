import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GUICanvas extends JPanel {
    private Simulation sim;
    
    public GUICanvas(Simulation sim) {
        this.sim = sim;
        setPreferredSize(new Dimension(800, 600));
    }
    
    public void paintComponent(Graphics g) {
    	g.setColor(Color.white);
    	g.fillRect(0, 0, getWidth(), getHeight());
    	
    	g.setColor(Color.black);
        for(SwarmRobot robot: sim.getRobots()) {
            drawRobot(robot, g);
        }
    }
    
    private void drawRobot(SwarmRobot robot, Graphics g) {
        int x = (int)(robot.getX()*20);
        int y = (int)(robot.getY()*20);
        double angle = robot.getAngle();
        int endX = x + (int)(Math.sin(angle*((2*Math.PI)/360))*10);
        int endY = y + (int)(Math.cos(angle*((2*Math.PI)/360))*10);
        
        g.drawOval(x-5, y-5, 10, 10);
        g.drawLine(x, y, endX, endY);
    }
}