import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class GUI extends JFrame {
	private static final String TITLE = "Swarm Robotics";

	private Container container;
    private LayoutManager layout;
    
    private Toolbar toolbar;
    private Simulation sim;
	
	private static final int gapH = 3;
    private static final int gapV = 3;
    
    private Thread  thread;
    private boolean running;
	
    public GUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(TITLE);
        
        sim = new Simulation();
        toolbar = new Toolbar(this);
        
        layout = new BorderLayout(gapH, gapV);
        container = getContentPane();

        container.add(toolbar, BorderLayout.NORTH);
        container.add(new GUICanvas(sim), BorderLayout.CENTER);

        setVisible(true);

        pack();
        
        startRun();
    }
    
    public void toggleRunning() {
        if (running) {
        	endRun();
        } else {
        	startRun();
        }
    }
    
    public void redraw() {
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

                if (stepTime < Settings.runDelay) {
                    Util.sleep((int) (Settings.runDelay - stepTime));
                }
            }
        }
    }
}