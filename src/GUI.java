import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	private static final String TITLE = "Swarm Robotics";
	public int runDelay = 50;

	private Container container;
    @SuppressWarnings("unused")
	private LayoutManager layout;
    
    private Toolbar toolbar;
    private Simulation sim;
    private GUICanvas canvas;
	
	private static final int gapH = 3;
    private static final int gapV = 3;
    
    private Thread  thread;
    private boolean running;
    
    private int currentNumRobots;
	
    public GUI() {
        currentNumRobots = 1;
        sim = new Simulation(currentNumRobots);
        toolbar = new Toolbar(this);
        
        layout = new BorderLayout(gapH, gapV);
        container = getContentPane();

        container.add(toolbar, BorderLayout.NORTH);
        
        canvas = new GUICanvas(sim);
        container.add(canvas, BorderLayout.CENTER);
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(TITLE+": Step "+sim.stepCount);
        setVisible(true);

        pack();
    }
    
    public void restart() {
    	endRun();
    	sim = new Simulation(currentNumRobots);
    	canvas.setSim(sim);
    	redraw();
    }
    public void restart(int numRobots) {
    	currentNumRobots = numRobots;
    	restart();
    }
    
    public void toggleRunning() {
        if (running) {
        	endRun();
        } else {
        	startRun();
        }
    }
    
    public void redraw() {
    	setTitle(TITLE+": Step "+sim.stepCount);
        repaint();
    }

    public void pause() {
        endRun();
    }
    public void play() {
        startRun();
    }
    private void startRun() {
        running = true;
        thread  = new RunnerThread();

        thread.start();
    }
    private void endRun() {
        running = false;
        if (thread != null) {
            Util.joinThread(thread);
            thread = null;
        }
    }
    public void step() {
        sim.step();
        redraw();
    }
    
    private class RunnerThread extends Thread {
        public void run() {
            long startTime;
            long stepTime;

            while (running) {
                startTime = System.currentTimeMillis();
                step();
                stepTime = System.currentTimeMillis() - startTime;

                if (stepTime < runDelay) {
                    Util.sleep((int) (runDelay - stepTime));
                }
            }
        }
    }
}